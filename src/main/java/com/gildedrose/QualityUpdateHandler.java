package com.gildedrose;

public class QualityUpdateHandler extends AbstractItemUpdateHandler {
    private static final int CONJURED_ITEM_QUALITY_MULTIPLIER = 2;
    private static final int PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER = 2;
    private static final int QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT = 2;
    private static final int QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT = 3;

    /*
     * Updates the quality property of a provided Item.
     * Based on the item's properties, the quality may be increased or decreased at the end of the day.
     * @param   An item whose quality is to be updated
     */
    protected static void updateEndOfDay(Item item) {
        if (ItemTypeMembership.isQualityUpgradedWithTime(item)) increaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
        else decreaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
    }

    /*
     * Increases the provided item's quality by specified amount.
     * Before applying the change, the function checks against certain criteria of the item to determine whether
     * the originally provided amount to increase the item's quality by should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Legendary status
     *      - Conjured status
     *      - Backstage passes
     *      - Aged Brie
     *
     * With exception to Legendary Items, the quality may not be outside the range of 0 and 50.
     * Hence, any request to change the item's quality outside that range is bound to those limits.
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     */
    protected static void increaseQualityOfItemBy(Item item, int qualityIncrease) {
        int qualityIncreaseWithConstraints = qualityIncrease;

        if (ItemTypeMembership.isBackstagePass(item)) qualityIncreaseWithConstraints = qualityOfBackstagePassConstraint(item, qualityIncreaseWithConstraints);
        if (ItemTypeMembership.isConjured(item)) qualityIncreaseWithConstraints = qualityOfConjuredConstraint(qualityIncreaseWithConstraints);
        if (ItemTypeMembership.isExpired(item)) qualityIncreaseWithConstraints = qualityExpiredConstraint(qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualityNotBelowZeroConstraint(item, qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualityNotAboveFiftyConstraint(item, qualityIncreaseWithConstraints);
        if (ItemTypeMembership.isLegendary(item)) qualityIncreaseWithConstraints = qualityOfLegendaryConstraint(item);

        item.quality += qualityIncreaseWithConstraints;
    }

    /*
     * Decreases the provided item's quality by specified amount.
     *
     * Applies the relevant constraints as defined within increaseQualityOfItemBy's documentation.
     *
     * @param   an item whose quality is to be decreased,
     *          an integer by which the item's quality is to be decreased
     */
    protected static void decreaseQualityOfItemBy(Item item, int qualityDecrease) {
        increaseQualityOfItemBy(item, -1 * qualityDecrease);
    }

    /*
     * In case the amount by which the quality is to be increased would result in the item's quality being below 0
     * the function returns a modified amount to increase the item's quality by such amount that the
     * "not below 0 quality" constraint is preserved.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "not below 0 quality" constraint
     */
    private static int qualityNotBelowZeroConstraint(Item item, int qualityIncrease) {
        return Math.max(qualityIncrease, MIN_QUALITY - item.quality);
    }

    /*
     * In case the amount by which the quality is to be increased would result in the item's quality being above 50
     * the function returns a modified amount to increase the item's quality by such amount that the
     * "not above 50 quality" constraint is preserved.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "not above 50 quality" constraint
     */
    private static int qualityNotAboveFiftyConstraint(Item item, int qualityIncrease) {
        return Math.min(qualityIncrease, MAX_QUALITY - item.quality);
    }

    /*
     * Given that the item whose quality is to be increased is legendary,
     * the appropriate limitations for legendary item's quality change are applied.
     * Legendary items' quality is to always be 80.
     * Therefore, this function returns as the amount to be increased the difference between the legendary expected quality
     * and the current quality of the legendary item. As such after the "end of day" update to item's quality,
     * the legendary item will have the expected 80 quality.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "legendary items quality" constraint
     */
    private static int qualityOfLegendaryConstraint(Item item) {
        return LEGENDARY_QUALITY - item.quality;
    }

    /*
     * Given that the item whose quality is to be increased in conjured,
     * the appropriate modifications for changing quality of conjured items are applied.
     * Whenever the quality of a conjured item is changed, the amount by which it is changed is multiplied by the factor of 2.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "conjured items quality" constraint
     */
    private static int qualityOfConjuredConstraint(int qualityIncrease) {
        return qualityIncrease * CONJURED_ITEM_QUALITY_MULTIPLIER;
    }

    /*
     * Given that the item whose quality is to be increased in a backstage pass,
     * the appropriate modifications for changing quality of backstage pass items are applied.
     * Whenever the quality of backstage passes is increased, the amount by which it is increased is affected by
     * the time proximity to the concert.
     *
     * If the concert has already happened, which is indicated by sellIn below 0, the quality is to become 0.
     * That is achieved by setting the amount by which the item's quality is to be increased to the inverse of the item's
     * current quality.
     *
     * If the concert will be in 5 days or less, the quality of the item increases by 3.
     *
     * If the concert will be in 6 to 10 days, the quality of the item increase by 2.
     *
     * Otherwise, the quality is increased by the default amount passed as parameter.
     *
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "backstage pass items quality" constraint
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
     * For items whose sellIn is below 0, the change to their quality is multiplied by the factor of 2.
     * @param   an item whose quality is to be increased,
     *          an integer by which the item's quality is to be increased
     * @return  an integer, the amount to increase the quality after applying the "passed sell by date quality" constraint
     */
    private static int qualityExpiredConstraint(int qualityIncrease) {
        return qualityIncrease * PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER;
    }
}
