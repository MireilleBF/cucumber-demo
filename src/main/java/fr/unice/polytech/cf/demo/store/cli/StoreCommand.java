package fr.unice.polytech.cf.demo.store.cli;

import fr.unice.polytech.cf.demo.store.centralsystem.StoreManager;
import fr.unice.polytech.cf.demo.store.centralsystem.StoreManagerInterface;
import picocli.CommandLine;

@CommandLine.Command(
        name = "aboutStores",
        description = "store Manager commande"
)
public class StoreCommand implements Runnable {

    @CommandLine.Option(names = {"-s", "--storeName"}, required = true)
    private String storeName;

    @CommandLine.Option(names = {"-c", "--create"})
    private boolean toCreate;

    private StoreManagerInterface storeManager;

    public StoreCommand() {
        storeManager = new StoreManager();
    }

    public StoreCommand(StoreManagerInterface storeManager) {
        this.storeManager = storeManager;
    }


    public static void main(String[] args) {
        CommandLine.run(new StoreCommand(), args);
    }

    public void createStore() {
        UIGhost.println("Creating a store named " + storeName);
        storeManager.addStore(storeName);
        storeManager.getStore(storeName).ifPresentOrElse(UIGhost::println, () -> UIGhost.println("Store not found"));
    }

    public void findStore() {
        UIGhost.println("Finding a store by name " + storeName);
        storeManager.getStore(storeName).ifPresentOrElse(UIGhost::println, () -> UIGhost.println("Store not found"));
    }
    @Override
    public void run() {
        if (toCreate) {
            createStore();
        }
        else {
            findStore();
        }
    }
}
