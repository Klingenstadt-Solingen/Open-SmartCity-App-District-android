package de.osca.android.district.weather.data

import com.parse.ParseObject
import de.osca.android.district.core.data.boundaries.ParseObjectRegistration
import de.osca.android.district.weather.data.model.Weather
import de.osca.android.district.weather.data.model.WeatherMeasurement

class WeatherParseObjectRegistration : ParseObjectRegistration {
    override fun registerSubclasses() {
        ParseObject.registerSubclass(Weather::class.java)
        ParseObject.registerSubclass(WeatherMeasurement::class.java)
    }
}
