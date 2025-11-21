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

        // Quick filter buttons for common result counts
        header.add(theme.label("Show: "));
        WButton top5Btn = header.add(theme.button("Top 5")).widget();
        top5Btn.action = () -> {
            module.setMaxResults(5);
            reload();
        };
        WButton top10Btn = header.add(theme.button("Top 10")).widget();
        top10Btn.action = () -> {
            module.setMaxResults(10);
            reload();
        };
        WButton top20Btn = header.add(theme.button("Top 20")).widget();
        top20Btn.action = () -> {
            module.setMaxResults(20);
            reload();
        };
        WButton allBtn = header.add(theme.button("All")).widget();
        allBtn.action = () -> {
            module.setMaxResults(100);
            reload();
        };

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
        // Calculate color based on profit percentage (gradient from dark green to bright green)
        String profitColor = getProfitColor(craft.profitPercentage);

        // Rank
        craftTable.add(theme.label(String.format("§f%d", rank))).expandCellX();

        // Item name
        craftTable.add(theme.label(String.format("§f%s §7x%d",
            craft.recipe.resultName, craft.recipe.resultCount))).expandCellX();

        // Cost (gray)
        craftTable.add(theme.label(String.format("§7$%s", craft.getFormattedCost()))).expandCellX();

        // Sell price (gray)
        craftTable.add(theme.label(String.format("§7$%s", craft.getFormattedSellPrice()))).expandCellX();

        // Profit (colored with gradient)
        craftTable.add(theme.label(String.format("%s$%s", profitColor, craft.getFormattedProfit()))).expandCellX();

        // Percentage (colored with gradient)
        craftTable.add(theme.label(String.format("%s%s", profitColor, craft.getFormattedProfitPercentage()))).expandCellX();

        // Recipe details
        WButton detailsBtn = craftTable.add(theme.button("Details")).widget();
        detailsBtn.action = () -> showRecipeDetails(craft);

        craftTable.row();
    }

    /**
     * Returns a color code based on profit percentage
     * Green gradient: darker green for low profit, brighter green for high profit
     */
    private String getProfitColor(double profitPercentage) {
        if (profitPercentage <= 0) {
            return "§c"; // Red for losses
        } else if (profitPercentage < 5) {
            return "§2"; // Dark green (0-5%)
        } else if (profitPercentage < 15) {
            return "§a"; // Green (5-15%)
        } else if (profitPercentage < 30) {
            return "§a§l"; // Bold green (15-30%)
        } else {
            return "§a§n"; // Bright underlined green (30%+)
        }
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
