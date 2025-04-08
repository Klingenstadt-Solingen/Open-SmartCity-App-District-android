package de.osca.android.district.politics.presentation.component

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import de.osca.android.district.R
import de.osca.android.district.core.presentation.design.DistrictDesign

@SuppressLint("SuspiciousIndentation")
@Composable
fun MeetingFileItemView( downloadUrl: String,
    name: String,
    mimeType: String){
    val context = LocalContext.current

    Row(Modifier.fillMaxWidth().clickable {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl))
        startActivity(context, browserIntent,null)
    }, verticalAlignment = Alignment.CenterVertically) {
        Box() {
            Icon(
                painter = painterResource(id = R.drawable.ic_file),
                contentDescription = "",
                modifier = Modifier
                    .size(DistrictDesign.Size.Icon.BIG)
            )
            Text(mimeType.split("/").last().uppercase(),
                modifier = Modifier.align(Alignment.Center),
                fontSize = DistrictDesign.Size.Font.SMALLER_TEXT)
        }
        Text(name, modifier = Modifier.padding(start = DistrictDesign.Spacing.DEFAULT), fontSize = DistrictDesign.Size.Font.NORMAL_TEXT, maxLines = 2)
    }
}

@Preview
@Composable
fun MeetingFileItemViewPreview()
{
    MeetingFileItemView(downloadUrl= "", name= "Test", mimeType= "application/pdf")
}
