package com.gildedrose;

public class SellInUpdateHandler extends AbstractItemUpdateHandler {

    /*
     * Updates the sellIn property of the provided Item.
     * The item's sellIn is decreased by 1. The item's properties may impose constraints which would affect the amount by
     * which the sellIn is changed.
     * @param   An item whose sellIn is to be updated
     */
    protected static void updateEndOfDay(Item item) {
        decreaseSellInOfItemBy(item, DAILY_STANDARD_SELL_IN_CHANGE);
    }

    /*
     * Increases the provided item's sellIn by specified amount.
     * Before applying the change, the function checks against certain criteria of the item to determine whether
     * the originally provided amount, by which to increase the item's sellIn, should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Legendary status
     *
     * @param   an item whose sellIn is to be increased,
     *          an integer by which the item's sellIn is to be increased
     */
    protected static void increaseSellInOfItemBy(Item item, int sellInIncrease) {
        int sellInIncrementWithConstraints = sellInIncrease;

        if (ItemTypeMembership.isLegendary(item)) sellInIncrementWithConstraints = sellInOfLegendaryConstraint();

        item.sellIn += sellInIncrementWithConstraints;
    }

    /*
     * Decreases the provided item's sellIn by specified amount.
     *
     * Applies the relevant constraints as defined within increaseSellInOfItemBy's documentation.
     *
     * @param   an item whose sellIn is to be decreased,
     *          an integer by which the item's sellIn is to be decreased
     */
    protected static void decreaseSellInOfItemBy(Item item, int sellInDecrease) {
        increaseSellInOfItemBy(item, -1 * sellInDecrease);
    }

    /*
     * Given that the item whose sellIn is to be changed is legendary,
     * the appropriate limitations for legendary item's sellIn change are applied.
     * Legendary items may not have their sellIn changed.
     * Therefore, this function returns 0 as the amount by which the sellIn should be changed.
     * @return  an integer, the amount by which the sellIn will be changed after applying the "legendary items quality" constraint
     */
    private static int sellInOfLegendaryConstraint() {
        return LEGENDARY_SELL_IN_CHANGE;
    }
}
