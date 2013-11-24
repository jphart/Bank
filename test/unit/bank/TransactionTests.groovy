package bank

import grails.test.*

class TransactionTests extends GrailsUnitTestCase {
    
    def testTransaction
    def from
    def to
    
    protected void setUp() {
        super.setUp()
        from = new Account(name:"validName1",email:"valid1@test.com", balance: 10.0d)
        to = new Account(name:"validName2",email:"valid2@test.com", balance: 10.0d)
        
        testTransaction = new Transaction(from: from, to:to, amount:5.0d)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNullConstraints() {
        
        mockForConstraintsTests(Transaction, [])
        def transaction = new Transaction()
        assertFalse transaction.validate()
        
        assertEquals "nullable", transaction.errors["from"]
        assertEquals "nullable", transaction.errors["to"]
    }
    
    void testNegativeAmountIsInvalid(){
        testTransaction.amount = -1.0
        mockForConstraintsTests(Transaction, [testTransaction])
        assertFalse testTransaction.validate()
        assertEquals "min", testTransaction.errors["amount"]
    }
    
    void testPayingFromToSameInvalid(){
        
        mockLogging(Transaction)
                
        testTransaction.to = from
        mockForConstraintsTests(Transaction, [testTransaction])
        assertFalse testTransaction.validate()
        assertEquals "transaction.pay.self", testTransaction.errors["to"]
    }
    
    void testValidTransactionSucceeds(){
        mockForConstraintsTests(Transaction, [testTransaction])
        assertTrue testTransaction.validate()
    }
}
