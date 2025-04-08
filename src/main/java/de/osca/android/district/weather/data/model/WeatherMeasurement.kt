package de.osca.android.district.weather.data.model

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ktx.delegates.doubleAttribute
import com.parse.ktx.delegates.stringAttribute

@ParseClassName("WeatherMeasurement")
class WeatherMeasurement : ParseObject(){

    var name: String by stringAttribute()
    var unit: String by stringAttribute()
    var value: Double by doubleAttribute()

    companion object {
        fun getTemperature(measurements: Array<WeatherMeasurement>?): WeatherMeasurement? {
            var temperatur: WeatherMeasurement? = null
            measurements?.forEach() {
                    if (it.name == "Temperatur") {
                        temperatur = it
                    }
            }
            return temperatur
        }
    }






}