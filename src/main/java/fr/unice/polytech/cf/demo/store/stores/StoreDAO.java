package fr.unice.polytech.cf.demo.store.stores;


public class StoreDAO extends fr.unice.polytech.cf.demo.store.SimplifiedDAO<StoreInterface> {
    public StoreDAO() {
        super();
        save(new Store("Store 1"));
        save(new Store("Store 2"));
    }

}
