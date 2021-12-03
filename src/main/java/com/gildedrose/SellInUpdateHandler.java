package com.gildedrose;

public class SellInUpdateHandler extends AbstractItemUpdateHandler {

    /*
     *
     */
    protected static void updateEndOfDay(Item item) {
        final int dailyStandardSellInChange = 1;

        increaseSellIn(item, -1 * dailyStandardSellInChange);
    }

    /*
     *
     */
    protected static void increaseSellIn(Item item, int sellInIncrease) {
        int sellInIncrementWithConstraints = sellInIncrease;

        if (ItemTypeMembership.isLegendary(item)) sellInIncrementWithConstraints = sellInOfLegendaryConstraint();

        item.sellIn += sellInIncrementWithConstraints;
    }

    /*
     *
     */
    private static int sellInOfLegendaryConstraint() {
        final int sellInDailyChangeLegendaryItem = MIN_QUALITY;
        return sellInDailyChangeLegendaryItem;
    }
}
