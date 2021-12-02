package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    /*
     *
     */
    @Test
    void testDefaultQualityAndSellInUpdate() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 10, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(29, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testLegendaryQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 10, 50),
            new Item("Sulfuras, Hand of Ragnaros", -1, 50)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(10, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
        assertEquals(-1, app.items[1].sellIn);
        assertEquals(80, app.items[1].quality);
    }

    /*
     *
     */
    @Test
    void testConjuredQualityUpdate() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 3, 6) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBackstagePassesQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(14, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBackstagePassesSellInTenQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBackstagePassesSellInFiveQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBackstagePassesSellAtTheDayOfConcertQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBackstagePassesSellAfterConcertQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testBelowZeroSellInQualityUpdate() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", -1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testAgedBrieQualityUpdate() {
        Item[] items = new Item[] { new Item("Aged Brie", 2, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1, app.items[0].sellIn);
        assertEquals(11, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testQualityBelowZeroQualityUpdate() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", -10, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-11, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    /*
     *
     */
    @Test
    void testQualityAboveFiftyQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Aged Brie", 8, 50),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
        assertEquals(7, app.items[1].sellIn);
        assertEquals(50, app.items[1].quality);
    }
}
