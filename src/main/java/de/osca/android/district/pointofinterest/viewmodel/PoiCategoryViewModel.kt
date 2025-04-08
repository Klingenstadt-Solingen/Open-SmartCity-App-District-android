package de.osca.android.district.pointofinterest.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.osca.android.district.core.data.model.LoadingState
import de.osca.android.district.core.data.resetWith
import de.osca.android.district.core.viewmodel.Loadable
import de.osca.android.district.pointofinterest.data.boundary.PoiCategoryRepository
import de.osca.android.district.pointofinterest.data.model.PoiCategory
import javax.inject.Inject

@HiltViewModel
class PoiCategoryViewModel
    @Inject
    constructor(
        private val repository: PoiCategoryRepository,
    ) : ViewModel(),
        Loadable<Void?> {
        override var loadingState by mutableStateOf<LoadingState<Void?>>(LoadingState.Loading)

        var categories = mutableStateListOf<PoiCategory>()
        var selectedCategories by mutableStateOf<Set<String>>(emptySet())

        /**
         * @param categoryId optional category sourceId to initialize [selectedCategories] with
         */
        suspend fun initCategories(categoryId: String? = null) {
            loadingStateScope {
                categories.resetWith(repository.getPoiCategories())

                selectedCategories =
                    if (categoryId == null) {
                        categories.map { it.sourceId }.toSet()
                    } else {
                        setOf(categoryId)
                    }
                finishLoading(null)
            }
        }

        fun requestCategories() {
            loadingStateScope {
                categories.resetWith(repository.getPoiCategories())
                finishLoading(null)
            }
        }

        fun selectCategory(
            category: PoiCategory,
            selected: Boolean,
        ) {
            if (selected) {
                val list = selectedCategories.toMutableSet()
                list.add(category.sourceId)
                selectedCategories = list
            } else {
                val list = selectedCategories.toMutableSet()
                list.remove(category.sourceId)
                selectedCategories = list
            }
        }

        fun getCategory(sourceId: String?): PoiCategory? {
            for (cat in categories) {
                if (cat.sourceId == sourceId) return cat
            }
            return null
        }
    }
