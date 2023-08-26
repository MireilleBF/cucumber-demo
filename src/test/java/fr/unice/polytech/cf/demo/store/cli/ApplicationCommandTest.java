package fr.unice.polytech.cf.demo.store.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ApplicationCommandTest {


    /**
     * Test the command line interface
     * But as we don't return anything, we can't test the result
     * We just test that no exception is thrown
     * And we can see the result in the console
     * @throws Exception
     */
    @Test
    void cliNonAutomaticallyTesting() {
        System.out.println("------ Find existing customer");
        ApplicationCommand.main(new String[]{"find-customer", "-n", "John"});
        assertTrue(true);//No exception thrown
        System.out.println("------ Find all existing customers (two ways)");
        ApplicationCommand.main(new String[]{"find-customer", "-A"});
        ApplicationCommand.main(new String[]{"find-customer", "--all"});
        assertTrue(true);
        System.out.println("------ Find non existing customer");
        ApplicationCommand.main(new String[]{"find-customer", "-n", "John Doe"});
        assertTrue(true);
        System.out.println("------ Create a customer whose the name is John Doe");
        ApplicationCommand.main(new String[]{"create-customer","-c", "John Doe"});
        //without persistence we can't find the customer created before
        ApplicationCommand.main(new String[]{"find-customer", "-n", "John Doe"});
        assertTrue(true);
        System.out.println("------ Create a store whose the name is polygone");
        ApplicationCommand.main(new String[]{"stores", "-c", "-s", "polygone"});
        assertTrue(true);
        System.out.println("------ Find existing stores");
        ApplicationCommand.main(new String[]{"find-stores"});
        assertTrue(true);
        System.out.println("------ Find an existing store");
        ApplicationCommand.main(new String[]{"stores", "-s", "Carrefour"});
        assertTrue(true);
        System.out.println("------ Find a non existing store");
        ApplicationCommand.main(new String[]{"stores", "-s", "BlueSky"});
        assertTrue(true);


        System.out.println("------ Test order with valid payment and valid customer");
        ApplicationCommand.main(new String[]{"order", "-c", "John", "-s", "cap3000", "-d", "mac", "-p", "1000", "-v", "visa100"});
        System.out.println("------ Test order with non valid payment and valid customer");
        ApplicationCommand.main(new String[]{"order", "-c", "Jack", "-s", "polygone", "-d", "acer", "-p", "1000"});
        System.out.println("------ Test order with valid payment and non valid customer");
        ApplicationCommand.main(new String[]{"order", "-c", "Jane", "-s", "polygone", "-d", "acer", "-p", "1000","-v", "visa100"});

        assertTrue(true);
    }

}