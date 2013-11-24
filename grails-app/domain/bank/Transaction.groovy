package bank

/**
* Domain model for holding transactions between to Accounts
*/
class Transaction {

    //Where the money is transferred from
    Account from
    
    //Where the money went to
    Account to
    
    //How much was transferred
    double amount
    
    //GORM autoupdated value for when the transaction occured
    Date dateCreated

    static constraints = {
        /*
        * Making from / to not nullable means that account that is part of a 
        * transaction can not be deleted. This preserves transaction history.
        */
        from(nullable: false)
        to(nullable: false,validator: { payTo, trans ->
                if(payTo == trans.from){
                    trans.log.warn("Attempted to pay to self: ${payTo}")
                    return "transaction.pay.self"
                }
            })
        
        /*
         * 'double' so implicity not nullable.
         * MinSize of 0 to prevent negative transfers
        */
        amount(min:0.01d)
    }
    
    String toString(){
        return "Payment from ${from.name} to ${to.name} for ${amount}"
    }
}
