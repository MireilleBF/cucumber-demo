package fr.unice.polytech.cf.demo.store.cli;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManagerInterface;
import fr.unice.polytech.cf.demo.store.centralsystem.StoreManager;
import fr.unice.polytech.cf.demo.store.centralsystem.StoreManagerInterface;
import fr.unice.polytech.cf.demo.store.payment.OrderManager;
import fr.unice.polytech.cf.demo.store.payment.PaymentSystem;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.stores.StoreDAO;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.injectors.AnnotatedFieldInjection;
import picocli.CommandLine;

import static org.mockito.Mockito.*;


@CommandLine.Command(
        name = "ecoSystem"
        , mixinStandardHelpOptions = true
)
//We don't use these subcommands because we need to inject dependencies in them
//but we keep them here to show how to use them when we don't need to inject dependencies

/*        subcommands = {
                //CustomerCreateCommand.class,
                //CustomerFindCommand.class
                //OrderCommand.class
        }
)
 */
public class ApplicationCommand implements Runnable {
    //Only to test another way to define commands
    private static StoreManagerInterface storeManager;

        public static void main(String[] args) {
            MutablePicoContainer container = new DefaultPicoContainer(new Caching());
            container.addComponent(OrderManager.class);
            container.addComponent(CustomerManagerInterface.class, CustomerManager.class);
            container.addComponent(PaymentSystem.class, mock(PaymentSystem.class));
            container.addComponent(CustomerDAO.class);

            PaymentSystem paymentSystem = container.getComponent(PaymentSystem.class);
            CustomerManager customerManager = container.getComponent(CustomerManager.class);
            customerManager.registerCustomer("John");
            customerManager.registerCustomer("Jack");
            OrderManager orderManager = container.getComponent(OrderManager.class);

            MutablePicoContainer subcontainer = new DefaultPicoContainer(new AnnotatedFieldInjection());
            subcontainer.addComponent(StoreDAO.class);
            subcontainer.addComponent(StoreManagerInterface.class, StoreManager.class);
            storeManager = subcontainer.getComponent(StoreManagerInterface.class);
            storeManager.addStore("MK2");
            storeManager.addStore("Carrefour");

            container.addChildContainer(subcontainer);
            CommandLine commandLine = new CommandLine(new ApplicationCommand());
            //The following lines are required to inject dependencies in the subcommands
            commandLine.addSubcommand("order", new OrderCommand(orderManager, paymentSystem ));
            commandLine.addSubcommand("create-customer", new CustomerCreateCommand(customerManager));
            commandLine.addSubcommand("find-customer", new CustomerFindCommand(customerManager));
            commandLine.addSubcommand("stores", new StoreCommand(storeManager));
            commandLine.parseWithHandler(new CommandLine.RunLast(), args);
        }

        @Override
        public void run() {
            UIGhost.println("The popular ecoSystem command");
        }

/*
Another way to do it is to use the @Command annotation on the methods instead of the class.
Seemed to be a good idea at first, but it is not possible to use the same annotation on a class and a method.
We keep only one example here.
 */
    @CommandLine.Command(
            name = "find-stores",
            description = "Print all stores"
    )
    public void findStores() {
        storeManager.findAllStores().forEach(UIGhost::println);
    }

}

