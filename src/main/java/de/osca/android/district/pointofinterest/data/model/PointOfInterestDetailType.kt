package de.osca.android.district.pointofinterest.data.model

enum class PointOfInterestDetailType {
    TEXT,
    TEL,
    URL,
    MAIL,
    POSITION,
    LIST,
    HTML,
    ;

    companion object {
        fun fromString(type: String): PointOfInterestDetailType {
            val t = type.uppercase()

            for (e in entries) {
                if (e.name.uppercase() == t) {
                    return e
                }
            }

            return TEXT
        }
    }
}
