package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    /*
     * Given an item to which only the default "end of day update" rules apply,
     * following an end of day property update,
     * the item's quality should decrease by 1, and it's sellIn should also decrease by 1
     */
    @Test
    void testDefaultQualityAndSellInUpdate() {
        Item[] items = new Item[] { new Item("Elixir of the Mongoose", 30, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(29, app.items[0].sellIn);
        assertEquals(29, app.items[0].quality);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(19, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    /*
     * Given a legendary item,
     * following an end of day property update,
     * the item's quality should be 80, and it's sellIn should not change
     */
    @Test
    void testLegendaryQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Sulfuras, Hand of Ragnaros", 10, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(10, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
        assertEquals(-1, app.items[1].sellIn);
        assertEquals(80, app.items[1].quality);
    }

    /*
     * Given a conjured item,
     * following an end of day property update,
     * the item's quality should decrease by 2, and it's sellIn should decrease by 1
     */
    @Test
    void testConjuredQualityUpdate() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 20, 46) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(19, app.items[0].sellIn);
        assertEquals(44, app.items[0].quality);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(9, app.items[0].sellIn);
        assertEquals(24, app.items[0].quality);
    }

    /*
     * Given an expired and conjured item,
     * following an end of day property update,
     * the item's quality should decrease by 4, and it's sellIn should decrease by 1
     */
    @Test
    void testExpiredAndConjuredQualityUpdate() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 0, 48) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(44, app.items[0].quality);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(-11, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);

    }

    /*
     * Given a backstage pass item whose sellIn is above 10,
     * following an end of day property update,
     * the item's quality should increase by 1, and it's sellIn should decrease by 1
     */
    @Test
    void testBackstagePassesQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 16, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(15, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);

        for (int i = 0; i < 4; i++) {
            app.updateQuality();
        }
        assertEquals(11, app.items[0].sellIn);
        assertEquals(25, app.items[0].quality);

    }

    /*
     * Given a backstage pass item whose sellIn is below or at 10 and above 5,
     * following an end of day property update,
     * the item's quality should increase by 2, and it's sellIn should decrease by 1
     */
    @Test
    void testBackstagePassesSellInTenQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(10, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);

        for (int i = 0; i < 4; i++) {
            app.updateQuality();
        }
        assertEquals(6, app.items[0].sellIn);
        assertEquals(30, app.items[0].quality);

    }

    /*
     * Given a backstage pass item whose sellIn is below or at 5 and above or at 0,
     * following an end of day property update,
     * the item's quality should increase by 2, and it's sellIn should decrease by 1
     */
    @Test
    void testBackstagePassesSellInFiveQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 6, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(5, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);

        for (int i = 0; i < 4; i++) {
            app.updateQuality();
        }
        assertEquals(1, app.items[0].sellIn);
        assertEquals(35, app.items[0].quality);
    }

    /*
     * Given a backstage pass item whose sellIn is below 0,
     * following an end of day property update,
     * the item's quality should become 0, and it's sellIn should decrease by 1
     */
    @Test
    void testBackstagePassesSellAfterConcertQualityUpdate() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    /*
     * Given an item whose sellIn is below 0,
     * following an end of day property update,
     * the item's quality should decrease by 2, and it's sellIn should decrease by 1
     */
    @Test
    void testExpiredQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Elixir of the Mongoose", 0, 10),
            new Item("Elixir of the Mongoose", -10, 40)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(8, app.items[0].quality);
        assertEquals(-11, app.items[1].sellIn);
        assertEquals(38, app.items[1].quality);

        for (int i = 0; i < 2; i++) {
            app.updateQuality();
        }
        assertEquals(-3, app.items[0].sellIn);
        assertEquals(4, app.items[0].quality);
        assertEquals(-13, app.items[1].sellIn);
        assertEquals(34, app.items[1].quality);
    }

    /*
     * Given an Aged Brie item,
     * following an end of day property update,
     * the item's quality should increase by 1, and it's sellIn should decrease by 1
     */
    @Test
    void testAgedBrieQualityUpdate() {
        Item[] items = new Item[] { new Item("Aged Brie", 1, 10) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].sellIn);
        assertEquals(11, app.items[0].quality);

        for(int i = 0; i < 2; i++) {
            app.updateQuality();
        }
        assertEquals(-2, app.items[0].sellIn);
        assertEquals(15, app.items[0].quality);
    }

    /*
     * Given an item whose quality change at the end of day is greater than the item's quality,
     * following an end of day property update,
     * the item's quality should become 0, and it's sellIn should decrease by 1
     */
    @Test
    void testQualityBelowZeroQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Elixir of the Mongoose", 10, 0),
            new Item("Conjured Mana Cake", -1, 2)
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
        assertEquals(-2, app.items[1].sellIn);
        assertEquals(0, app.items[1].quality);

        for (int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(0, app.items[0].quality);
        assertEquals(0, app.items[1].quality);
    }

    /*
     * Given an item whose quality change at the end of day combined with the item's quality is greater than 50,
     * following an end of day property update,
     * the item's quality should become 50, and it's sellIn should decrease by 1
     */
    @Test
    void testQualityAboveFiftyQualityUpdate() {
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 1, 48),
            new Item("Aged Brie", 8, 50),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
        assertEquals(0, app.items[1].sellIn);
        assertEquals(50, app.items[1].quality);
        assertEquals(7, app.items[2].sellIn);
        assertEquals(50, app.items[2].quality);

        for(int i = 0; i < 10; i++) {
            app.updateQuality();
        }
        assertEquals(50, app.items[2].quality);
    }
}
