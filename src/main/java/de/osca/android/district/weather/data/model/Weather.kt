package de.osca.android.district.weather.data.model

import com.parse.ParseClassName
import com.parse.ParseGeoPoint
import com.parse.ParseObject
import com.parse.ktx.delegates.attribute
import com.parse.ktx.delegates.booleanAttribute
import com.parse.ktx.delegates.stringAttribute
import de.osca.android.district.weather.viewmodel.WeatherDetailViewModel

@ParseClassName("WeatherObserved")
class Weather : ParseObject() {
    var name: String by stringAttribute()
    val shortName: String? by stringAttribute()
    val image: String? by stringAttribute()
    val geopoint: ParseGeoPoint by attribute()
    val values: HashMap<String, HashMap<String, *>> by attribute()
    val maintenance: Boolean? by booleanAttribute()

    fun getValues(): List<WeatherMeasurement> {
        var weatherMeasurements: List<WeatherMeasurement> = emptyList()
        WeatherDetailViewModel.preselectedMeasurementCategories.forEach { key ->
            val weatherMeasurement = WeatherMeasurement()
            weatherMeasurement.unit = values[key]?.get("unit") as? String ?: ""
            weatherMeasurement.name = values[key]?.get("name") as? String ?: key
            weatherMeasurement.value = values[key]?.get("value") as? Double ?: 0.0
            weatherMeasurements = weatherMeasurements.plus(weatherMeasurement)
        }

        return weatherMeasurements
    }
}
