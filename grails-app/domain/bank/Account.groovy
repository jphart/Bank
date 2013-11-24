package bank

/**
* Represents a user's account details
*/
class Account {

    String name
    String email
    double balance
    
    /*
    * Whether the account is active or not. Accounts cannot be deleted if they
    * have participated in a transaction.
    */
    boolean active = true
    
    //GORM autoupdated fields
    Date dateCreated
    Date lastUpdated

    static constraints = {
        name(blank:false, nullable: false, unique: true, size: 3..100)
        email(blank:false, nullable:false, unique:true, email:true)
        
        //For this simple example we won't allow overdrafts
        balance(min:0d)
    }
    
    String toString(){
        return name
    }
}
