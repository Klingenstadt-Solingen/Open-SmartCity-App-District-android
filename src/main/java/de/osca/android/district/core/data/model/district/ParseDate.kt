package de.osca.android.district.core.data.model.district

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class ParseDate(
    @SerializedName("iso")
    val iso: String,
) {
    fun getDate(): LocalDate {
        return LocalDate.parse(iso)
    }

    companion object {
        fun now() : ParseDate{
            return ParseDate(LocalDate.now().toString())
        }
    }
}
