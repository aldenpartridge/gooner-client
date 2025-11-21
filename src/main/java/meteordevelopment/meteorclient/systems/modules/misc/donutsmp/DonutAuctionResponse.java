package meteordevelopment.meteorclient.systems.modules.misc.donutsmp;

import java.util.List;

public class DonutAuctionResponse {
    public int status;
    public List<AuctionEntry> result;

    public static class AuctionEntry {
        public AuctionItem item;
        public double price;
        public Seller seller;
        public int time_left;
    }

    public static class AuctionItem {
        public String id;
        public String display_name;
        public int count;
        public List<String> lore;
        public ItemData enchants;
        public List<ContainerItem> contents;
    }

    public static class ContainerItem {
        public String id;
        public String display_name;
        public int count;
        public ItemData enchants;
    }

    public static class ItemData {
        public Enchantments enchantments;
        public Trim trim;
    }

    public static class Enchantments {
        public java.util.Map<String, Integer> levels;
    }

    public static class Trim {
        public String material;
        public String pattern;
    }

    public static class Seller {
        public String name;
        public String uuid;
    }
}
