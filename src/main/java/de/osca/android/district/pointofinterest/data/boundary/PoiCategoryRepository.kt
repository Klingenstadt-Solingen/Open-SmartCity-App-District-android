package de.osca.android.district.pointofinterest.data.boundary

import de.osca.android.district.pointofinterest.data.model.PoiCategory

interface PoiCategoryRepository {
    suspend fun getPoiCategories(): List<PoiCategory>
}
