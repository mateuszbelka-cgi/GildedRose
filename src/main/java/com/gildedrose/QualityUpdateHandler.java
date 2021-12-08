package com.gildedrose;

public class QualityUpdateHandler {
    private static final int MAX_QUALITY = 50;
    private static final int LEGENDARY_QUALITY = 80;
    private static final int DAILY_STANDARD_QUALITY_CHANGE = 1;
    private static final int CONJURED_ITEM_QUALITY_MULTIPLIER = 2;
    private static final int PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER = 2;
    private static final int BACKSTAGE_PASS_SELL_IN_FOR_INCREASE_OF_TWO = 5;
    private static final int BACKSTAGE_PASS_SELL_IN_FOR_INCREASE_OF_THREE = 10;
    private static final int QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT = 2;
    private static final int QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT = 3;


    /**
     * Updates the quality property of a provided Item.
     * Based on the item's properties, the quality may be increased or decreased at the end of the day.
     * @param   item    An item whose quality is to be updated
     */
    public static void updateEndOfDay(Item item) {
        if (ItemTypeMembership.isQualityUpgradedWithTime(item)) {
            increaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
        } else {
            decreaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
        }
    }

    /**
     * Increases the provided item's quality by specified amount.
     *
     * Applies the relevant constraints as defined within
     * applyGeneralConstraintsToQuality's and applyUpgradingWithTimeConstraintsToQuality's documentation.
     *
     * @param   item                an item whose quality is to be increased,
     * @param   qualityIncrease     an integer by which the item's quality is to be increased
     */
    private static void increaseQualityOfItemBy(Item item, int qualityIncrease) {
        int qualityIncreaseWithConstraints = qualityIncrease;

        qualityIncreaseWithConstraints = applyGeneralConstraintsToQuality(item, qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = applyUpgradingWithTimeConstraintsToQuality(item, qualityIncreaseWithConstraints);

        item.quality += qualityIncreaseWithConstraints;
    }

    /**
     * Decreases the provided item's quality by specified amount.
     *
     * Applies the relevant constraints as defined within
     * applyGeneralConstraintsToQuality's and applyNotUpgradingWithTimeConstraintsToQuality's documentation.
     *
     * @param   item                an item whose quality is to be decreased,
     * @param   qualityDecrease     an integer by which the item's quality is to be decreased
     */
    private static void decreaseQualityOfItemBy(Item item, int qualityDecrease) {
        int qualityDecreaseWithConstraints = qualityDecrease;

        qualityDecreaseWithConstraints = applyGeneralConstraintsToQuality(item, qualityDecreaseWithConstraints);
        qualityDecreaseWithConstraints = applyNotUpgradingWithTimeConstraintsToQuality(item, qualityDecreaseWithConstraints);

        item.quality -= qualityDecreaseWithConstraints;
    }

    /**
     * Checks against certain criteria of the item to determine whether
     * the originally provided amount to change the item's quality by should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Conjured status
     *      - Expired status
     *
     * @param   item            an item whose quality is to be changed,
     * @param   qualityChange   an integer by which the item's quality is to be changed
     * @return                  an integer, the amount by which the quality will be changed after applying all general constraints
     */
    private static int applyGeneralConstraintsToQuality(Item item, int qualityChange) {
        int qualityChangeWithConstraints = qualityChange;

        qualityChangeWithConstraints = qualityOfConjuredConstraint(item, qualityChangeWithConstraints);
        qualityChangeWithConstraints = qualityExpiredConstraint(item, qualityChangeWithConstraints);

        return qualityChangeWithConstraints;
    }

    /**
     * Checks against certain criteria of an item that is upgrading with time to determine whether
     * the originally provided amount to change the item's quality by should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Backstage passes
     *      - Quality after update exceeds 50
     *
     * @param   item                an item whose quality is to be increased,
     * @param   qualityIncrease     an integer by which the item's quality is to be increased
     * @return                      an integer, the amount by which the quality will be increased after applying all upgrading with time constraints
     */
    private static int applyUpgradingWithTimeConstraintsToQuality(Item item, int qualityIncrease) {
        int qualityIncreaseWithConstraints = qualityIncrease;

        qualityIncreaseWithConstraints = qualityOfBackstagePassConstraint(item, qualityIncreaseWithConstraints);
        qualityIncreaseWithConstraints = qualityNotAboveFiftyConstraint(item, qualityIncreaseWithConstraints);

        return qualityIncreaseWithConstraints;
    }

    /**
     * Checks against certain criteria of an item that is not upgrading with time to determine whether
     * the originally provided amount to change the item's quality by should be modified.
     *
     * The criteria based on which the change can be made includes:
     *      - Legendary status
     *      - Quality after update is below 0
     *
     * @param   item                an item whose quality is to be changed,
     * @param   qualityDecrease     an integer by which the item's quality is to be changed
     * @return                      an integer, the amount by which the quality will be changed after applying all downgrading with time constraints
     */
    private static int applyNotUpgradingWithTimeConstraintsToQuality(Item item, int qualityDecrease) {
        int qualityDecreaseWithConstraints = qualityDecrease;

        qualityDecreaseWithConstraints = qualityOfLegendaryConstraint(item, qualityDecreaseWithConstraints);
        qualityDecreaseWithConstraints = qualityNotBelowZeroConstraint(item, qualityDecreaseWithConstraints);

        return qualityDecreaseWithConstraints;
    }

    /**
     * In case the amount by which the quality is to be decreased would result in the item's quality being below 0
     * the function returns a modified amount to decrease the item's quality by such amount that the
     * "not below 0 quality" constraint is maintained.
     * @param   item                an item whose quality is to be decreased,
     * @param   qualityDecrease     an integer by which the item's quality is to be decreased
     * @return                      an integer, the amount to decrease the quality after applying the "not below 0 quality" constraint
     */
    private static int qualityNotBelowZeroConstraint(Item item, int qualityDecrease) {
        return Math.min(qualityDecrease, item.quality);
    }

    /**
     * In case the amount by which the quality is to be changed would result in the item's quality being above 50
     * the function returns a modified amount to change the item's quality by such amount that the
     * "not above 50 quality" constraint is maintained.
     * @param   item                an item whose quality is to be increased,
     * @param   qualityIncrease     an integer by which the item's quality is to be increased
     * @return                      an integer, the amount to increase the quality after applying the "not above 50 quality" constraint
     */
    private static int qualityNotAboveFiftyConstraint(Item item, int qualityIncrease) {
        return Math.min(qualityIncrease, MAX_QUALITY - item.quality);
    }

    /**
     * Given that the item whose quality is to be changed is legendary,
     * the appropriate limitations for legendary item's quality change are applied.
     * Legendary items' quality should always be 80.
     * Therefore, this function returns as the amount to be increased the difference between the legendary expected quality
     * and the current quality of the legendary item. As such after the "end of day" update to item's quality,
     * the legendary item will have the expected 80 quality.
     * @param   item                an item whose quality is to be decreased,
     * @param   qualityDecrease     an integer by which the item's quality is to be decreased
     * @return                      an integer, the amount to decrease the quality after applying the "legendary items quality" constraint
     */
    private static int qualityOfLegendaryConstraint(Item item, int qualityDecrease) {
        if (!ItemTypeMembership.isLegendary(item)) return qualityDecrease;
        return item.quality - LEGENDARY_QUALITY;
    }

    /**
     * Given that the item whose quality is to be changed in conjured,
     * the appropriate modifications for changing quality of conjured items are applied.
     * Whenever the quality of a conjured item is changed, the amount by which it is changed is multiplied by the factor of 2.
     * @param   item            an item whose quality is to be changed,
     * @param   qualityChange   an integer by which the item's quality is to be changed
     * @return                  an integer, the amount to change the quality after applying the "conjured items quality" constraint
     */
    private static int qualityOfConjuredConstraint(Item item, int qualityChange) {
        if (!ItemTypeMembership.isConjured(item)) return qualityChange;
        return qualityChange * CONJURED_ITEM_QUALITY_MULTIPLIER;
    }

    /**
     * Given that the item whose quality is to be increased is a backstage pass,
     * the appropriate modifications for changing quality of backstage pass items are applied.
     * Whenever the quality of a backstage pass is increased, the amount by which it is increased is affected by
     * the time proximity to the concert.
     *
     * If the concert has already happened, which is indicated by sellIn below 0, the quality is to become 0.
     * That is achieved by setting the amount by which the item's quality is to be increased, to the inverse of the item's
     * current quality.
     *
     * If the concert will be in 5 days or less, the quality of the item increases by 3.
     *
     * If the concert will be in 6 to 10 days, the quality of the item increase by 2.
     *
     * Otherwise, the quality is increased by the default amount passed as parameter.
     *
     * @param   item                an item whose quality is to be increased,
     * @param   qualityIncrease     an integer by which the item's quality is to be increased
     * @return                      an integer, the amount to increase the quality after applying the "backstage pass items quality" constraint
     */
    private static int qualityOfBackstagePassConstraint(Item item, int qualityIncrease) {
        final int qualityIncreaseAfterConcert = -1 * item.quality;

        if (!ItemTypeMembership.isBackstagePass(item)) return qualityIncrease;

        if (ItemTypeMembership.isExpired(item)) {
            return qualityIncreaseAfterConcert;
        } else if (item.sellIn < BACKSTAGE_PASS_SELL_IN_FOR_INCREASE_OF_TWO) {
            return QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT;
        } else if (item.sellIn < BACKSTAGE_PASS_SELL_IN_FOR_INCREASE_OF_THREE) {
            return QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT;
        }

        return qualityIncrease;
    }

    /**
     * For items whose sellIn is below 0, the change to their quality is multiplied by the factor of 2.
     * @param   item            an item whose quality is to be changed,
     * @param   qualityChange   an integer by which the item's quality is to be changed
     * @return                  an integer, the amount to change the quality after applying the "passed sell by date quality" constraint
     */
    private static int qualityExpiredConstraint(Item item, int qualityChange) {
        if (!ItemTypeMembership.isExpired(item)) return qualityChange;
        return qualityChange * PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER;
    }
}
