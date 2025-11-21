package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.WindowScreen;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.systems.modules.misc.CraftingProfit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CraftingProfitScreenImpl extends WindowScreen {
    private final CraftingProfit module;
    private WTable craftTable;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public CraftingProfitScreenImpl(GuiTheme theme, CraftingProfit module) {
        super(theme, "DonutSMP Crafting Profit Calculator");
        this.module = module;
    }

    @Override
    public void initWidgets() {
        // Header with status info
        WHorizontalList header = add(theme.horizontalList()).expandX().widget();

        String statusText = module.isRefreshing() ? "Refreshing..." :
            (module.getLastRefreshTime() > 0 ? "Last refresh: " + dateFormat.format(new Date(module.getLastRefreshTime())) : "Not refreshed yet");
        header.add(theme.label(statusText));
        header.add(theme.horizontalSeparator()).expandX();

        WButton refreshBtn = header.add(theme.button("Refresh")).widget();
        refreshBtn.action = () -> {
            module.refreshPrices();
            reload();
        };

        // Stats row
        if (module.getLastRefreshTime() > 0) {
            add(theme.horizontalSeparator()).expandX();
            WHorizontalList stats = add(theme.horizontalList()).expandX().widget();
            stats.add(theme.label(String.format("Items tracked: %d", module.getItemPricesCount())));
            stats.add(theme.label(" | "));
            stats.add(theme.label(String.format("Recipes checked: %d", module.getTotalRecipesChecked())));
            stats.add(theme.label(" | "));
            stats.add(theme.label(String.format("Profitable: %d", module.getProfitableRecipesFound())));
        }

        // Error message if any
        if (module.getLastError() != null) {
            add(theme.horizontalSeparator()).expandX();
            add(theme.label("Error: " + module.getLastError())).expandX();
        }

        add(theme.horizontalSeparator()).expandX();

        // Instructions
        add(theme.label("Top Profitable Crafts (Buy materials -> Craft -> Sell result)")).expandX();
        add(theme.horizontalSeparator()).expandX();

        // Craft table
        List<ProfitableCraft> crafts = module.getProfitableCrafts();

        if (crafts.isEmpty()) {
            if (module.isRefreshing()) {
                add(theme.label("Loading auction data...")).expandX();
            } else if (module.getLastRefreshTime() == 0) {
                add(theme.label("Click Refresh to fetch auction data")).expandX();
            } else {
                add(theme.label("No profitable crafts found with current filters")).expandX();
            }
        } else {
            craftTable = add(theme.table()).expandX().widget();

            // Table header
            craftTable.add(theme.label("Rank")).expandCellX();
            craftTable.add(theme.label("Item")).expandCellX();
            craftTable.add(theme.label("Materials Cost")).expandCellX();
            craftTable.add(theme.label("Sell Price")).expandCellX();
            craftTable.add(theme.label("Profit")).expandCellX();
            craftTable.add(theme.label("%")).expandCellX();
            craftTable.add(theme.label("Recipe")).expandCellX();
            craftTable.row();

            craftTable.add(theme.horizontalSeparator()).expandCellX();
            craftTable.row();

            // Craft rows
            int rank = 1;
            for (ProfitableCraft craft : crafts) {
                addCraftRow(rank++, craft);
            }
        }
    }

    private void addCraftRow(int rank, ProfitableCraft craft) {
        // Rank
        craftTable.add(theme.label(String.valueOf(rank)));

        // Item name
        craftTable.add(theme.label(craft.recipe.resultName + " x" + craft.recipe.resultCount));

        // Cost
        String costColor = "§7";
        craftTable.add(theme.label(costColor + "$" + craft.getFormattedCost()));

        // Sell price
        craftTable.add(theme.label("§7$" + craft.getFormattedSellPrice()));

        // Profit (colored)
        String profitColor = craft.isProfitable() ? "§a" : "§c";
        craftTable.add(theme.label(profitColor + "$" + craft.getFormattedProfit()));

        // Percentage (colored)
        craftTable.add(theme.label(profitColor + craft.getFormattedProfitPercentage()));

        // Recipe details
        WButton detailsBtn = craftTable.add(theme.button("Details")).widget();
        detailsBtn.action = () -> showRecipeDetails(craft);

        craftTable.row();
    }

    private void showRecipeDetails(ProfitableCraft craft) {
        // Create a popup with detailed recipe information
        RecipeDetailsScreen detailsScreen = new RecipeDetailsScreen(theme, craft);
        client.setScreen(detailsScreen);
    }

    // Helper class for showing recipe details
    public static class RecipeDetailsScreen extends WindowScreen {
        private final ProfitableCraft craft;

        public RecipeDetailsScreen(GuiTheme theme, ProfitableCraft craft) {
            super(theme, craft.recipe.resultName + " - Recipe Details");
            this.craft = craft;
        }

        @Override
        public void initWidgets() {
            // Result
            add(theme.label("Result:")).expandX();
            WTable resultTable = add(theme.table()).expandX().widget();
            resultTable.add(theme.label("  " + craft.recipe.resultName + " x" + craft.recipe.resultCount));
            resultTable.add(theme.label("($" + craft.getFormattedSellPrice() + " total)"));
            resultTable.row();
            resultTable.add(theme.label("  Unit price: $" + String.format("%.2f", craft.resultPrice)));
            resultTable.row();

            add(theme.horizontalSeparator()).expandX();

            // Ingredients
            add(theme.label("Materials needed:")).expandX();
            WTable ingredientsTable = add(theme.table()).expandX().widget();

            for (Map.Entry<String, Integer> entry : craft.recipe.ingredients.entrySet()) {
                String itemId = entry.getKey();
                int count = entry.getValue();
                String normalizedId = CraftingRecipe.normalizeId(itemId);
                Double pricePerItem = craft.ingredientPrices.get(normalizedId);

                if (pricePerItem != null) {
                    double totalCost = pricePerItem * count;
                    ingredientsTable.add(theme.label("  " + itemId + " x" + count));
                    ingredientsTable.add(theme.label("(@$" + String.format("%.2f", pricePerItem) + " each)"));
                    ingredientsTable.add(theme.label("= $" + String.format("%.2f", totalCost)));
                } else {
                    ingredientsTable.add(theme.label("  " + itemId + " x" + count));
                    ingredientsTable.add(theme.label("(price unknown)"));
                }
                ingredientsTable.row();
            }

            add(theme.horizontalSeparator()).expandX();

            // Summary
            add(theme.label("Summary:")).expandX();
            WTable summaryTable = add(theme.table()).expandX().widget();
            summaryTable.add(theme.label("  Total cost:"));
            summaryTable.add(theme.label("$" + craft.getFormattedCost()));
            summaryTable.row();
            summaryTable.add(theme.label("  Sell price:"));
            summaryTable.add(theme.label("$" + craft.getFormattedSellPrice()));
            summaryTable.row();
            summaryTable.add(theme.label("  Profit:"));
            String profitColor = craft.isProfitable() ? "§a" : "§c";
            summaryTable.add(theme.label(profitColor + "$" + craft.getFormattedProfit() + " (" + craft.getFormattedProfitPercentage() + ")"));
            summaryTable.row();

            add(theme.horizontalSeparator()).expandX();

            // Close button
            WButton closeBtn = add(theme.button("Close")).expandX().widget();
            closeBtn.action = this::close;
        }
    }
}
