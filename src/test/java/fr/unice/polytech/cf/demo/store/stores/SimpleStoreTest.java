package fr.unice.polytech.cf.demo.store.stores;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleStoreTest {

    @Test
    void getNameTest() {
        String name = "test";
        StoreInterface store = new SimpleStore(name);
        assertEquals(name, store.getName());
    }

    @Test
    void sellTest() {
        StoreInterface store = new SimpleStore("test");
        store.sell("Purchase", 10);
        assertEquals(10, store.getRevenue());
        store.sell("Purchase", 20);
        assertEquals(30, store.getRevenue());
    }

    @Test
    void getRevenueTest() {
        final StoreInterface store = new SimpleStore("test");
        assertEquals(0, store.getRevenue());

    }
}