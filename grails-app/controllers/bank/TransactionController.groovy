package bank

class TransactionController {
    
    static allowedMethods = [save: "POST"]
    
    def paymentService

    def index = {
        redirect(action: "userTransactions", params: params)
    }

    
    def userTransactions = {
        
        //If we have an account Id display it's transactions
        if(params.accountId){
            List transactions = Transaction.findAllByFrom(Account.get(params.accountId))
            [transactionInstanceList:transactions, transactionInstanceTotal: transactions.size()]
        }
    }

    def pay = {
        def transactionInstance = new PaymentCommand()
        return [transactionInstance: transactionInstance]
    }

    def save = { PaymentCommand pc ->
        
        if(pc.hasErrors()){
            log.warn("Payment failed due to errors ${pc.errors}")            
            render(view: "pay", model: [transactionInstance: pc])
        } else {           
            
            //Make the payment (Exceptions will be caught by URL mapping exception handling
            paymentService.makePayment(pc)
            
            flash.message = "${message(code: 'transaction.complete')}"
            redirect(action: "userTransactions", params: ["accountId":pc.from])
        }
    }
}


/**
 * Payment command object
 * @author Jonathan
 */
class PaymentCommand {
    
    long from
    long to
    double amount
    
    static constraints = {

        from(nullable: false, validator:{ fromAcc, pc ->
                //Check from is set
                if(!fromAcc){
                    return "paymentcommand.from.null"
                }
                
                //Check from account exists
                if(!Account.get(fromAcc)){
                    return "paymentcommand.from.invalid.account"
                }
            })
        to(nullable: false, validator:{ toAcc, pc ->
                
                //Check to is set
                if(!toAcc){
                    return "paymentcommand.to.null"
                }
                //Check to account exists
                if(!Account.get(toAcc)){
                    return "paymentcommand.to.invalid.account"
                }
                //Check from and to are different accounts
                if(toAcc == pc.from){
                    return "paymentcommand.same.account"
                }
            })
        amount(min:0.01d, validator:{ amount, pc -> 
                //Check the from account has enough credit
                
                if(Account.get(pc.from)){
                    if(Account.get(pc.from).balance < amount){
                        return "paymentcommand.insufficient.funds"
                    }
                }
            })
    }
    
}
