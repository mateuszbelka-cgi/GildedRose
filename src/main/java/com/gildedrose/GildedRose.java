package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

//    public void updateQuality() {
//        for (int i = 0; i < items.length; i++) {
//            if (!items[i].name.equals("Aged Brie")
//                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                if (items[i].quality > 0) {
//                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                        items[i].quality = items[i].quality - 1;
//                    }
//                }
//            } else {
//                if (items[i].quality < 50) {
//                    items[i].quality = items[i].quality + 1;
//
//                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (items[i].sellIn < 11) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1;
//                            }
//                        }
//
//                        if (items[i].sellIn < 6) {
//                            if (items[i].quality < 50) {
//                                items[i].quality = items[i].quality + 1;
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                items[i].sellIn = items[i].sellIn - 1;
//            }
//
//            if (items[i].sellIn < 0) {
//                if (!items[i].name.equals("Aged Brie")) {
//                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
//                        if (items[i].quality > 0) {
//                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
//                                items[i].quality = items[i].quality - 1;
//                            }
//                        }
//                    } else {
//                        items[i].quality = items[i].quality - items[i].quality;
//                    }
//                } else {
//                    if (items[i].quality < 50) {
//                        items[i].quality = items[i].quality + 1;
//                    }
//                }
//            }
//        }
//    }
    public void updateQuality() {
        for (Item item : items) updateItemGivenStatus(item);
    }

    private void updateItemGivenStatus(Item item) {
        if (isItemDegradingWithTime(item)) updateItemDegradingWithTime(item);
        else updateItemUpgradingWithTime(item);
        updateItemGivenLegendaryStatus(item);
        updateItemGivenConjuredStatus(item);
    }

    private void updateItemGivenLegendaryStatus(Item item) {
        if (item.name.contains("Sulfuras")) legendaryItemUpdate(item);
    }

    private boolean isItemDegradingWithTime(Item item) {
        return true;
    }

    private void updateItemDegradingWithTime(Item item) {
        setQualityOfItem(item, item.quality - 1);
        setSellInOfItem(item, item.sellIn - 1);
    }

    private void updateItemUpgradingWithTime(Item item) {
        setQualityOfItem(item, item.quality + 1);
        setSellInOfItem(item, item.sellIn + 1);
    }

    private void updateItemGivenConjuredStatus(Item item) {
        if (item.name.contains("Conjured")) conjuredItemUpdate(item);
    }

    private void conjuredItemUpdate(Item item) {
        setQualityOfItem(item, item.quality - 2);
        setSellInOfItem(item, item.sellIn - 2);
    }

    private void legendaryItemUpdate(Item item) {
        item.quality = 80;
    }

    private void setSellInOfItem(Item item, int newSellIn) {
        item.sellIn = newSellIn;
    }

    private void setQualityOfItem(Item item, int newQuality) {
        int newQualityWithConstraints = newQuality;
        newQualityWithConstraints = qualityAboveZeroConstraint(newQualityWithConstraints);
        newQualityWithConstraints = qualityBelowFiftyConstraint(newQualityWithConstraints);
        item.quality = newQualityWithConstraints;
    }

    private int qualityAboveZeroConstraint(int quality) {
        return Math.max(quality, 0);
    }

    private int qualityBelowFiftyConstraint(int quality) {
        return Math.min(quality, 50);
    }
}
