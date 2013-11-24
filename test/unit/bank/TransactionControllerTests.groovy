package bank

import grails.test.*

class TransactionControllerTests extends ControllerUnitTestCase {
    
    def from
    def to
    
    protected void setUp() {
        super.setUp()
        from = new Account(name:"validName1",email:"valid1@test.com", balance: 10.0d)
        to = new Account(name:"validName2",email:"valid2@test.com", balance: 10.0d)
        mockDomain(Account.class, [from, to])
        
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNullableFail() {
       
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand()
        assertFalse paymentCommand.validate()
        assertEquals "paymentcommand.from.null", paymentCommand.errors["from"]
        assertEquals "paymentcommand.to.null", paymentCommand.errors["to"]
    }
    
    void testNonExistentFromAccountFails(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: 1000, to: to.id, amount: 1.0d)
        assertFalse paymentCommand.validate()
        assertEquals "paymentcommand.from.invalid.account", paymentCommand.errors["from"]
    }
    
    void testNonExistentToAccountFails(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: from.id, to: 1000, amount: 1.0d)
        assertFalse paymentCommand.validate()
        assertEquals "paymentcommand.to.invalid.account", paymentCommand.errors["to"]
    }
    
    void testPayingSameAccountFails(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: from.id, to: from.id, amount: 1.0d)
        assertFalse paymentCommand.validate()
        assertEquals "paymentcommand.same.account", paymentCommand.errors["to"]
    }
    
    void testNegativeAmountFails(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: from.id, to: to.id, amount: -1.0d)
        assertFalse paymentCommand.validate()        
        assertEquals "min", paymentCommand.errors["amount"]
    }
    
    void testInsufficientFundsFails(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: from.id, to: to.id, amount: 100.0d)
        assertFalse paymentCommand.validate()        
        assertEquals "paymentcommand.insufficient.funds", paymentCommand.errors["amount"]
    }
    
    void testSufficientFundsSucceeds(){
        mockCommandObject(PaymentCommand.class)

        def paymentCommand = new PaymentCommand(from: from.id, to: to.id, amount: 10.0d)
        assertTrue paymentCommand.validate()
    }
    
    void testIndexRedirectsToUserTransactions(){
       
        this.controller.index()
        assertEquals "userTransactions", this.controller.redirectArgs["action"]
    }
    
    void testUserTransactionsWithoutAccountParamReturnsEmptyModel(){
        
        def model = this.controller.userTransactions()
        assertNull(model)
    }
    
    void testUserTransactionsWithAccountParamReturnsTransactions(){
        
        mockDomain(Account, [new Account(id:1)])
        mockDomain(Transaction, [new Transaction(id: 1, from: Account.get(1))])
        
        this.controller.params.accountId = 1
        def model = this.controller.userTransactions()
        assertNotNull("model not null", model)
        assertNotNull("transactionList not null", model["transactionInstanceList"])
        assertNotNull("transactionListCount not null", model["transactionInstanceTotal"])
        
        assertEquals("Count of list oftransactions correct", 1,model["transactionInstanceList"].size())
        assertEquals("Count of transactionInstanceTotal correct", 1,model["transactionInstanceTotal"])
    }
    
    void testPayModelContainsCommandObject(){
        def model = this.controller.pay()
        assertNotNull("model not null", model)
        assertNotNull("transactionInstance not null", model["transactionInstance"])
    }
    
    void testSaveWithInvalidCommandObjectRendersPayScreen(){
        mockCommandObject(PaymentCommand.class)
        
        def paymentCmd = new PaymentCommand()
        paymentCmd.validate()
        def model = this.controller.save(paymentCmd)
        
        assertEquals "Correct view", "pay", this.controller.renderArgs["view"]
        assertNotNull("model not null", model)
        assertNotNull("transactionInstance not null", model["transactionInstance"])
        
    }
    
    void testSaveWithValidCommandObjectMakesPayment(){
        mockCommandObject(PaymentCommand.class)
        
        /*
         * i18n Messaging will not be available in this unit test, use metaClassing here to
         * fake it for the test. 
        */
        registerMetaClass(TransactionController)
        this.controller.metaClass.message = { Map p -> return "foo" }
        
        long accountId = 1
        def paymentCmd = new PaymentCommand(from: accountId)
        
        def mockPaymentService = mockFor(PaymentService.class)
        mockPaymentService.demand.makePayment(1){ pc ->
            assertEquals("Correct cmd", paymentCmd, pc)
        }
        
        this.controller.paymentService = mockPaymentService.createMock()
        
        //Don't validate payment command, will pass hasErrors check
        this.controller.save(paymentCmd)
        
        assertEquals "Redirect correctly","userTransactions", this.controller.redirectArgs["action"]
        assertNotNull "Flash", this.controller.flash
        
        mockPaymentService.verify()
    }
}
