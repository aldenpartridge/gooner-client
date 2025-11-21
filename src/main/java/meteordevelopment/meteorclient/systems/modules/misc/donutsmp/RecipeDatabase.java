package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import java.util.ArrayList;
import java.util.List;

public class RecipeDatabase {
    private static final List<CraftingRecipe> RECIPES = new ArrayList<>();

    static {
        // ============ STORAGE BLOCKS (Crafting & Decrafting) ============
        // Blocks -> Items (Decrafting - usually more profitable)
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
        addRecipe("slime_ball", "Slime Ball", 9).ingredient("slime_block", 1);
        addRecipe("honey_bottle", "Honey Bottle", 4).ingredient("honey_block", 1);
        addRecipe("wheat", "Wheat", 9).ingredient("hay_block", 1);
        addRecipe("dried_kelp", "Dried Kelp", 9).ingredient("dried_kelp_block", 1);
        addRecipe("bone_meal", "Bone Meal", 9).ingredient("bone_block", 1);

        // Items -> Blocks (Crafting)
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
        addRecipe("slime_block", "Slime Block", 1).ingredient("slime_ball", 9);
        addRecipe("honey_block", "Honey Block", 1).ingredient("honey_bottle", 4);
        addRecipe("hay_block", "Hay Block", 1).ingredient("wheat", 9);
        addRecipe("dried_kelp_block", "Dried Kelp Block", 1).ingredient("dried_kelp", 9);
        addRecipe("bone_block", "Bone Block", 1).ingredient("bone_meal", 9);

        // ============ NUGGETS & INGOTS ============
        // Ingots -> Nuggets
        addRecipe("iron_nugget", "Iron Nugget", 9).ingredient("iron_ingot", 1);
        addRecipe("gold_nugget", "Gold Nugget", 9).ingredient("gold_ingot", 1);

        // Nuggets -> Ingots
        addRecipe("iron_ingot", "Iron Ingot", 1).ingredient("iron_nugget", 9);
        addRecipe("gold_ingot", "Gold Ingot", 1).ingredient("gold_nugget", 9);

        // ============ SMELTING RECIPES ============
        // Raw Materials -> Ingots (Furnace/Blast Furnace)
        addRecipe("iron_ingot", "Iron Ingot (Smelted)", 1).ingredient("raw_iron", 1);
        addRecipe("gold_ingot", "Gold Ingot (Smelted)", 1).ingredient("raw_gold", 1);
        addRecipe("copper_ingot", "Copper Ingot (Smelted)", 1).ingredient("raw_copper", 1);
        addRecipe("iron_ingot", "Iron Ingot (from Ore)", 1).ingredient("iron_ore", 1);
        addRecipe("gold_ingot", "Gold Ingot (from Ore)", 1).ingredient("gold_ore", 1);
        addRecipe("copper_ingot", "Copper Ingot (from Ore)", 1).ingredient("copper_ore", 1);
        addRecipe("iron_ingot", "Iron Ingot (from Deepslate)", 1).ingredient("deepslate_iron_ore", 1);
        addRecipe("gold_ingot", "Gold Ingot (from Deepslate)", 1).ingredient("deepslate_gold_ore", 1);
        addRecipe("copper_ingot", "Copper Ingot (from Deepslate)", 1).ingredient("deepslate_copper_ore", 1);
        addRecipe("gold_ingot", "Gold Ingot (from Nether)", 1).ingredient("nether_gold_ore", 1);
        addRecipe("netherite_scrap", "Netherite Scrap", 1).ingredient("ancient_debris", 1);

        // Netherite crafting
        addRecipe("netherite_ingot", "Netherite Ingot", 1)
            .ingredient("netherite_scrap", 4)
            .ingredient("gold_ingot", 4);

        // Other smelting
        addRecipe("glass", "Glass", 1).ingredient("sand", 1);
        addRecipe("brick", "Brick", 1).ingredient("clay_ball", 1);
        addRecipe("nether_brick", "Nether Brick", 1).ingredient("netherrack", 1);
        addRecipe("terracotta", "Terracotta", 1).ingredient("clay", 1);
        addRecipe("charcoal", "Charcoal", 1).ingredient("oak_log", 1);
        addRecipe("lime_dye", "Lime Dye (Smelted)", 1).ingredient("sea_pickle", 1);
        addRecipe("green_dye", "Green Dye (Smelted)", 1).ingredient("cactus", 1);

        // ============ WOOD & PLANKS ============
        // Logs -> Planks
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

        // Stripped Logs -> Planks
        addRecipe("oak_planks", "Oak Planks (Stripped)", 4).ingredient("stripped_oak_log", 1);
        addRecipe("spruce_planks", "Spruce Planks (Stripped)", 4).ingredient("stripped_spruce_log", 1);
        addRecipe("birch_planks", "Birch Planks (Stripped)", 4).ingredient("stripped_birch_log", 1);
        addRecipe("jungle_planks", "Jungle Planks (Stripped)", 4).ingredient("stripped_jungle_log", 1);
        addRecipe("acacia_planks", "Acacia Planks (Stripped)", 4).ingredient("stripped_acacia_log", 1);
        addRecipe("dark_oak_planks", "Dark Oak Planks (Stripped)", 4).ingredient("stripped_dark_oak_log", 1);
        addRecipe("mangrove_planks", "Mangrove Planks (Stripped)", 4).ingredient("stripped_mangrove_log", 1);
        addRecipe("cherry_planks", "Cherry Planks (Stripped)", 4).ingredient("stripped_cherry_log", 1);

        // Planks -> Sticks
        addRecipe("stick", "Stick", 4).ingredient("oak_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("spruce_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("birch_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("jungle_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("acacia_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("dark_oak_planks", 2);
        addRecipe("stick", "Stick", 4).ingredient("bamboo", 2);

        // Planks -> Other Wood Items
        addRecipe("bowl", "Bowl", 4).ingredient("oak_planks", 3);
        addRecipe("crafting_table", "Crafting Table", 1).ingredient("oak_planks", 4);
        addRecipe("chest", "Chest", 1).ingredient("oak_planks", 8);
        addRecipe("barrel", "Barrel", 1).ingredient("oak_planks", 6).ingredient("oak_slab", 2);

        // ============ TOOLS - WOOD ============
        addRecipe("wooden_pickaxe", "Wooden Pickaxe", 1).ingredient("oak_planks", 3).ingredient("stick", 2);
        addRecipe("wooden_axe", "Wooden Axe", 1).ingredient("oak_planks", 3).ingredient("stick", 2);
        addRecipe("wooden_shovel", "Wooden Shovel", 1).ingredient("oak_planks", 1).ingredient("stick", 2);
        addRecipe("wooden_hoe", "Wooden Hoe", 1).ingredient("oak_planks", 2).ingredient("stick", 2);
        addRecipe("wooden_sword", "Wooden Sword", 1).ingredient("oak_planks", 2).ingredient("stick", 1);

        // ============ TOOLS - STONE ============
        addRecipe("stone_pickaxe", "Stone Pickaxe", 1).ingredient("cobblestone", 3).ingredient("stick", 2);
        addRecipe("stone_axe", "Stone Axe", 1).ingredient("cobblestone", 3).ingredient("stick", 2);
        addRecipe("stone_shovel", "Stone Shovel", 1).ingredient("cobblestone", 1).ingredient("stick", 2);
        addRecipe("stone_hoe", "Stone Hoe", 1).ingredient("cobblestone", 2).ingredient("stick", 2);
        addRecipe("stone_sword", "Stone Sword", 1).ingredient("cobblestone", 2).ingredient("stick", 1);

        // ============ TOOLS - IRON ============
        addRecipe("iron_pickaxe", "Iron Pickaxe", 1).ingredient("iron_ingot", 3).ingredient("stick", 2);
        addRecipe("iron_axe", "Iron Axe", 1).ingredient("iron_ingot", 3).ingredient("stick", 2);
        addRecipe("iron_shovel", "Iron Shovel", 1).ingredient("iron_ingot", 1).ingredient("stick", 2);
        addRecipe("iron_hoe", "Iron Hoe", 1).ingredient("iron_ingot", 2).ingredient("stick", 2);
        addRecipe("iron_sword", "Iron Sword", 1).ingredient("iron_ingot", 2).ingredient("stick", 1);

        // ============ TOOLS - GOLD ============
        addRecipe("golden_pickaxe", "Golden Pickaxe", 1).ingredient("gold_ingot", 3).ingredient("stick", 2);
        addRecipe("golden_axe", "Golden Axe", 1).ingredient("gold_ingot", 3).ingredient("stick", 2);
        addRecipe("golden_shovel", "Golden Shovel", 1).ingredient("gold_ingot", 1).ingredient("stick", 2);
        addRecipe("golden_hoe", "Golden Hoe", 1).ingredient("gold_ingot", 2).ingredient("stick", 2);
        addRecipe("golden_sword", "Golden Sword", 1).ingredient("gold_ingot", 2).ingredient("stick", 1);

        // ============ TOOLS - DIAMOND ============
        addRecipe("diamond_pickaxe", "Diamond Pickaxe", 1).ingredient("diamond", 3).ingredient("stick", 2);
        addRecipe("diamond_axe", "Diamond Axe", 1).ingredient("diamond", 3).ingredient("stick", 2);
        addRecipe("diamond_shovel", "Diamond Shovel", 1).ingredient("diamond", 1).ingredient("stick", 2);
        addRecipe("diamond_hoe", "Diamond Hoe", 1).ingredient("diamond", 2).ingredient("stick", 2);
        addRecipe("diamond_sword", "Diamond Sword", 1).ingredient("diamond", 2).ingredient("stick", 1);

        // ============ ARMOR - LEATHER ============
        addRecipe("leather_helmet", "Leather Helmet", 1).ingredient("leather", 5);
        addRecipe("leather_chestplate", "Leather Chestplate", 1).ingredient("leather", 8);
        addRecipe("leather_leggings", "Leather Leggings", 1).ingredient("leather", 7);
        addRecipe("leather_boots", "Leather Boots", 1).ingredient("leather", 4);

        // ============ ARMOR - IRON ============
        addRecipe("iron_helmet", "Iron Helmet", 1).ingredient("iron_ingot", 5);
        addRecipe("iron_chestplate", "Iron Chestplate", 1).ingredient("iron_ingot", 8);
        addRecipe("iron_leggings", "Iron Leggings", 1).ingredient("iron_ingot", 7);
        addRecipe("iron_boots", "Iron Boots", 1).ingredient("iron_ingot", 4);

        // ============ ARMOR - GOLD ============
        addRecipe("golden_helmet", "Golden Helmet", 1).ingredient("gold_ingot", 5);
        addRecipe("golden_chestplate", "Golden Chestplate", 1).ingredient("gold_ingot", 8);
        addRecipe("golden_leggings", "Golden Leggings", 1).ingredient("gold_ingot", 7);
        addRecipe("golden_boots", "Golden Boots", 1).ingredient("gold_ingot", 4);

        // ============ ARMOR - DIAMOND ============
        addRecipe("diamond_helmet", "Diamond Helmet", 1).ingredient("diamond", 5);
        addRecipe("diamond_chestplate", "Diamond Chestplate", 1).ingredient("diamond", 8);
        addRecipe("diamond_leggings", "Diamond Leggings", 1).ingredient("diamond", 7);
        addRecipe("diamond_boots", "Diamond Boots", 1).ingredient("diamond", 4);

        // ============ ARMOR - CHAINMAIL (uncraftable but listed for completeness) ============
        // Chainmail cannot be crafted in survival

        // ============ BUILDING BLOCKS ============
        addRecipe("stone_bricks", "Stone Bricks", 1).ingredient("stone", 4);
        addRecipe("mossy_stone_bricks", "Mossy Stone Bricks", 1).ingredient("stone_bricks", 1).ingredient("vine", 1);
        addRecipe("chiseled_stone_bricks", "Chiseled Stone Bricks", 1).ingredient("stone_brick_slab", 2);
        addRecipe("bricks", "Bricks", 1).ingredient("brick", 4);
        addRecipe("nether_bricks", "Nether Bricks", 1).ingredient("nether_brick", 4);
        addRecipe("red_nether_bricks", "Red Nether Bricks", 1).ingredient("nether_wart", 2).ingredient("nether_brick", 2);
        addRecipe("clay", "Clay Block", 1).ingredient("clay_ball", 4);
        addRecipe("glowstone", "Glowstone", 1).ingredient("glowstone_dust", 4);
        addRecipe("snow_block", "Snow Block", 1).ingredient("snowball", 4);
        addRecipe("sandstone", "Sandstone", 1).ingredient("sand", 4);
        addRecipe("chiseled_sandstone", "Chiseled Sandstone", 1).ingredient("sandstone_slab", 2);
        addRecipe("cut_sandstone", "Cut Sandstone", 4).ingredient("sandstone", 4);
        addRecipe("red_sandstone", "Red Sandstone", 1).ingredient("red_sand", 4);
        addRecipe("prismarine", "Prismarine", 1).ingredient("prismarine_shard", 4);
        addRecipe("prismarine_bricks", "Prismarine Bricks", 1).ingredient("prismarine_shard", 9);
        addRecipe("dark_prismarine", "Dark Prismarine", 1).ingredient("prismarine_shard", 8).ingredient("black_dye", 1);
        addRecipe("sea_lantern", "Sea Lantern", 1).ingredient("prismarine_shard", 4).ingredient("prismarine_crystals", 5);
        addRecipe("purpur_block", "Purpur Block", 4).ingredient("popped_chorus_fruit", 4);
        addRecipe("purpur_pillar", "Purpur Pillar", 1).ingredient("purpur_slab", 2);
        addRecipe("end_stone_bricks", "End Stone Bricks", 4).ingredient("end_stone", 4);
        addRecipe("bookshelf", "Bookshelf", 1).ingredient("oak_planks", 6).ingredient("book", 3);
        addRecipe("quartz_bricks", "Quartz Bricks", 4).ingredient("quartz_block", 4);
        addRecipe("chiseled_quartz_block", "Chiseled Quartz Block", 1).ingredient("quartz_slab", 2);
        addRecipe("quartz_pillar", "Quartz Pillar", 2).ingredient("quartz_block", 2);

        // ============ WOOL & COLORED BLOCKS ============
        addRecipe("white_wool", "White Wool", 1).ingredient("string", 4);
        addRecipe("white_carpet", "White Carpet", 3).ingredient("white_wool", 2);
        addRecipe("white_bed", "White Bed", 1).ingredient("white_wool", 3).ingredient("oak_planks", 3);
        addRecipe("white_banner", "White Banner", 1).ingredient("white_wool", 6).ingredient("stick", 1);

        // Color dying wool (16 colors - showing pattern for a few)
        addRecipe("orange_wool", "Orange Wool", 1).ingredient("white_wool", 1).ingredient("orange_dye", 1);
        addRecipe("magenta_wool", "Magenta Wool", 1).ingredient("white_wool", 1).ingredient("magenta_dye", 1);
        addRecipe("light_blue_wool", "Light Blue Wool", 1).ingredient("white_wool", 1).ingredient("light_blue_dye", 1);
        addRecipe("yellow_wool", "Yellow Wool", 1).ingredient("white_wool", 1).ingredient("yellow_dye", 1);
        addRecipe("lime_wool", "Lime Wool", 1).ingredient("white_wool", 1).ingredient("lime_dye", 1);
        addRecipe("pink_wool", "Pink Wool", 1).ingredient("white_wool", 1).ingredient("pink_dye", 1);
        addRecipe("gray_wool", "Gray Wool", 1).ingredient("white_wool", 1).ingredient("gray_dye", 1);
        addRecipe("light_gray_wool", "Light Gray Wool", 1).ingredient("white_wool", 1).ingredient("light_gray_dye", 1);
        addRecipe("cyan_wool", "Cyan Wool", 1).ingredient("white_wool", 1).ingredient("cyan_dye", 1);
        addRecipe("purple_wool", "Purple Wool", 1).ingredient("white_wool", 1).ingredient("purple_dye", 1);
        addRecipe("blue_wool", "Blue Wool", 1).ingredient("white_wool", 1).ingredient("blue_dye", 1);
        addRecipe("brown_wool", "Brown Wool", 1).ingredient("white_wool", 1).ingredient("brown_dye", 1);
        addRecipe("green_wool", "Green Wool", 1).ingredient("white_wool", 1).ingredient("green_dye", 1);
        addRecipe("red_wool", "Red Wool", 1).ingredient("white_wool", 1).ingredient("red_dye", 1);
        addRecipe("black_wool", "Black Wool", 1).ingredient("white_wool", 1).ingredient("black_dye", 1);

        // Concrete powder
        addRecipe("white_concrete_powder", "White Concrete Powder", 8)
            .ingredient("white_dye", 1).ingredient("sand", 4).ingredient("gravel", 4);
        addRecipe("orange_concrete_powder", "Orange Concrete Powder", 8)
            .ingredient("orange_dye", 1).ingredient("sand", 4).ingredient("gravel", 4);
        addRecipe("magenta_concrete_powder", "Magenta Concrete Powder", 8)
            .ingredient("magenta_dye", 1).ingredient("sand", 4).ingredient("gravel", 4);

        // ============ DYES ============
        addRecipe("orange_dye", "Orange Dye", 2).ingredient("red_dye", 1).ingredient("yellow_dye", 1);
        addRecipe("magenta_dye", "Magenta Dye", 2).ingredient("purple_dye", 1).ingredient("pink_dye", 1);
        addRecipe("magenta_dye", "Magenta Dye", 3).ingredient("blue_dye", 1).ingredient("red_dye", 2);
        addRecipe("light_blue_dye", "Light Blue Dye", 2).ingredient("blue_dye", 1).ingredient("white_dye", 1);
        addRecipe("lime_dye", "Lime Dye", 2).ingredient("green_dye", 1).ingredient("white_dye", 1);
        addRecipe("pink_dye", "Pink Dye", 2).ingredient("red_dye", 1).ingredient("white_dye", 1);
        addRecipe("gray_dye", "Gray Dye", 2).ingredient("black_dye", 1).ingredient("white_dye", 1);
        addRecipe("light_gray_dye", "Light Gray Dye", 2).ingredient("gray_dye", 1).ingredient("white_dye", 1);
        addRecipe("light_gray_dye", "Light Gray Dye", 3).ingredient("black_dye", 1).ingredient("white_dye", 2);
        addRecipe("cyan_dye", "Cyan Dye", 2).ingredient("blue_dye", 1).ingredient("green_dye", 1);
        addRecipe("purple_dye", "Purple Dye", 2).ingredient("blue_dye", 1).ingredient("red_dye", 1);
        addRecipe("bone_meal", "Bone Meal", 3).ingredient("bone", 1);
        addRecipe("bone_meal", "Bone Meal (from Block)", 9).ingredient("bone_block", 1);

        // ============ FOOD ============
        addRecipe("bread", "Bread", 1).ingredient("wheat", 3);
        addRecipe("cookie", "Cookie", 8).ingredient("wheat", 2).ingredient("cocoa_beans", 1);
        addRecipe("cake", "Cake", 1)
            .ingredient("milk_bucket", 3).ingredient("sugar", 2).ingredient("egg", 1).ingredient("wheat", 3);
        addRecipe("pumpkin_pie", "Pumpkin Pie", 1).ingredient("pumpkin", 1).ingredient("sugar", 1).ingredient("egg", 1);
        addRecipe("golden_apple", "Golden Apple", 1).ingredient("gold_ingot", 8).ingredient("apple", 1);
        addRecipe("enchanted_golden_apple", "Enchanted Golden Apple", 1).ingredient("gold_block", 8).ingredient("apple", 1);
        addRecipe("golden_carrot", "Golden Carrot", 1).ingredient("gold_nugget", 8).ingredient("carrot", 1);
        addRecipe("glistering_melon_slice", "Glistering Melon Slice", 1).ingredient("gold_nugget", 8).ingredient("melon_slice", 1);
        addRecipe("mushroom_stew", "Mushroom Stew", 1).ingredient("red_mushroom", 1).ingredient("brown_mushroom", 1).ingredient("bowl", 1);
        addRecipe("rabbit_stew", "Rabbit Stew", 1)
            .ingredient("cooked_rabbit", 1).ingredient("carrot", 1).ingredient("baked_potato", 1).ingredient("brown_mushroom", 1).ingredient("bowl", 1);
        addRecipe("beetroot_soup", "Beetroot Soup", 1).ingredient("beetroot", 6).ingredient("bowl", 1);
        addRecipe("suspicious_stew", "Suspicious Stew", 1)
            .ingredient("red_mushroom", 1).ingredient("brown_mushroom", 1).ingredient("bowl", 1).ingredient("dandelion", 1);
        addRecipe("melon", "Melon Block", 1).ingredient("melon_slice", 9);
        addRecipe("melon_seeds", "Melon Seeds", 1).ingredient("melon_slice", 1);
        addRecipe("pumpkin_seeds", "Pumpkin Seeds", 4).ingredient("pumpkin", 1);
        addRecipe("sugar", "Sugar", 1).ingredient("sugar_cane", 1);
        addRecipe("sugar", "Sugar (from Honey)", 3).ingredient("honey_bottle", 1);

        // ============ REDSTONE COMPONENTS ============
        addRecipe("redstone_torch", "Redstone Torch", 1).ingredient("redstone", 1).ingredient("stick", 1);
        addRecipe("lever", "Lever", 1).ingredient("stick", 1).ingredient("cobblestone", 1);
        addRecipe("redstone_lamp", "Redstone Lamp", 1).ingredient("redstone", 4).ingredient("glowstone", 1);
        addRecipe("piston", "Piston", 1)
            .ingredient("oak_planks", 3).ingredient("cobblestone", 4).ingredient("iron_ingot", 1).ingredient("redstone", 1);
        addRecipe("sticky_piston", "Sticky Piston", 1).ingredient("slime_ball", 1).ingredient("piston", 1);
        addRecipe("dispenser", "Dispenser", 1)
            .ingredient("cobblestone", 7).ingredient("bow", 1).ingredient("redstone", 1);
        addRecipe("dropper", "Dropper", 1).ingredient("cobblestone", 7).ingredient("redstone", 1);
        addRecipe("observer", "Observer", 1)
            .ingredient("cobblestone", 6).ingredient("redstone", 2).ingredient("quartz", 1);
        addRecipe("repeater", "Redstone Repeater", 1)
            .ingredient("redstone", 1).ingredient("redstone_torch", 2).ingredient("stone", 3);
        addRecipe("comparator", "Redstone Comparator", 1)
            .ingredient("redstone_torch", 3).ingredient("quartz", 1).ingredient("stone", 3);
        addRecipe("hopper", "Hopper", 1).ingredient("iron_ingot", 5).ingredient("chest", 1);
        addRecipe("tripwire_hook", "Tripwire Hook", 2).ingredient("iron_ingot", 1).ingredient("stick", 1).ingredient("oak_planks", 1);
        addRecipe("trapped_chest", "Trapped Chest", 1).ingredient("chest", 1).ingredient("tripwire_hook", 1);
        addRecipe("tnt", "TNT", 1).ingredient("gunpowder", 5).ingredient("sand", 4);
        addRecipe("note_block", "Note Block", 1).ingredient("oak_planks", 8).ingredient("redstone", 1);
        addRecipe("daylight_detector", "Daylight Detector", 1).ingredient("glass", 3).ingredient("quartz", 3).ingredient("oak_slab", 3);
        addRecipe("target", "Target", 1).ingredient("redstone", 4).ingredient("hay_block", 1);

        // ============ RAILS ============
        addRecipe("rail", "Rail", 16).ingredient("iron_ingot", 6).ingredient("stick", 1);
        addRecipe("powered_rail", "Powered Rail", 6).ingredient("gold_ingot", 6).ingredient("stick", 1).ingredient("redstone", 1);
        addRecipe("detector_rail", "Detector Rail", 6).ingredient("iron_ingot", 6).ingredient("stone_pressure_plate", 1).ingredient("redstone", 1);
        addRecipe("activator_rail", "Activator Rail", 6).ingredient("iron_ingot", 6).ingredient("stick", 2).ingredient("redstone_torch", 1);
        addRecipe("minecart", "Minecart", 1).ingredient("iron_ingot", 5);
        addRecipe("chest_minecart", "Chest Minecart", 1).ingredient("chest", 1).ingredient("minecart", 1);
        addRecipe("furnace_minecart", "Furnace Minecart", 1).ingredient("furnace", 1).ingredient("minecart", 1);
        addRecipe("tnt_minecart", "TNT Minecart", 1).ingredient("tnt", 1).ingredient("minecart", 1);
        addRecipe("hopper_minecart", "Hopper Minecart", 1).ingredient("hopper", 1).ingredient("minecart", 1);

        // ============ UTILITY BLOCKS ============
        addRecipe("furnace", "Furnace", 1).ingredient("cobblestone", 8);
        addRecipe("blast_furnace", "Blast Furnace", 1).ingredient("iron_ingot", 5).ingredient("furnace", 1).ingredient("smooth_stone", 3);
        addRecipe("smoker", "Smoker", 1).ingredient("oak_log", 4).ingredient("furnace", 1);
        addRecipe("brewing_stand", "Brewing Stand", 1).ingredient("blaze_rod", 1).ingredient("cobblestone", 3);
        addRecipe("anvil", "Anvil", 1).ingredient("iron_block", 3).ingredient("iron_ingot", 4);
        addRecipe("grindstone", "Grindstone", 1).ingredient("stick", 2).ingredient("stone_slab", 1).ingredient("oak_planks", 2);
        addRecipe("enchanting_table", "Enchanting Table", 1).ingredient("book", 1).ingredient("diamond", 2).ingredient("obsidian", 4);
        addRecipe("beacon", "Beacon", 1).ingredient("glass", 5).ingredient("obsidian", 3).ingredient("nether_star", 1);
        addRecipe("conduit", "Conduit", 1).ingredient("nautilus_shell", 8).ingredient("heart_of_the_sea", 1);
        addRecipe("ender_chest", "Ender Chest", 1).ingredient("obsidian", 8).ingredient("ender_eye", 1);
        addRecipe("shulker_box", "Shulker Box", 1).ingredient("shulker_shell", 2).ingredient("chest", 1);
        addRecipe("respawn_anchor", "Respawn Anchor", 1).ingredient("crying_obsidian", 6).ingredient("glowstone", 3);
        addRecipe("lodestone", "Lodestone", 1).ingredient("chiseled_stone_bricks", 8).ingredient("netherite_ingot", 1);
        addRecipe("smithing_table", "Smithing Table", 1).ingredient("iron_ingot", 2).ingredient("oak_planks", 4);
        addRecipe("fletching_table", "Fletching Table", 1).ingredient("flint", 2).ingredient("oak_planks", 4);
        addRecipe("cartography_table", "Cartography Table", 1).ingredient("paper", 2).ingredient("oak_planks", 4);
        addRecipe("loom", "Loom", 1).ingredient("string", 2).ingredient("oak_planks", 2);
        addRecipe("composter", "Composter", 1).ingredient("oak_slab", 7);
        addRecipe("stonecutter", "Stonecutter", 1).ingredient("iron_ingot", 1).ingredient("stone", 3);
        addRecipe("cauldron", "Cauldron", 1).ingredient("iron_ingot", 7);

        // ============ MISC ITEMS ============
        addRecipe("paper", "Paper", 3).ingredient("sugar_cane", 3);
        addRecipe("book", "Book", 1).ingredient("paper", 3).ingredient("leather", 1);
        addRecipe("writable_book", "Book and Quill", 1).ingredient("book", 1).ingredient("ink_sac", 1).ingredient("feather", 1);
        addRecipe("map", "Map", 1).ingredient("paper", 8).ingredient("compass", 1);
        addRecipe("glass_pane", "Glass Pane", 16).ingredient("glass", 6);
        addRecipe("iron_bars", "Iron Bars", 16).ingredient("iron_ingot", 6);
        addRecipe("chain", "Chain", 1).ingredient("iron_nugget", 2).ingredient("iron_ingot", 1);
        addRecipe("lantern", "Lantern", 1).ingredient("iron_nugget", 8).ingredient("torch", 1);
        addRecipe("soul_lantern", "Soul Lantern", 1).ingredient("iron_nugget", 8).ingredient("soul_torch", 1);
        addRecipe("torch", "Torch", 4).ingredient("coal", 1).ingredient("stick", 1);
        addRecipe("torch", "Torch (Charcoal)", 4).ingredient("charcoal", 1).ingredient("stick", 1);
        addRecipe("soul_torch", "Soul Torch", 4).ingredient("coal", 1).ingredient("stick", 1).ingredient("soul_sand", 1);
        addRecipe("campfire", "Campfire", 1).ingredient("stick", 3).ingredient("coal", 1).ingredient("oak_log", 3);
        addRecipe("soul_campfire", "Soul Campfire", 1).ingredient("stick", 3).ingredient("soul_sand", 1).ingredient("oak_log", 3);
        addRecipe("ladder", "Ladder", 3).ingredient("stick", 7);
        addRecipe("scaffolding", "Scaffolding", 6).ingredient("bamboo", 6).ingredient("string", 1);
        addRecipe("painting", "Painting", 1).ingredient("stick", 8).ingredient("white_wool", 1);
        addRecipe("item_frame", "Item Frame", 1).ingredient("stick", 8).ingredient("leather", 1);
        addRecipe("glow_item_frame", "Glow Item Frame", 1).ingredient("item_frame", 1).ingredient("glow_ink_sac", 1);
        addRecipe("armor_stand", "Armor Stand", 1).ingredient("stick", 6).ingredient("stone_slab", 1);
        addRecipe("end_crystal", "End Crystal", 1).ingredient("glass", 7).ingredient("ender_eye", 1).ingredient("ghast_tear", 1);
        addRecipe("flower_pot", "Flower Pot", 1).ingredient("brick", 3);
        addRecipe("lead", "Lead", 2).ingredient("string", 4).ingredient("slime_ball", 1);
        addRecipe("name_tag", "Name Tag", 1).ingredient("paper", 1).ingredient("string", 1);

        // ============ WEAPONS & COMBAT ============
        addRecipe("bow", "Bow", 1).ingredient("stick", 3).ingredient("string", 3);
        addRecipe("crossbow", "Crossbow", 1).ingredient("stick", 3).ingredient("iron_ingot", 1).ingredient("string", 2).ingredient("tripwire_hook", 1);
        addRecipe("arrow", "Arrow", 4).ingredient("flint", 1).ingredient("stick", 1).ingredient("feather", 1);
        addRecipe("spectral_arrow", "Spectral Arrow", 2).ingredient("glowstone_dust", 4).ingredient("arrow", 1);
        addRecipe("tipped_arrow", "Tipped Arrow", 8).ingredient("arrow", 8).ingredient("lingering_potion", 1);
        addRecipe("shield", "Shield", 1).ingredient("oak_planks", 6).ingredient("iron_ingot", 1);
        addRecipe("trident", "Trident", 1).ingredient("prismarine_shard", 2).ingredient("prismarine_crystals", 1);

        // ============ TRANSPORTATION ============
        addRecipe("boat", "Oak Boat", 1).ingredient("oak_planks", 5);
        addRecipe("chest_boat", "Oak Chest Boat", 1).ingredient("boat", 1).ingredient("chest", 1);
        addRecipe("saddle", "Saddle", 1).ingredient("leather", 5).ingredient("iron_ingot", 3);

        // ============ DECORATION ============
        addRecipe("carpet", "Carpet", 3).ingredient("white_wool", 2);
        addRecipe("banner", "Banner", 1).ingredient("white_wool", 6).ingredient("stick", 1);
        addRecipe("candle", "Candle", 1).ingredient("string", 1).ingredient("honeycomb", 1);

        // ============ COPPER VARIANTS ============
        addRecipe("cut_copper", "Cut Copper", 4).ingredient("copper_block", 4);
        addRecipe("waxed_copper_block", "Waxed Copper Block", 1).ingredient("copper_block", 1).ingredient("honeycomb", 1);
        addRecipe("lightning_rod", "Lightning Rod", 1).ingredient("copper_ingot", 3);
        addRecipe("spyglass", "Spyglass", 1).ingredient("amethyst_shard", 1).ingredient("copper_ingot", 2);

        // ============ MISC CRAFTABLE ITEMS ============
        addRecipe("bucket", "Bucket", 1).ingredient("iron_ingot", 3);
        addRecipe("shears", "Shears", 1).ingredient("iron_ingot", 2);
        addRecipe("flint_and_steel", "Flint and Steel", 1).ingredient("iron_ingot", 1).ingredient("flint", 1);
        addRecipe("clock", "Clock", 1).ingredient("gold_ingot", 4).ingredient("redstone", 1);
        addRecipe("compass", "Compass", 1).ingredient("iron_ingot", 4).ingredient("redstone", 1);
        addRecipe("recovery_compass", "Recovery Compass", 1).ingredient("compass", 1).ingredient("echo_shard", 8);
        addRecipe("fishing_rod", "Fishing Rod", 1).ingredient("stick", 3).ingredient("string", 2);
        addRecipe("carrot_on_a_stick", "Carrot on a Stick", 1).ingredient("fishing_rod", 1).ingredient("carrot", 1);
        addRecipe("warped_fungus_on_a_stick", "Warped Fungus on a Stick", 1).ingredient("fishing_rod", 1).ingredient("warped_fungus", 1);
        addRecipe("spyglass", "Spyglass", 1).ingredient("amethyst_shard", 1).ingredient("copper_ingot", 2);
        addRecipe("brush", "Brush", 1).ingredient("feather", 1).ingredient("copper_ingot", 1).ingredient("stick", 1);

        // ============ FIREWORKS & EXPLOSIVES ============
        addRecipe("firework_rocket", "Firework Rocket", 3).ingredient("paper", 1).ingredient("gunpowder", 1);
        addRecipe("firework_star", "Firework Star", 1).ingredient("gunpowder", 1).ingredient("orange_dye", 1);
        addRecipe("tnt", "TNT", 1).ingredient("gunpowder", 5).ingredient("sand", 4);

        // ============ END GAME ITEMS ============
        addRecipe("ender_eye", "Eye of Ender", 1).ingredient("ender_pearl", 1).ingredient("blaze_powder", 1);
        addRecipe("blaze_powder", "Blaze Powder", 2).ingredient("blaze_rod", 1);
        addRecipe("magma_cream", "Magma Cream", 1).ingredient("blaze_powder", 1).ingredient("slime_ball", 1);
        addRecipe("fermented_spider_eye", "Fermented Spider Eye", 1).ingredient("spider_eye", 1).ingredient("brown_mushroom", 1).ingredient("sugar", 1);
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
