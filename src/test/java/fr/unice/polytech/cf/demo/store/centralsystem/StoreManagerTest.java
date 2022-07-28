package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.injectors.AnnotatedFieldInjection;

import static org.junit.jupiter.api.Assertions.*;

class StoreManagerTest {

    StoreManagerInterface storeManager;

    @BeforeEach
    void setUp() {
        MutablePicoContainer container = new DefaultPicoContainer(new AnnotatedFieldInjection());
        container.addComponent(StoreDAO.class);
        container.addComponent(StoreManagerInterface.class, StoreManager.class);
        storeManager = container.getComponent(StoreManagerInterface.class);
    }

    @Test
    void addStoreTest() {
        String name = "test";
        storeManager.addStore(name);
        assertEquals(name, storeManager.getStore(name).get().getName());
    }

}