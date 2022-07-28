package fr.unice.polytech.cf.demo.store.centralsystem;

import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import org.picocontainer.MutablePicoContainer;


public class FacadeContainer {

   MutablePicoContainer container ;

    StoreDAO storeDAO ;
    CustomerDAO customerDAO ;
    EcoSystemManager ecoSystemManager;

   public FacadeContainer(AppExplicitConfig config) {
        this.container = config.getContainer();
        this.storeDAO = container.getComponent(StoreDAO.class);
        this.customerDAO = container.getComponent(CustomerDAO.class);
        this.ecoSystemManager = container.getComponent(EcoSystemManager.class);
    }






}

