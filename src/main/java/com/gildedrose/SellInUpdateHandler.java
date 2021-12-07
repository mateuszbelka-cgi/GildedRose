package com.gildedrose;

public class SellInUpdateHandler {
    private static final int DAILY_STANDARD_SELL_IN_CHANGE = 1;
    private static final int LEGENDARY_SELL_IN_CHANGE = 0;

    /**
     * Updates the sellIn property of the provided Item.
     * The item's sellIn is decreased by 1. The item's properties may impose constraints which would affect the amount by
     * which the sellIn is changed.
     * @param   item    An item whose sellIn is to be updated
     */
    public static void updateEndOfDay(Item item) {
        decreaseSellInOfItemBy(item, DAILY_STANDARD_SELL_IN_CHANGE);
    }

    /**
     * Increases the provided item's sellIn by specified amount.
     *
     * Applies the relevant constraints as defined within
     * applyGeneralConstraintsToSellIn's documentation.
     *
     * @param   item                an item whose sellIn is to be increased,
     * @param   sellInIncrease      an integer by which the item's sellIn is to be increased
     */
    private static void increaseSellInOfItemBy(Item item, int sellInIncrease) {
        item.sellIn += applyGeneralConstraintsToSellIn(item, sellInIncrease);
    }

    /**
     * Decreases the provided item's sellIn by specified amount.
     *
     * Applies the relevant constraints as defined within
     * applyGeneralConstraintsToSellIn's documentation.
     *
     * @param   item                an item whose sellIn is to be decreased,
     * @param   sellInDecrease      an integer by which the item's sellIn is to be decreased
     */
    private static void decreaseSellInOfItemBy(Item item, int sellInDecrease) {
        item.sellIn -= applyGeneralConstraintsToSellIn(item, sellInDecrease);
    }

    /**
     * Checks against certain criteria of the item to determine whether
     * the originally provided amount, by which to change the item's sellIn, should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Legendary status
     *
     * @param   item            an item whose sellIn is to be changed,
     * @param   sellInChange    an integer by which the item's sellIn is to be changed
     * @return                  an integer, the amount by which the sellIn will be changed after applying all general constraints
     */
    private static int applyGeneralConstraintsToSellIn(Item item, int sellInChange) {
        int sellInChangeWithConstraints = sellInChange;

        sellInChangeWithConstraints = sellInOfLegendaryConstraint(item, sellInChangeWithConstraints);

        return sellInChangeWithConstraints;
    }

    /**
     * Given that the item whose sellIn is to be changed is legendary,
     * the appropriate limitations for legendary item's sellIn change are applied.
     * Legendary items may not have their sellIn changed.
     * Therefore, this function returns 0 as the amount by which the sellIn should be changed.
     * @param   item            an item whose sellIn is to be changed,
     * @param   sellInChange    an integer by which the item's sellIn is to be changed
     * @return                  an integer, the amount by which the sellIn will be changed after applying the "legendary items quality" constraint
     */
    private static int sellInOfLegendaryConstraint(Item item, int sellInChange) {
        if (!ItemTypeMembership.isLegendary(item)) return sellInChange;
        return LEGENDARY_SELL_IN_CHANGE;
    }
}
