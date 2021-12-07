package com.gildedrose;

import java.util.Arrays;

public class ItemTypeMembership {
    public static final String[] LEGENDARIES = new String[] {"Sulfuras, Hand of Ragnaros"};
    public static final String BACKSTAGE_PASSES_LOWERCASE_SUBSTRING = "backstage passes";
    public static final String CONJURED_LOWERCASE_SUBSTRING = "conjured";
    public static final String AGED_BRIE = "Aged Brie";
    public static final int EXPIRATION_AFTER_SELLIN_OF = 0;

    /**
     * Determines the legendary status of an item.
     * @param   item    an item whose legendary status is to be determined
     * @return          a boolean, specifies whether an item is legendary. TRUE if it is, FALSE if it is not
     */
    protected static boolean isLegendary(Item item) {
        return Arrays.asList(LEGENDARIES).contains(item.name);
    }

    /**
     * Determines the conjured status of an item.
     * @param   item    an item whose conjured status is to be determined
     * @return          a boolean, specifies whether an item is conjured. TRUE if it is, FALSE if it is not
     */
    protected static boolean isConjured(Item item) {
        return item.name.toLowerCase().contains(CONJURED_LOWERCASE_SUBSTRING);
    }

    /**
     * Determines whether an item is a backstage pass.
     * @param   item    an item that is checked whether it is a backstage pass.
     * @return          a boolean, specifies whether an item is a backstage pass. TRUE if it is, FALSE if it is not
     */
    protected static boolean isBackstagePass(Item item) {
        return item.name.toLowerCase().contains(BACKSTAGE_PASSES_LOWERCASE_SUBSTRING);
    }

    /**
     * Determines whether an item is an Aged Brie.
     * @param   item    an item that checked whether it is an Aged Brie
     * @return          a boolean, specifies whether an item is an Aged Brie. TRUE if it is, FALSE if it is not
     */
    protected static boolean isAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }

    /**
     * Determines whether an item's quality is upgrading with time.
     * @param   item    an item whose quality is checked whether it is upgrading with time
     * @return          a boolean, specifies whether an item's quality is upgrading with time. TRUE if it is, FALSE if it is not
     */
    protected static boolean isQualityUpgradedWithTime(Item item) {
        return isAgedBrie(item) || isBackstagePass(item);
    }

    /**
     * Determines whether an item is expired, by checking if it's sellIn is below 0.
     * @param   item    an item whose expiration status is checked
     * @return          a boolean, specifies whether an item is expired. TRUE if it is, FALSE if it is not
     */
    protected static boolean isExpired(Item item) {
        return item.sellIn < EXPIRATION_AFTER_SELLIN_OF;
    }
}
