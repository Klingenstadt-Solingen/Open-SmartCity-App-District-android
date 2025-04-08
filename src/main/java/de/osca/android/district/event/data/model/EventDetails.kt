package de.osca.android.district.event.data.model

class EventDetails
(event: Event) {
    var prices: List<EventPrice> = emptyList()
    var addressLocality: String = ""
    var name: String = ""
    var postalCode: String = ""
    var streetAdress: String = ""

    init {
        val location = event.location
        val address = location?.get("address") as? HashMap<*, *>
        this.addressLocality = address?.get("addressLocality") as? String ?: ""
        this.name = address?.get("name") as? String ?: ""
        this.postalCode = address?.get("postalCode") as? String ?: ""
        this.streetAdress = address?.get("streetAddress") as? String ?: ""
        val castedPrices = mutableListOf<EventPrice>()
        val offers = event.offers
        offers.forEach { offer ->
            try {
                (offer as? HashMap<String, Any>)?.let { offerMap ->
                    val name = offerMap["name"] as? String
                    val price = offerMap["price"] as? String
                    val priceCurrency = offerMap["priceCurrency"] as? String
                    castedPrices.add(
                        EventPrice(
                            name = name ?: "",
                            price = price ?: "",
                            priceCurrency = priceCurrency ?: "",
                        ),
                    )
                }
            } catch (t: Throwable) {
                // safe casting
            }
        }
        this.prices = castedPrices
    }
}
