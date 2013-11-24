package bank.errors

class ErrorsController {

    def payment = {
        log.error("Error during payment",request.exception)
        [exception: request.exception]
    }
}
