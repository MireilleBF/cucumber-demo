package fr.unice.polytech.cf.demo.store.cli;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import picocli.CommandLine;
import static picocli.CommandLine.Command;

@Command(
        name = "create-customer",
        description = "Create a customer"
)
public class CustomerCreateCommand implements Runnable {

    @CommandLine.Option(names = {"-c", "--customer"})
    private String customerName;



    @Override
    public void run() {
        UIGhost.println("Creating a customer with name " + customerName);
        if (customerName == null) {
            UIGhost.println("Customer not created, no name provided");
            return;
        }
        customerManager.registerCustomer(customerName);
    }

    private CustomerManager customerManager;


    public CustomerCreateCommand(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }
}
