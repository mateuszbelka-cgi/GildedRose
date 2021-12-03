package com.gildedrose;

public class QualityUpdateHandler extends AbstractItemUpdateHandler {
    private static final int CONJURED_ITEM_QUALITY_MULTIPLIER = 2;
    private static final int PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER = 2;
    private static final int QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT = 2;
    private static final int QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT = 3;

    /*
     * Updates the quality property of the provided Item.
     * Based on the item's properties, the quality may be increased or decreased at the end of the day.
     * @param   An item whose quality is to be updated
     */
    protected static void updateEndOfDay(Item item) {
        if (ItemTypeMembership.isUpgradingWithTime(item)) increaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
        else increaseQualityOfItemBy(item, -1 * DAILY_STANDARD_QUALITY_CHANGE);
    }

    /*
     * Increases the provided item's quality by specified amount.
     * Before applying the change, the function checks against certain criteria of the item to determine whether
     * the originally provided amount to increase the item's quality by is to be changed.
     *
     * The criteria based on which the change can be made includes:
     *      - Legendary status
     *      - Conjured status
     *      - Backstage passes
     *      - Aged Brie
     *
     * With exception to Legendary Items, the quality may not be outside the range of 0 and 50.
     * Hence, any request to change the item's quality outside that range is bound by those limits.
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     */
    // Hierarchy: legendary = 80 > above 0, below 50 > conjured multiplier
    protected static void increaseQualityOfItemBy(Item item, int qualityIncrease) {
        int qualityIncreaseWithConstraints = qualityIncrease;

        if (ItemTypeMembership.isBackstagePass(item)) qualityIncreaseWithConstraints = qualityOfBackstagePassConstraint(item, qualityIncreaseWithConstraints);
        if (ItemTypeMembership.isConjured(item)) qualityIncreaseWithConstraints = qualityOfConjuredConstraint(qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualitySellByPassedConstraint(item, qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualityNotBelowZeroConstraint(item, qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualityNotAboveFiftyConstraint(item, qualityIncreaseWithConstraints);
        if (ItemTypeMembership.isLegendary(item)) qualityIncreaseWithConstraints = qualityOfLegendaryConstraint(item);

        item.quality += qualityIncreaseWithConstraints;
    }

    /*
     * In case the amount by which the quality is to be increased would result in the item's quality being below 0
     * the function returns a modified amount to increase the item's quality by such that the "not below 0 quality"
     * constrained is preserved.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not below 0 quality" constraint
     */
    private static int qualityNotBelowZeroConstraint(Item item, int qualityIncrease) {
        return Math.max(qualityIncrease, -1 * item.quality);
    }

    /*
     * In case the amount by which the quality is to be increased would result in the item's quality being above 50
     * the function returns a modified amount to increase the item's quality by such that the "not above 50 quality"
     * constrained is preserved.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not above 50 quality" constraint
     */
    private static int qualityNotAboveFiftyConstraint(Item item, int qualityIncrease) {
        return Math.min(qualityIncrease, MAX_QUALITY - item.quality);
    }

    /*
     * Given that the item whose quality is to be changed is legendary,
     * the appropriate limitations for legendary item's quality change are applied.
     * Legendary items may not have their quality changed, and their quality is to be always 80.
     * Therefore, this function returns as the amount to be changed the difference between the legendary expected quality
     * and the current quality of the legendary item. As such after the "end of day" update to item's quality,
     * the legendary item will have the expected 80 quality.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not below zero quality" constraint
     */
    private static int qualityOfLegendaryConstraint(Item item) {
        return LEGENDARY_QUALITY - item.quality;
    }

    /*
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not below zero quality" constraint
     */
    private static int qualityOfConjuredConstraint(int qualityIncrease) {
        return qualityIncrease * CONJURED_ITEM_QUALITY_MULTIPLIER;
    }

    /*
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not below zero quality" constraint
     */
    private static int qualityOfBackstagePassConstraint(Item item, int qualityIncrease) {
        final int qualityIncreaseAfterConcert = -1 * item.quality;

        if (item.sellIn < 0) {
            return qualityIncreaseAfterConcert;
        } else if (item.sellIn <= 5) {
            return QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT;
        } else if (item.sellIn <= 10) {
            return QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT;
        }

        return qualityIncrease;
    }

    /*
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased by
     * @return  an integer, the amount to increase the quality after applying the "not below zero quality" constraint
     */
    private static int qualitySellByPassedConstraint(Item item, int qualityIncrease) {
        if (item.sellIn < 0) return qualityIncrease * PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER;
        return qualityIncrease;
    }
}
