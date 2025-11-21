package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import java.util.HashMap;
import java.util.Map;

public class CraftingRecipe {
    public final String resultId;
    public final String resultName;
    public final int resultCount;
    public final Map<String, Integer> ingredients;

    public CraftingRecipe(String resultId, String resultName, int resultCount) {
        this.resultId = resultId;
        this.resultName = resultName;
        this.resultCount = resultCount;
        this.ingredients = new HashMap<>();
    }

    public CraftingRecipe ingredient(String itemId, int count) {
        ingredients.put(itemId, count);
        return this;
    }

    // Normalize item IDs (remove minecraft: prefix if present)
    public static String normalizeId(String id) {
        if (id == null) return null;
        return id.replace("minecraft:", "");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(resultName).append(" (").append(resultCount).append(")");
        sb.append(" from: ");
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            sb.append(entry.getValue()).append("x ").append(entry.getKey()).append(", ");
        }
        return sb.toString();
    }
}
