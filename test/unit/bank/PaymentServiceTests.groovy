package bank

import grails.test.*
import bank.exceptions.PaymentException

class PaymentServiceTests extends GrailsUnitTestCase {
     
    def service
    
    def from
    def to
    PaymentCommand paymentCmd
    
    protected void setUp() {
        super.setUp()
        mockLogging(PaymentService)
        service = new PaymentService()
        
        from = new Account(name:"validName1",email:"valid1@test.com", balance: 10.0d)
        to = new Account(name:"validName2",email:"valid2@test.com", balance: 10.0d)
        
        mockDomain(Account, [from, to])
        mockDomain(Transaction)
        
        paymentCmd = new PaymentCommand(from: from.id, to: to.id, amount: 5.0d)
    }

    protected void tearDown() {
        super.tearDown()
    }
   
    void testMakePaymentSaveFailsThrowsPaymentException(){
        
        
        
        registerMetaClass Transaction
        Transaction.metaClass.save = { -> return false }
        
        try{
            service.makePayment(paymentCmd)
            fail("Expected exception")
        }
        catch(PaymentException ex){
            assertTrue(true)
        }
        
    }
}
