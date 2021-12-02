package com.gildedrose;

public class QualityUpdateHandler extends AbstractItemUpdateHandler {
    private static final int CONJURED_ITEM_QUALITY_MULTIPLIER = 2;
    private static final int QUALITY_INCREASE_TEN_OR_LESS_DAYS_BEFORE_CONCERT = 2;
    private static final int QUALITY_INCREASE_FIVE_OR_LESS_DAYS_BEFORE_CONCERT = 3;
    private static final int PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER = 2;

    /*
     *
     */
    protected static void updateEndOfDay(Item item) {
        if (ItemPropertyPossession.isUpgradingWithTime(item)) increaseQualityOfItemBy(item, DAILY_STANDARD_QUALITY_CHANGE);
        else increaseQualityOfItemBy(item, -1 * DAILY_STANDARD_QUALITY_CHANGE);
    }

    /*
     *
     */
    // Hierarchy: legendary = 80 > above 0, below 50 > conjured multiplier
    protected static void increaseQualityOfItemBy(Item item, int qualityIncrement) {
        int qualityIncrementWithConstraints = qualityIncrement;

        if (ItemPropertyPossession.isBackstagePass(item)) qualityIncrementWithConstraints = qualityOfBackstagePassConstraint(item, qualityIncrementWithConstraints);
        if (ItemPropertyPossession.isConjured(item)) qualityIncrementWithConstraints = qualityOfConjuredConstraint(qualityIncrementWithConstraints);
        qualityIncrementWithConstraints = qualitySellByPassedConstraint(item, qualityIncrementWithConstraints);
        qualityIncrementWithConstraints = qualityAboveZeroConstraint(item, qualityIncrementWithConstraints);
        qualityIncrementWithConstraints = qualityBelowFiftyConstraint(item, qualityIncrementWithConstraints);
        if (ItemPropertyPossession.isLegendary(item)) qualityIncrementWithConstraints = qualityOfLegendaryConstraint(item);

        item.quality += qualityIncrementWithConstraints;
    }

    /*
     *
     */
    private static int qualityAboveZeroConstraint(Item item, int qualityIncrease) {
        return Math.max(qualityIncrease, -1 * item.quality);
    }

    /*
     *
     */
    private static int qualityBelowFiftyConstraint(Item item, int qualityIncrease) {
        return Math.min(qualityIncrease, MAX_QUALITY - item.quality);
    }

    /*
     *
     */
    private static int qualityOfLegendaryConstraint(Item item) {
        return LEGENDARY_QUALITY - item.quality;
    }

    /*
     *
     */
    private static int qualityOfConjuredConstraint(int qualityIncrease) {
        return qualityIncrease * CONJURED_ITEM_QUALITY_MULTIPLIER;
    }

    /*
     *
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
     */
    private static int qualitySellByPassedConstraint(Item item, int qualityIncrease) {
        if (item.sellIn < 0) return qualityIncrease * PASSED_SELL_BY_DATE_QUALITY_MULTIPLIER;
        return qualityIncrease;
    }
}
