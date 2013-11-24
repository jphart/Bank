
import bank.Account

class BootStrap {

    def init = { servletContext ->
        
        new Account(name:"Patty", email:"patty@test.com", balance: 200.0d).save(failOnError:true)
        new Account(name:"Joe", email:"joe@test.com", balance: 200.0d).save(failOnError:true)
    }
    def destroy = {
    }
}
