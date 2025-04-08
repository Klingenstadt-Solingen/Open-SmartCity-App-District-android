package de.osca.android.district.project.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.osca.android.district.R
import de.osca.android.district.core.presentation.component.FadingEdges
import de.osca.android.district.core.presentation.component.button.SelectableTextButton
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.project.data.model.ProjectStatus

@Composable
fun ProjectStatusFilter(
    projectStatus: ProjectStatus?,
    statusList: List<ProjectStatus>,
    setStatus: (ProjectStatus?) -> (Unit),
) {
    Box(modifier = Modifier.height(70.dp)) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(DistrictDesign.Padding.MEDIUM),
            contentPadding =
                PaddingValues(
                    top = DistrictDesign.Padding.MEDIUM,
                    start = DistrictDesign.Padding.BIG,
                    end = DistrictDesign.Padding.BIG,
                ),
        ) {
            item {
                SelectableTextButton(
                    id = R.string.project_all,
                    onClick = {
                        setStatus(null)
                    },
                    selected = projectStatus == null,
                )
            }
            items(statusList) { status ->
                val isSelected = projectStatus == status
                SelectableTextButton(
                    text = stringResource(id = status.getStringResource()),
                    onClick = {
                        setStatus(status)
                    },
                    selected = isSelected,
                )
            }
        }
        FadingEdges(DistrictDesign.Padding.MEDIUM)
    }
}
