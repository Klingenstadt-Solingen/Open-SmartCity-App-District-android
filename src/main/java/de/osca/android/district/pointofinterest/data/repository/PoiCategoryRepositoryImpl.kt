package de.osca.android.district.pointofinterest.data.repository

import com.parse.ParseQuery
import com.parse.coroutines.suspendFind
import com.parse.ktx.orderByAscending
import com.parse.ktx.selectKeys
import com.parse.ktx.whereEqualTo
import de.osca.android.district.core.CACHE_TIME
import de.osca.android.district.core.data.repository.catchParse
import de.osca.android.district.pointofinterest.data.boundary.PoiCategoryRepository
import de.osca.android.district.pointofinterest.data.model.PoiCategory
import javax.inject.Inject

class PoiCategoryRepositoryImpl
    @Inject
    constructor() : PoiCategoryRepository {
        override suspend fun getPoiCategories(): List<PoiCategory> {
            val query =
                ParseQuery.getQuery(PoiCategory::class.java)
                    .selectKeys(
                        listOf(
                            PoiCategory::sourceId,
                            PoiCategory::name,
                            PoiCategory::symbolPath,
                            PoiCategory::symbolName,
                            PoiCategory::symbolMimetype,
                            PoiCategory::iconPath,
                            PoiCategory::iconName,
                            PoiCategory::iconMimetype,
                        ),
                    )
                    .orderByAscending(PoiCategory::position)
                    .whereEqualTo(PoiCategory::showCategory, "true")
                    .setMaxCacheAge(CACHE_TIME)
                    .setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK)

            return catchParse(query::suspendFind)
        }
    }
