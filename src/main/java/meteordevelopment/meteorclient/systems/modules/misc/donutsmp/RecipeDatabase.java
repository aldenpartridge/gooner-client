package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import java.util.ArrayList;
import java.util.List;

public class RecipeDatabase {
    private static final List<CraftingRecipe> RECIPES = new ArrayList<>();

    static {
        // Blocks -> Ingots/Items (Decrafting)
        addRecipe("iron_ingot", "Iron Ingot", 9).ingredient("iron_block", 1);
        addRecipe("gold_ingot", "Gold Ingot", 9).ingredient("gold_block", 1);
        addRecipe("diamond", "Diamond", 9).ingredient("diamond_block", 1);
        addRecipe("emerald", "Emerald", 9).ingredient("emerald_block", 1);
        addRecipe("netherite_ingot", "Netherite Ingot", 9).ingredient("netherite_block", 1);
        addRecipe("coal", "Coal", 9).ingredient("coal_block", 1);
        addRecipe("redstone", "Redstone", 9).ingredient("redstone_block", 1);
        addRecipe("lapis_lazuli", "Lapis Lazuli", 9).ingredient("lapis_block", 1);
        addRecipe("copper_ingot", "Copper Ingot", 9).ingredient("copper_block", 1);
        addRecipe("raw_iron", "Raw Iron", 9).ingredient("raw_iron_block", 1);
        addRecipe("raw_gold", "Raw Gold", 9).ingredient("raw_gold_block", 1);
        addRecipe("raw_copper", "Raw Copper", 9).ingredient("raw_copper_block", 1);
        addRecipe("amethyst_shard", "Amethyst Shard", 4).ingredient("amethyst_block", 1);
        addRecipe("quartz", "Quartz", 4).ingredient("quartz_block", 1);

        // Ingots/Items -> Blocks (Crafting)
        addRecipe("iron_block", "Iron Block", 1).ingredient("iron_ingot", 9);
        addRecipe("gold_block", "Gold Block", 1).ingredient("gold_ingot", 9);
        addRecipe("diamond_block", "Diamond Block", 1).ingredient("diamond", 9);
        addRecipe("emerald_block", "Emerald Block", 1).ingredient("emerald", 9);
        addRecipe("netherite_block", "Netherite Block", 1).ingredient("netherite_ingot", 9);
        addRecipe("coal_block", "Coal Block", 1).ingredient("coal", 9);
        addRecipe("redstone_block", "Redstone Block", 1).ingredient("redstone", 9);
        addRecipe("lapis_block", "Lapis Block", 1).ingredient("lapis_lazuli", 9);
        addRecipe("copper_block", "Copper Block", 1).ingredient("copper_ingot", 9);
        addRecipe("raw_iron_block", "Raw Iron Block", 1).ingredient("raw_iron", 9);
        addRecipe("raw_gold_block", "Raw Gold Block", 1).ingredient("raw_gold", 9);
        addRecipe("raw_copper_block", "Raw Copper Block", 1).ingredient("raw_copper", 9);
        addRecipe("amethyst_block", "Amethyst Block", 1).ingredient("amethyst_shard", 4);
        addRecipe("quartz_block", "Quartz Block", 1).ingredient("quartz", 4);

        // Ingots -> Nuggets
        addRecipe("iron_nugget", "Iron Nugget", 9).ingredient("iron_ingot", 1);
        addRecipe("gold_nugget", "Gold Nugget", 9).ingredient("gold_ingot", 1);

        // Nuggets -> Ingots
        addRecipe("iron_ingot", "Iron Ingot", 1).ingredient("iron_nugget", 9);
        addRecipe("gold_ingot", "Gold Ingot", 1).ingredient("gold_nugget", 9);

        // Smelting (Raw -> Ingots)
        addRecipe("iron_ingot", "Iron Ingot", 1).ingredient("raw_iron", 1);
        addRecipe("gold_ingot", "Gold Ingot", 1).ingredient("raw_gold", 1);
        addRecipe("copper_ingot", "Copper Ingot", 1).ingredient("raw_copper", 1);

        // Netherite crafting
        addRecipe("netherite_ingot", "Netherite Ingot", 1)
            .ingredient("netherite_scrap", 4)
            .ingredient("gold_ingot", 4);

        // Wood planks
        addRecipe("oak_planks", "Oak Planks", 4).ingredient("oak_log", 1);
        addRecipe("spruce_planks", "Spruce Planks", 4).ingredient("spruce_log", 1);
        addRecipe("birch_planks", "Birch Planks", 4).ingredient("birch_log", 1);
        addRecipe("jungle_planks", "Jungle Planks", 4).ingredient("jungle_log", 1);
        addRecipe("acacia_planks", "Acacia Planks", 4).ingredient("acacia_log", 1);
        addRecipe("dark_oak_planks", "Dark Oak Planks", 4).ingredient("dark_oak_log", 1);
        addRecipe("mangrove_planks", "Mangrove Planks", 4).ingredient("mangrove_log", 1);
        addRecipe("cherry_planks", "Cherry Planks", 4).ingredient("cherry_log", 1);
        addRecipe("bamboo_planks", "Bamboo Planks", 2).ingredient("bamboo_block", 1);
        addRecipe("crimson_planks", "Crimson Planks", 4).ingredient("crimson_stem", 1);
        addRecipe("warped_planks", "Warped Planks", 4).ingredient("warped_stem", 1);

        // Sticks
        addRecipe("stick", "Stick", 4).ingredient("oak_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("spruce_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("birch_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("jungle_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("acacia_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("dark_oak_planks", 2);

        // Tools - Iron
        addRecipe("iron_pickaxe", "Iron Pickaxe", 1)
            .ingredient("iron_ingot", 3)
            .ingredient("stick", 2);
        addRecipe("iron_axe", "Iron Axe", 1)
            .ingredient("iron_ingot", 3)
            .ingredient("stick", 2);
        addRecipe("iron_shovel", "Iron Shovel", 1)
            .ingredient("iron_ingot", 1)
            .ingredient("stick", 2);
        addRecipe("iron_hoe", "Iron Hoe", 1)
            .ingredient("iron_ingot", 2)
            .ingredient("stick", 2);
        addRecipe("iron_sword", "Iron Sword", 1)
            .ingredient("iron_ingot", 2)
            .ingredient("stick", 1);

        // Tools - Diamond
        addRecipe("diamond_pickaxe", "Diamond Pickaxe", 1)
            .ingredient("diamond", 3)
            .ingredient("stick", 2);
        addRecipe("diamond_axe", "Diamond Axe", 1)
            .ingredient("diamond", 3)
            .ingredient("stick", 2);
        addRecipe("diamond_shovel", "Diamond Shovel", 1)
            .ingredient("diamond", 1)
            .ingredient("stick", 2);
        addRecipe("diamond_hoe", "Diamond Hoe", 1)
            .ingredient("diamond", 2)
            .ingredient("stick", 2);
        addRecipe("diamond_sword", "Diamond Sword", 1)
            .ingredient("diamond", 2)
            .ingredient("stick", 1);

        // Tools - Gold
        addRecipe("golden_pickaxe", "Golden Pickaxe", 1)
            .ingredient("gold_ingot", 3)
            .ingredient("stick", 2);
        addRecipe("golden_axe", "Golden Axe", 1)
            .ingredient("gold_ingot", 3)
            .ingredient("stick", 2);
        addRecipe("golden_shovel", "Golden Shovel", 1)
            .ingredient("gold_ingot", 1)
            .ingredient("stick", 2);
        addRecipe("golden_hoe", "Golden Hoe", 1)
            .ingredient("gold_ingot", 2)
            .ingredient("stick", 2);
        addRecipe("golden_sword", "Golden Sword", 1)
            .ingredient("gold_ingot", 2)
            .ingredient("stick", 1);

        // Armor - Iron
        addRecipe("iron_helmet", "Iron Helmet", 1).ingredient("iron_ingot", 5);
        addRecipe("iron_chestplate", "Iron Chestplate", 1).ingredient("iron_ingot", 8);
        addRecipe("iron_leggings", "Iron Leggings", 1).ingredient("iron_ingot", 7);
        addRecipe("iron_boots", "Iron Boots", 1).ingredient("iron_ingot", 4);

        // Armor - Diamond
        addRecipe("diamond_helmet", "Diamond Helmet", 1).ingredient("diamond", 5);
        addRecipe("diamond_chestplate", "Diamond Chestplate", 1).ingredient("diamond", 8);
        addRecipe("diamond_leggings", "Diamond Leggings", 1).ingredient("diamond", 7);
        addRecipe("diamond_boots", "Diamond Boots", 1).ingredient("diamond", 4);

        // Armor - Gold
        addRecipe("golden_helmet", "Golden Helmet", 1).ingredient("gold_ingot", 5);
        addRecipe("golden_chestplate", "Golden Chestplate", 1).ingredient("gold_ingot", 8);
        addRecipe("golden_leggings", "Golden Leggings", 1).ingredient("gold_ingot", 7);
        addRecipe("golden_boots", "Golden Boots", 1).ingredient("gold_ingot", 4);

        // Building blocks
        addRecipe("bricks", "Bricks", 1).ingredient("brick", 4);
        addRecipe("nether_bricks", "Nether Bricks", 1).ingredient("nether_brick", 4);
        addRecipe("clay", "Clay", 1).ingredient("clay_ball", 4);
        addRecipe("glowstone", "Glowstone", 1).ingredient("glowstone_dust", 4);
        addRecipe("snow_block", "Snow Block", 1).ingredient("snowball", 4);

        // Slabs (2 slabs -> 1 block)
        addRecipe("stone", "Stone", 1).ingredient("stone_slab", 2);
        addRecipe("oak_planks", "Oak Planks", 1).ingredient("oak_slab", 2);
        addRecipe("cobblestone", "Cobblestone", 1).ingredient("cobblestone_slab", 2);

        // Stairs (6 stairs from 6 blocks, so 1.5 blocks -> 1 stairs is not profitable usually)
        // Omitting stairs as they're usually not profitable

        // Redstone components
        addRecipe("repeater", "Redstone Repeater", 1)
            .ingredient("redstone", 1)
            .ingredient("redstone_torch", 2)
            .ingredient("stone", 3);
        addRecipe("comparator", "Redstone Comparator", 1)
            .ingredient("redstone_torch", 3)
            .ingredient("quartz", 1)
            .ingredient("stone", 3);

        // Misc useful crafts
        addRecipe("ender_chest", "Ender Chest", 1)
            .ingredient("obsidian", 8)
            .ingredient("ender_eye", 1);
        addRecipe("enchanting_table", "Enchanting Table", 1)
            .ingredient("book", 1)
            .ingredient("diamond", 2)
            .ingredient("obsidian", 4);
        addRecipe("anvil", "Anvil", 1)
            .ingredient("iron_block", 3)
            .ingredient("iron_ingot", 4);
        addRecipe("beacon", "Beacon", 1)
            .ingredient("glass", 5)
            .ingredient("obsidian", 3)
            .ingredient("nether_star", 1);

        // Food
        addRecipe("bread", "Bread", 1).ingredient("wheat", 3);
        addRecipe("cake", "Cake", 1)
            .ingredient("milk_bucket", 3)
            .ingredient("sugar", 2)
            .ingredient("egg", 1)
            .ingredient("wheat", 3);
        addRecipe("cookie", "Cookie", 8)
            .ingredient("wheat", 2)
            .ingredient("cocoa_beans", 1);
        addRecipe("golden_apple", "Golden Apple", 1)
            .ingredient("gold_ingot", 8)
            .ingredient("apple", 1);
        addRecipe("enchanted_golden_apple", "Enchanted Golden Apple", 1)
            .ingredient("gold_block", 8)
            .ingredient("apple", 1);

        // Dyes
        addRecipe("orange_dye", "Orange Dye", 2)
            .ingredient("red_dye", 1)
            .ingredient("yellow_dye", 1);
        addRecipe("magenta_dye", "Magenta Dye", 2)
            .ingredient("purple_dye", 1)
            .ingredient("pink_dye", 1);
        addRecipe("light_blue_dye", "Light Blue Dye", 2)
            .ingredient("blue_dye", 1)
            .ingredient("white_dye", 1);
        addRecipe("lime_dye", "Lime Dye", 2)
            .ingredient("green_dye", 1)
            .ingredient("white_dye", 1);
        addRecipe("pink_dye", "Pink Dye", 2)
            .ingredient("red_dye", 1)
            .ingredient("white_dye", 1);
        addRecipe("gray_dye", "Gray Dye", 2)
            .ingredient("black_dye", 1)
            .ingredient("white_dye", 1);
        addRecipe("light_gray_dye", "Light Gray Dye", 2)
            .ingredient("gray_dye", 1)
            .ingredient("white_dye", 1);
        addRecipe("cyan_dye", "Cyan Dye", 2)
            .ingredient("blue_dye", 1)
            .ingredient("green_dye", 1);
        addRecipe("purple_dye", "Purple Dye", 2)
            .ingredient("blue_dye", 1)
            .ingredient("red_dye", 1);

        // Concrete powder
        addRecipe("white_concrete_powder", "White Concrete Powder", 8)
            .ingredient("white_dye", 1)
            .ingredient("sand", 4)
            .ingredient("gravel", 4);
    }

    private static CraftingRecipe addRecipe(String resultId, String resultName, int resultCount) {
        CraftingRecipe recipe = new CraftingRecipe(resultId, resultName, resultCount);
        RECIPES.add(recipe);
        return recipe;
    }

    public static List<CraftingRecipe> getAllRecipes() {
        return new ArrayList<>(RECIPES);
    }
}
