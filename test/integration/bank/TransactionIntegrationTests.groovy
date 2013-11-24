package bank

import grails.test.*

class TransactionIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSaveInvalidTransactionFails() {
        def transaction = new Transaction()
        
        assertNull transaction.save()
    }
    
    void testSaveValidTransactionSucceeds(){
        
        String testName = "testName"
        
        def from = new Account(name:"testName1", email:"testEmail1@test.com", balance:20.0d)
        assertNotNull from.save()
        
        def to = new Account(name:"testName2", email:"testEmail2@test.com", balance:20.0d)
        assertNotNull to.save()
        
        def transaction = new Transaction(from: from, to: to, amount: 10.0d)        
        
        assertNotNull transaction.save()
        assertNotNull transaction.id
        
        def foundTransaction = Transaction.get(transaction.id)
        assertEquals 10.0d, foundTransaction.amount
        assertEquals from, foundTransaction.from
        assertEquals to, foundTransaction.to
    }
}
