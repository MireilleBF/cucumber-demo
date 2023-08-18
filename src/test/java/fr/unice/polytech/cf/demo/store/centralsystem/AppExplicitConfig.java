package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import org.picocontainer.*;
import org.picocontainer.behaviors.Caching;

/**
 * Manage the dependency injection for the application
 */
public class AppExplicitConfig {

    private MutablePicoContainer container;

    public MutablePicoContainer getContainer() {
        if (container == null) {
            //Cache is used to avoid creating multiple instances of the same component
            container = new DefaultPicoContainer(new Caching());
            // Register test-specific dependencies, mocks, etc.
            container.addComponent(StoreDAO.class);
            container.addComponent(CustomerDAO.class);
            container.addComponent(EcoSystemManager.class);
        }
        return container;
    }

}
