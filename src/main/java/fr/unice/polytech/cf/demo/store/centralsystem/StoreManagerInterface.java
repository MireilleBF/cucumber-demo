package fr.unice.polytech.cf.demo.store.centralsystem;
import fr.unice.polytech.cf.demo.store.stores.StoreInterface;

import java.util.Optional;
import java.util.List;
public interface StoreManagerInterface {
     Optional<StoreInterface> getStore(String name);

     StoreInterface addStore(String storeName) ;

    List<StoreInterface> findAllStores();
}
