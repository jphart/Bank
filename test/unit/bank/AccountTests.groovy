package bank

import grails.test.*

class AccountTests extends GrailsUnitTestCase {
    
    def testAccount
    
    protected void setUp() {
        super.setUp()
        
        testAccount = new Account(name:"validName",email:"valid@test.com", balance: 10.0d)
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testNullConstraints() {
        
        mockForConstraintsTests(Account, [])
        def account = new Account()
        assertFalse account.validate()
        
        assertEquals "nullable", account.errors["name"]
        assertEquals "nullable", account.errors["email"]
    }
    
    void testEmailNotValidIsInvalid(){
        
        testAccount.email = "invalid"
        mockForConstraintsTests(Account, [testAccount])
        assertFalse testAccount.validate()
        assertEquals "email", testAccount.errors["email"]
    }
    
    void testNegativeBalanceIsInvalid(){
        testAccount.balance = -1.0
        mockForConstraintsTests(Account, [testAccount])
        assertFalse testAccount.validate()
        assertEquals "min", testAccount.errors["balance"]
        
    }
    
    void testValidAccountValidates(){
        mockForConstraintsTests(Account, [testAccount])
        assertTrue testAccount.validate()
    }
}
