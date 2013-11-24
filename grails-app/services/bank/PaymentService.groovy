package bank

import bank.exceptions.PaymentException

class PaymentService {

    static transactional = true

    def mailService
    
    /**
    * Makes a payment from the accounts given in the command object
    *  
    * @param paymentCmd fully populated & validated PaymentCommand
    * @throws PaymentException Thrown if there is an error during payment processing
    */
    void makePayment(PaymentCommand paymentCmd) throws PaymentException{
        
        log.debug("Processing payment ${paymentCmd}")
        
        //Decrement from account
        def fromAccount = Account.get(paymentCmd.from)
        fromAccount.balance -= paymentCmd.amount
        log.trace("${fromAccount} new balance ${fromAccount.balance}")
        
        //Increment to account
        def toAccount = Account.get(paymentCmd.to)
        toAccount.balance += paymentCmd.amount
        log.trace("${toAccount} new balance ${toAccount.balance}")
        
        //Create transaction (Consider using factory in productionized code)
        def transaction = new Transaction(from: fromAccount, to:toAccount, amount: paymentCmd.amount)
        if(!transaction.save()){
            //Causes rollback of Account balances to previous state
            throw new PaymentException("Failed to create transaction")
        }
        
        //Send emails       
        sendPaidEmail(fromAccount.email, toAccount.name, paymentCmd.amount)
        log.trace("Paid email sent to ${fromAccount.email}")
        sendPaymentRecieved(toAccount.email, fromAccount.name, paymentCmd.amount)
        log.trace("Payment recieved email sent to ${toAccount.email}")    
        
        log.info("Payment of ${paymentCmd.amount} from ${fromAccount} to ${toAccount} successful")
    }
    
    /**
     * Sends a payment sent email
     * 
     *  @param emailAddress The address to send to
     *  @to from who the payment is to
     *  @param amount the amount of the payment  
     */
    void sendPaidEmail(String emailAddress, String to, double amount){
        String subject = "Payment Send"
        String message = "You have sent ${to} £ ${amount}"
        sendEmail(emailAddress, subject, message)
    }
    
    /**
     * Sends a payment recieved email
     * 
     *  @param emailAddress The address to send to
     *  @param from who the payment is from
     *  @param amount the amount of the payment 
     */
    void sendPaymentRecieved(String emailAddress, String from, double amount){
        String subject = "Payment Recieved"
        String message = "You have recieved £ ${amount} from ${from}"
        sendEmail(emailAddress, subject, message)
    }
    
    /**
    * Sends an email using the Mail Plugin
    */
    void sendEmail(String toAddress, String subjectLine, String message){
        mailService.sendMail {
            to toAddress
            subject subjectLine
            html message
        }
    }    
}
