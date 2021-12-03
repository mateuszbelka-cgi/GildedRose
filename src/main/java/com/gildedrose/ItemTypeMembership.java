package com.gildedrose;

import java.util.Arrays;

public class ItemTypeMembership {
    public static final String[] LEGENDARIES = new String[] {"Sulfuras, Hand of Ragnaros"};
    public static final String[] BACKSTAGE_PASSES = new String[] {"Backstage passes to a TAFKAL80ETC concert"};
    public static final String CONJURED_LOWERCASE = "conjured";
    public static final String AGED_BRIE = "Aged Brie";

    /*
     *
     */
    protected static boolean isLegendary(Item item) {
        return Arrays.asList(LEGENDARIES).contains(item.name);
    }

    /*
     *
     */
    protected static boolean isConjured(Item item) {
        return item.name.toLowerCase().contains(CONJURED_LOWERCASE);
    }

    /*
     *
     */
    protected static boolean isBackstagePass(Item item) {
        return Arrays.asList(BACKSTAGE_PASSES).contains(item.name);
    }

    /*
     *
     */
    protected static boolean isAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }

    /*
     *
     */
    protected static boolean isUpgradingWithTime(Item item) {
        return isAgedBrie(item) || isBackstagePass(item);
    }
}
