package bank

import grails.test.*
import com.icegreen.greenmail.util.*


class PaymentServiceIntegrationTests extends GroovyTestCase {
    def greenMail
 
    def paymentService
    
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
          greenMail.deleteAllMessages()
    }

     void testSendEmail() {
        
        
        String to = "test@test.com"
        String subject = "subject"
        String msg = "message"
        
        paymentService.sendEmail(to,subject,msg)
        
        
        assertEquals(1, greenMail.getReceivedMessages().length)

        def message = greenMail.getReceivedMessages()[0]

        assertEquals(msg, GreenMailUtil.getBody(message))
        assertEquals(subject, message.subject)
        assertEquals(to, message.to)
        
    }
    
    void testSendPaymentRecievedSendsCorrectMessage(){
        String email = "test@test.com"
        String from = "from"
        double amount = 1.0d
        paymentService.sendPaymentRecieved(email, from, amount)
        
        assertEquals(1, greenMail.getReceivedMessages().length)

        def message = greenMail.getReceivedMessages()[0]

        assertEquals("You have recieved =C2=A3 ${amount} from ${from}", GreenMailUtil.getBody(message))
        assertEquals("Payment Recieved", message.subject)
        assertEquals(email, message.to)
    }
    
    void testSendPaidEmailSendsCorrectMessage(){
        String email = "test@test.com"
        String to = "to"
        double amount = 1.0d
        paymentService.sendPaidEmail(email, to, amount)
        
        assertEquals(1, greenMail.getReceivedMessages().length)

        def message = greenMail.getReceivedMessages()[0]

        assertEquals("You have sent ${to} =C2=A3 ${amount}", GreenMailUtil.getBody(message))
        assertEquals("Payment Send", message.subject)
        assertEquals(email, message.to)
    }
    
    
    void testMakePaymentIfValidUpdatesAccountsSendsEmails(){
        def patty = Account.findByName("Patty")
        def joe = Account.findByName("Joe")
        
        PaymentCommand pc = new PaymentCommand(from: patty.id, to: joe.id, amount: 10.0d)
        
        assertEquals(0, Transaction.count())
        
        paymentService.makePayment(pc)
        
        //Transaction should have occured
        assertEquals(1, Transaction.count())
        
        //Patty's account 10 down
        assertEquals(190.0d, patty.balance)
        //Joe up 10
        assertEquals(210.0d, joe.balance)
        
        //2 emails sent
        assertEquals(2, greenMail.getReceivedMessages().length)
        
    }
}
