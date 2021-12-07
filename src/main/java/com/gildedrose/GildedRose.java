package com.gildedrose;

class GildedRose {
    Item[] items;

    /*
     * The GildedRose constructor. Assigns given array of items to the GildedRose instance variable.
     * @param   An array of items
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /*
     * The entry point function responsible for updating Item instance properties (quality, sellIn)
     * of all items present in the GildedRose items array instance variable.
     *
     * The two function calls accept an Item as parameter and
     * update that item's specific property based on a wide range of criteria.
     */
    public void updateQuality() {
        for (Item item : items) {
            SellInUpdateHandler.updateEndOfDay(item);
            QualityUpdateHandler.updateEndOfDay(item);
        }
    }
}
