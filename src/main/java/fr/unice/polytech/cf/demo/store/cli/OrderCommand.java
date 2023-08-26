package fr.unice.polytech.cf.demo.store.cli;

import fr.unice.polytech.cf.demo.store.centralsystem.CustomerManager;
import fr.unice.polytech.cf.demo.store.payment.OrderManager;
import fr.unice.polytech.cf.demo.store.payment.PaymentSystem;
import fr.unice.polytech.cf.demo.store.purchasing.CustomerDAO;
import fr.unice.polytech.cf.demo.store.purchasing.Purchase;
import picocli.CommandLine;

import static org.mockito.Mockito.when;
import static picocli.CommandLine.Command;

@Command(
        name = "order",
        description = "Create an order"
)
public class OrderCommand implements Runnable {

    @CommandLine.Option(names = {"-c", "--customer"})
    private String customerName;
    @CommandLine.Option(names = {"-s", "--store"})
    private String storeName;
    @CommandLine.Option(names = {"-d", "--purchaseDescription"})
    private String productName;
    @CommandLine.Option(names = {"-p", "--price"})
    private int price;

    @CommandLine.Option(names = {"-v", "--paymentIdentifier"})
    private String paymentIdentifier;

   private OrderManager orderManager;
   private PaymentSystem paymentSystem;

   public OrderCommand() {
            orderManager = new OrderManager(paymentSystem, new CustomerManager(new CustomerDAO()));
        }

    public OrderCommand(OrderManager orderManager) {
        this.orderManager = orderManager;
    }

    public OrderCommand(OrderManager orderManager, PaymentSystem component) {
        this(orderManager);
        this.paymentSystem = component;
    }

    public static void main(String[] args) {
        CommandLine.run(new OrderCommand(), args);
    }
    public void createOrder() {
        UIGhost.println("Creating a order for " + customerName + " at " + storeName + " for " + productName + " at " + price + " euros" + " with payment identifier " + paymentIdentifier);
        Purchase p = new Purchase(productName,price);
        //Due to integration with the payment system, we need to mock the payment system
        if (paymentIdentifier != null) {
            when(paymentSystem.registerPayment(price, customerName)).thenReturn(paymentIdentifier);
            when(paymentSystem.isPaymentValid(paymentIdentifier)).thenReturn(true);
        }

        String orderIdent = orderManager.registerOrder(price, customerName, p);
        if (orderIdent == null) {
            UIGhost.println("Order not created, payment not valid");
            return;
        }
        if (orderIdent.isEmpty()) {
            UIGhost.println("Order not created, unknown customer");
            return;
        }

        UIGhost.println("Order created, to retrieve it, use the following ident: " + orderIdent);
        OrderManager.Order order = orderManager.getOrder(orderIdent);
        if (order != null) {
               UIGhost.println("Order found: " + order);
        }
        else
            UIGhost.println("Order not found");

    }

    @Override
    public void run() {
        createOrder();
    }


}
