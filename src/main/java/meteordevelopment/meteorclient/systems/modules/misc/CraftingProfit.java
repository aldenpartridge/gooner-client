package meteordevelopment.meteorclient.systems.modules.misc;

import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.misc.donutsmp.*;
import meteordevelopment.meteorclient.utils.network.Http;
import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
import meteordevelopment.orbit.EventHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CraftingProfit extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgFilters = settings.createGroup("Filters");

    // General settings
    private final Setting<String> apiKey = sgGeneral.add(new StringSetting.Builder()
        .name("api-key")
        .description("Your DonutSMP API key from /api command.")
        .defaultValue("ec239e2e3b7648cd92f91076554c93bf")
        .build()
    );

    private final Setting<Integer> refreshInterval = sgGeneral.add(new IntSetting.Builder()
        .name("refresh-interval")
        .description("How often to refresh prices (in seconds).")
        .defaultValue(60)
        .min(10)
        .sliderMax(300)
        .build()
    );

    private final Setting<Boolean> autoRefresh = sgGeneral.add(new BoolSetting.Builder()
        .name("auto-refresh")
        .description("Automatically refresh prices when module is active.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> openGuiOnActivate = sgGeneral.add(new BoolSetting.Builder()
        .name("open-gui-on-activate")
        .description("Automatically open the profit GUI when module is activated.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Integer> pageDelay = sgGeneral.add(new IntSetting.Builder()
        .name("query-delay")
        .description("Delay between item queries in milliseconds (to respect rate limits).")
        .defaultValue(250)
        .min(100)
        .sliderMax(1000)
        .build()
    );

    // Filter settings
    private final Setting<Double> minProfit = sgFilters.add(new DoubleSetting.Builder()
        .name("min-profit")
        .description("Minimum profit to display.")
        .defaultValue(0.01)
        .min(0)
        .sliderMax(10000)
        .build()
    );

    private final Setting<Double> minProfitPercent = sgFilters.add(new DoubleSetting.Builder()
        .name("min-profit-percent")
        .description("Minimum profit percentage to display.")
        .defaultValue(0.0)
        .min(0)
        .sliderMax(100)
        .build()
    );

    private final Setting<Integer> maxResults = sgFilters.add(new IntSetting.Builder()
        .name("max-results")
        .description("Maximum number of profitable crafts to display.")
        .defaultValue(50)
        .min(1)
        .sliderMax(200)
        .build()
    );

    private final Setting<Boolean> showOnlyProfitable = sgFilters.add(new BoolSetting.Builder()
        .name("only-profitable")
        .description("Only show crafts that are actually profitable.")
        .defaultValue(true)
        .build()
    );

    // Data
    private final Map<String, Double> itemPrices = new ConcurrentHashMap<>();
    private List<ProfitableCraft> profitableCrafts = new ArrayList<>();
    private long lastRefreshTime = 0;
    private boolean isRefreshing = false;
    private String lastError = null;
    private int totalRecipesChecked = 0;
    private int profitableRecipesFound = 0;

    public CraftingProfit() {
        super(Categories.Misc, "crafting-profit", "Calculates profitable crafting methods using DonutSMP auction house data.");
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WHorizontalList list = theme.horizontalList();

        // Open Results button
        WButton openResultsBtn = list.add(theme.button("Open Results Screen")).expandX().widget();
        openResultsBtn.action = this::openGui;

        // Refresh button
        WButton refreshBtn = list.add(theme.button("Refresh Prices")).expandX().widget();
        refreshBtn.action = this::refreshPrices;

        return list;
    }

    @Override
    public void onActivate() {
        if (apiKey.get().isEmpty()) {
            error("Please set your API key in the module settings! Get it with /api in-game.");
            toggle();
            return;
        }

        // Initial refresh (GUI will open when refresh completes)
        refreshPrices();
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        // Clear data when leaving server
        itemPrices.clear();
        profitableCrafts.clear();
        lastRefreshTime = 0;
    }

    @Override
    public void onDeactivate() {
        // Keep data cached even when inactive
    }

    // Public API for GUI
    public void refreshPrices() {
        if (isRefreshing) {
            info("Already refreshing prices...");
            return;
        }

        if (apiKey.get().isEmpty()) {
            error("Please set your API key in the module settings!");
            return;
        }

        isRefreshing = true;
        lastError = null;
        info("Fetching auction house data...");

        MeteorExecutor.execute(() -> {
            try {
                fetchAuctionData();
                calculateProfitableCrafts();
                lastRefreshTime = System.currentTimeMillis();
                info("Found " + profitableRecipesFound + " profitable crafts out of " + totalRecipesChecked + " recipes!");

                // Open GUI after refresh completes (if setting enabled)
                if (openGuiOnActivate.get()) {
                    mc.execute(this::openGui);
                }
            } catch (Exception e) {
                lastError = e.getMessage();
                error("Failed to refresh prices: " + e.getMessage());
                e.printStackTrace();
            } finally {
                isRefreshing = false;
            }
        });
    }

    private void fetchAuctionData() {
        itemPrices.clear();

        // Get all unique item IDs from recipes
        Set<String> itemsToQuery = new HashSet<>();
        List<CraftingRecipe> recipes = RecipeDatabase.getAllRecipes();

        for (CraftingRecipe recipe : recipes) {
            // Add result item
            itemsToQuery.add(CraftingRecipe.normalizeId(recipe.resultId));
            // Add all ingredient items
            for (String ingredientId : recipe.ingredients.keySet()) {
                itemsToQuery.add(CraftingRecipe.normalizeId(ingredientId));
            }
        }

        info("Querying lowest prices for " + itemsToQuery.size() + " unique items...");

        int queriedCount = 0;
        int foundCount = 0;
        int notFoundCount = 0;

        for (String itemId : itemsToQuery) {
            queriedCount++;

            // Progress update every 50 items
            if (queriedCount % 50 == 0) {
                info("Progress: " + queriedCount + "/" + itemsToQuery.size() + " items queried (" + foundCount + " found)");
            }

            Double lowestPrice = fetchLowestPriceForItem(itemId);

            if (lowestPrice != null) {
                itemPrices.put(itemId, lowestPrice);
                foundCount++;
            } else {
                notFoundCount++;
            }

            // Rate limiting: Use configurable delay
            try {
                Thread.sleep(pageDelay.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                warning("Price fetching interrupted");
                break;
            }
        }

        info("Completed! Queried " + queriedCount + " items: " + foundCount + " found, " + notFoundCount + " not on auction house");
        info("Tracking lowest prices for " + itemPrices.size() + " unique items");
    }

    private Double fetchLowestPriceForItem(String itemId) {
        try {
            // Query first page sorted by lowest price for this specific item
            String url = "https://api.donutsmp.net/v1/auction/list/1";

            // Create search request body (API uses GET with body, which is non-standard but supported)
            String requestBody = "{\"search\":\"" + itemId + "\",\"sort\":\"lowest_price\"}";

            DonutAuctionResponse response = Http.get(url)
                .bearer(apiKey.get())
                .bodyString(requestBody)
                .header("Content-Type", "application/json")
                .sendJson(DonutAuctionResponse.class);

            if (response != null && response.status == 200 && response.result != null && !response.result.isEmpty()) {
                // Search through all results on first page to find exact match
                for (DonutAuctionResponse.AuctionEntry entry : response.result) {
                    if (entry.item != null && entry.item.id != null) {
                        String entryItemId = CraftingRecipe.normalizeId(entry.item.id);

                        // Check for exact match
                        if (entryItemId.equalsIgnoreCase(itemId)) {
                            // Calculate price per item
                            double pricePerItem = entry.price / entry.item.count;
                            return pricePerItem;
                        }
                    }
                }

                // If no exact match found, try first result if it contains our search term
                DonutAuctionResponse.AuctionEntry firstEntry = response.result.get(0);
                if (firstEntry.item != null && firstEntry.item.id != null) {
                    String entryItemId = CraftingRecipe.normalizeId(firstEntry.item.id);
                    if (entryItemId.contains(itemId) || itemId.contains(entryItemId)) {
                        double pricePerItem = firstEntry.price / firstEntry.item.count;
                        return pricePerItem;
                    }
                }
            }

            return null; // Item not found or no results
        } catch (Exception e) {
            // Silently skip items that cause errors
            return null;
        }
    }

    private void calculateProfitableCrafts() {
        List<ProfitableCraft> allCrafts = new ArrayList<>();
        List<CraftingRecipe> recipes = RecipeDatabase.getAllRecipes();
        totalRecipesChecked = recipes.size();
        profitableRecipesFound = 0;
        int skippedMissingPrices = 0;

        for (CraftingRecipe recipe : recipes) {
            ProfitableCraft craft = calculateCraftProfit(recipe);
            if (craft != null) {
                allCrafts.add(craft);
                if (craft.isProfitable()) profitableRecipesFound++;
            } else {
                skippedMissingPrices++;
            }
        }

        info("Calculated " + allCrafts.size() + " crafts (" + profitableRecipesFound + " profitable, " + skippedMissingPrices + " skipped due to missing prices)");

        // Sort by profit (descending)
        Collections.sort(allCrafts);

        // Apply filters
        List<ProfitableCraft> filteredCrafts = new ArrayList<>();
        for (ProfitableCraft craft : allCrafts) {
            if (!showOnlyProfitable.get() || craft.isProfitable()) {
                if (craft.profit >= minProfit.get() && craft.profitPercentage >= minProfitPercent.get()) {
                    filteredCrafts.add(craft);
                }
            }
        }

        // Limit results
        if (filteredCrafts.size() > maxResults.get()) {
            filteredCrafts = filteredCrafts.subList(0, maxResults.get());
        }

        profitableCrafts = filteredCrafts;

        // Show top 5 regardless of filters for debugging
        if (allCrafts.size() > 0) {
            info("Top 5 crafts by profit:");
            for (int i = 0; i < Math.min(5, allCrafts.size()); i++) {
                ProfitableCraft craft = allCrafts.get(i);
                info("  " + (i+1) + ". " + craft.toString());
            }
        }
    }

    private ProfitableCraft calculateCraftProfit(CraftingRecipe recipe) {
        // Calculate cost to buy all ingredients
        double totalCost = 0;
        Map<String, Double> ingredientPrices = new HashMap<>();

        for (Map.Entry<String, Integer> ingredient : recipe.ingredients.entrySet()) {
            String itemId = CraftingRecipe.normalizeId(ingredient.getKey());
            Double pricePerItem = itemPrices.get(itemId);

            if (pricePerItem == null) {
                // Can't calculate if we don't know the price
                return null;
            }

            double cost = pricePerItem * ingredient.getValue();
            totalCost += cost;
            ingredientPrices.put(itemId, pricePerItem);
        }

        // Calculate sell price for result
        String resultId = CraftingRecipe.normalizeId(recipe.resultId);
        Double resultPricePerItem = itemPrices.get(resultId);

        if (resultPricePerItem == null) {
            // Can't calculate if we don't know the sell price
            return null;
        }

        double totalSellPrice = resultPricePerItem * recipe.resultCount;

        ProfitableCraft craft = new ProfitableCraft(recipe, totalCost, totalSellPrice);
        craft.ingredientPrices.putAll(ingredientPrices);
        return craft;
    }

    public void openGui() {
        if (mc.currentScreen instanceof CraftingProfitScreenImpl) {
            return; // Already open
        }
        mc.setScreen(new CraftingProfitScreenImpl(GuiThemes.get(), this));
    }

    // Getters for GUI
    public List<ProfitableCraft> getProfitableCrafts() {
        return new ArrayList<>(profitableCrafts);
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    public long getLastRefreshTime() {
        return lastRefreshTime;
    }

    public String getLastError() {
        return lastError;
    }

    public int getTotalRecipesChecked() {
        return totalRecipesChecked;
    }

    public int getProfitableRecipesFound() {
        return profitableRecipesFound;
    }

    public int getItemPricesCount() {
        return itemPrices.size();
    }

    @Override
    public String getInfoString() {
        if (profitableRecipesFound > 0) {
            return profitableRecipesFound + " profitable";
        }
        return null;
    }
}
