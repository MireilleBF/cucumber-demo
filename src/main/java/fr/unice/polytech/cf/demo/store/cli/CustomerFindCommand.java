package fr.unice.polytech.cf.demo.store.cli;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import picocli.CommandLine;

import static picocli.CommandLine.Command;


@Command(
        name = "find-customer",
        description = "Find a customer by name"
)
public class CustomerFindCommand implements Runnable {

    private CustomerManager customerManager;
    @CommandLine.Option(names = {"-A", "--all"})
    private boolean allCustomers;

    @CommandLine.Option(names = {"-n", "--customerName"})
    private String name;


    @Override
    public void run() {
        if (allCustomers) {
            UIGhost.println("Finding all customers");
            customerManager.findAllCustomers().forEach(UIGhost::println);
        }
        else
            if (name != null) {
                UIGhost.println("Finding a customer by name: " + name);
                customerManager.findCustomer(name).ifPresentOrElse(UIGhost::println, () -> UIGhost.println("Customer not found"));
            }
            else {
                UIGhost.println("When using the find-customer command, you must either provide a name or use the --all option");
            }
    }


    public CustomerFindCommand(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

}
