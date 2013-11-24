package bank

import grails.test.*

class AccountIntegrationTests extends GroovyTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSaveInvalidAccountFails() {
        def account = new Account()
        
        assertNull account.save()
    }
    
    void testSaveValidAccountSucceeds(){
        
        String testName = "testName"
        
        def account = new Account(name:testName, email:"testEmail@test.com", balance:20.0d)
        
        assertNotNull account.save()
        assertNotNull account.id
        
        def foundAccount = Account.get(account.id)
        assertEquals testName, foundAccount.name
    }
}
