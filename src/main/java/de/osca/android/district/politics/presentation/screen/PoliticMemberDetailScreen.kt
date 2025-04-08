package de.osca.android.district.politics.presentation.screen
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import de.osca.android.district.R
import de.osca.android.district.core.paging.PagingRefreshEffect
import de.osca.android.district.core.presentation.component.TopBarScaffold
import de.osca.android.district.core.presentation.component.pager.applyPagingHandler
import de.osca.android.district.core.presentation.design.DistrictDesign
import de.osca.android.district.core.presentation.design.defaultTextStyle
import de.osca.android.district.politics.data.model.PoliticMembership
import de.osca.android.district.politics.viewmodel.PoliticMemberDetailViewModel

@Composable
fun PoliticMemberDetailScreen(
    politicMemberDetailViewModel: PoliticMemberDetailViewModel = hiltViewModel(),
    member: PoliticMembership
){
    politicMemberDetailViewModel.updatePersonId(member.person.id)

    val scrollState = rememberLazyListState()

    val memberships = politicMemberDetailViewModel.memberships.collectAsLazyPagingItems()

    val isRefreshing = remember { mutableStateOf(false) }

    PagingRefreshEffect(memberships, isRefreshing)

    val context = LocalContext.current
    TopBarScaffold { innerPadding ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(vertical= DistrictDesign.Padding.MEDIUM),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            member.person.let { person ->
                Column(modifier = Modifier
                    .padding(horizontal = DistrictDesign.Padding.BIGGER),
                    horizontalAlignment = Alignment.Start) {
                    Text(
                        text = person.name(),
                        modifier = Modifier.padding(top = DistrictDesign.Spacing.DEFAULT),
                        style =
                        defaultTextStyle(
                            fontSize = DistrictDesign.Size.Font.HEADLINE,
                            fontWeight = FontWeight.Bold,
                        ),
                    )

                    member.role?.let { role ->
                        Text(role,
                            modifier = Modifier.padding(top = DistrictDesign.Spacing.DEFAULT),
                            fontSize = DistrictDesign.Size.Font.NORMAL_TEXT)

                    }

                    person.email.forEach { email ->
                        Text(email,
                            fontSize = DistrictDesign.Size.Font.DEFAULT,
                            color = Color.Blue,
                            modifier = Modifier
                                .padding(top = DistrictDesign.Spacing.DEFAULT)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_SEND)
                                    intent.type = "vnd.android.cursor.item/email"
                                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                                    startActivity(context, intent, null)
                                })
                    }

                    person.phone.forEach { phone ->
                        Text(phone,
                            fontSize = DistrictDesign.Size.Font.DEFAULT,
                            color = Color.Blue,
                            modifier = Modifier
                                    .padding(top = DistrictDesign.Spacing.DEFAULT)
                                    .clickable {
                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                        startActivity(context, intent, null)
                                    })
                    }
                    Text(stringResource(id = R.string.politic_memberships),
                        modifier = Modifier.padding(top = DistrictDesign.Spacing.DEFAULT),
                        style = defaultTextStyle(
                        fontSize = DistrictDesign.Size.Font.SUB_SUB_TITLE,
                        fontWeight = FontWeight.Bold,

                    ))

                    LazyColumn(state = scrollState, modifier = Modifier.padding(top = DistrictDesign.Spacing.BIG), verticalArrangement = Arrangement.spacedBy(DistrictDesign.Spacing.BIG)) {
                        items(memberships.itemCount) { index ->
                            memberships[index]?.let { membership ->
                                Column {
                                    membership.role?.let { Text(it, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT, fontWeight = FontWeight.Bold) }
                                    membership.organizationName?.let { Text(it, fontSize = DistrictDesign.Size.Font.NORMAL_TEXT) }
                                }
                            }
                        }
                        applyPagingHandler(memberships)
                    }
                }
            }
        }
    }
}
