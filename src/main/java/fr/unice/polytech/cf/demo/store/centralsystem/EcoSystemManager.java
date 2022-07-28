package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.Customer;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.purchasing.Purchase;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreInterface;

import java.util.Optional;

/**
 * Created by MBF on 10/5/16.
 * This class is one facade of the system.
 * It interacts between the stores and the customers.
 * Dedicated classes will manage the lifecycles of stores and customers themselves.
 *
 */
public class EcoSystemManager {


    private final StoreDAO storeDAO;
    private final CustomerDAO customerDAO;

    //Injecting dependencies through constructor
    public EcoSystemManager(StoreDAO storeDAO, CustomerDAO customerDAO) {
        this.storeDAO = storeDAO;
        this.customerDAO = customerDAO;
    }

    public StoreDAO getStoreDAO() {
        return storeDAO;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void sell(String customerName, String purchaseSescription, String storeName, int price) throws UnknownCustomerException, UnknownStoreException {
        Optional<Customer> customerOpt = customerDAO.get(customerName);
        if (customerOpt.isEmpty()) {
           throw new UnknownCustomerException(customerName);
       }


        Optional<StoreInterface> storeOpt = storeDAO.get(storeName);
        if (storeOpt.isEmpty()) {
            throw new UnknownStoreException(storeName);
        }

        Customer customer = customerOpt.get();
        StoreInterface store = storeOpt.get();
        sell(customer, purchaseSescription, store, price);
    }
    private void sell(Customer customer, String purchaseSescription, StoreInterface store, int price) {
        Purchase item = new Purchase(purchaseSescription, price);
        store.sell(purchaseSescription, price);
        customer.purchase(item);
    }

}
