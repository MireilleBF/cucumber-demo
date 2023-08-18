package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.stores.Store;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreInterface;
import org.picocontainer.annotations.Inject;

import java.util.Optional;

public class StoreManager implements StoreManagerInterface {

    //Injection using a field
    @Inject
    private StoreDAO storeDAO;

    public Optional<StoreInterface> getStore(String name) {
        return storeDAO.get(name);
    }

    public StoreInterface addStore(String storeName) {
        StoreInterface store = new Store(storeName);
        storeDAO.save(store);
        return store;
    }

}
