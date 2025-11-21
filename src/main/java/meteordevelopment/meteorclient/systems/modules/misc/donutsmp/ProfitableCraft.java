package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import java.util.HashMap;
import java.util.Map;

public class ProfitableCraft implements Comparable<ProfitableCraft> {
    public final CraftingRecipe recipe;
    public final double costToBuy;
    public final double sellPrice;
    public final double profit;
    public final double profitPercentage;
    public final Map<String, Double> ingredientPrices;
    public final double resultPrice;

    public ProfitableCraft(CraftingRecipe recipe, double costToBuy, double sellPrice) {
        this.recipe = recipe;
        this.costToBuy = costToBuy;
        this.sellPrice = sellPrice;
        this.profit = sellPrice - costToBuy;
        this.profitPercentage = costToBuy > 0 ? (profit / costToBuy) * 100 : 0;
        this.ingredientPrices = new HashMap<>();
        this.resultPrice = sellPrice / recipe.resultCount;
    }

    public boolean isProfitable() {
        return profit > 0;
    }

    @Override
    public int compareTo(ProfitableCraft other) {
        // Sort by profit (descending)
        return Double.compare(other.profit, this.profit);
    }

    public String getFormattedProfit() {
        return String.format("%.2f", profit);
    }

    public String getFormattedProfitPercentage() {
        return String.format("%.1f%%", profitPercentage);
    }

    public String getFormattedCost() {
        return String.format("%.2f", costToBuy);
    }

    public String getFormattedSellPrice() {
        return String.format("%.2f", sellPrice);
    }

    @Override
    public String toString() {
        return String.format("%s: Buy $%s -> Craft -> Sell $%s = Profit $%s (%s)",
            recipe.resultName,
            getFormattedCost(),
            getFormattedSellPrice(),
            getFormattedProfit(),
            getFormattedProfitPercentage());
    }
}
