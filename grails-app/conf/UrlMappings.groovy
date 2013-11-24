
import bank.exceptions.PaymentException
class UrlMappings {


	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
                "500"(controller: "errors", action: "payment",exception: PaymentException)
                "500"(view:'/error')
	}
}
