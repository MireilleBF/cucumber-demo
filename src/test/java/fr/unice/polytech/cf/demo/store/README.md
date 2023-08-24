# From features to tests

## Under _stocks_: very simple version
**Features** : _GestionDuStock.features_ (in French) and _StockManagment_.features (in English) provide a very basic view of stock management through Cucumber-defined tests.
**Implementations** :The steps are implemented in _GestionDuStockDefinitions_ (in French) and _StockDefinitions_ under _stores_

## Under _stores_: simple version integrating examples
**Features** : _ManageStore.feature_ provides examples of tests that include scenarios with examples.
**Implementations** :The associated steps are defined in _StoreDefinitions.java_.

## Under _withHistory_: first use of containers. 
We want to verify the stock history but do not want to define the history tests in the same context as the stocks.

## Under _centralsystem_: use of explicit containers and dependency injection

We use a facade to manage purchases simply by interacting between the purchasing store and the customer through a central system. For this, we use DAOs to represent knowledge that could be in Databases (customers and stores) and dependency injection to link these classes.

_CustomerManager_ uses Constructor Injection : CustomerManage gets all its dependencies via its constructor (_CustomerDAO_).

Similarly, _StoreManager_ uses Field Injection: StoreManager gets its dependencies via @Inject annotation (_StoreDAO_).

_EcoSystemDefinition_ uses Constructor Injection using a container to share the central system between the steps.

Test classes are defined, don't use cucumber, and explicit the use of containers.

**Features** : _StockWithHistory.features_ defines simple tests that include verification of stock actions.
**Implementations** :The steps are defined in both _StockProcess_ and _StockHistoryProcess_. 
To enable them to share the processed stock, we use a "container" that holds the stock. It is automatically created by the environment and establishes a link between the two classes of step tests.
- In StockProcess's '@Given' section, the stock is created. 
- In StockHistoryProcess, it is used.
There is dependency injection via constructors in a straightforward manner.

## Under _purchasing_: separation of concerns 
Our objective is to show that, through separation into different test classes and 
the use of a shared object (_PurchaseContainer_), we can handle our concerns separately.

- _CustomerDefinition_ implements a few very simple steps for _CustomerManagement.feature_
- _Purchasing.feature_ links the store, the customer, etc.
It defines a PurchaseContainer that will contain the customer, the store, and the purchase itself.
CustomerHistoryProcess, PurchaseProcess, and StoreProcess use this intermediate object.

## Under _payment_: MOCKITO and Dependency Injection
Under _test_ part of _payment_, we use of a container, dependency injection and integration tests  to simulate an external payment service.