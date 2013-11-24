package bank.exceptions

/**
 * Exceptions during payment
 * @author Jonathan
 */
class PaymentException extends RuntimeException{
	
    
    PaymentException(String message){
        super(message)
    }
}

