package fr.unice.polytech.cf.demo.store.purchasing;

/**
 * A DAO (data access object (DAO) ) for Customers.
 *
 */
public class CustomerDAO extends fr.unice.polytech.cf.demo.store.SimplifiedDAO<Customer> {

    public CustomerDAO() {
            super();
            save(new Customer("Custom 1"));
            save(new Customer("Custom 2"));
        }

}
