package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.ac.it_college.stds.androidsteganography.components.common.ImagePickerView
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme
import kotlin.math.roundToInt

@Composable
fun StartScene(modifier: Modifier = Modifier, onEncryptClick: () -> Unit = {}, onDecryptClick: () -> Unit = {})  {
    var testBitmap by remember { mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)) }
    val initialBitmap by remember { mutableStateOf(Bitmap.createBitmap(3500, 2200, Bitmap.Config.ARGB_8888)) }
    Column (
        modifier = Modifier.fillMaxSize(),
    ) {
        OutlinedCard(
            modifier = Modifier
                .width(350.dp)
                .height(220.dp)
                .align(Alignment.CenterHorizontally),

        ) {
            Box(modifier.fillMaxSize()){
                Text(text = "画像選択", modifier.align(Alignment.Center))
                ImagePickerView(
                    modifier = Modifier.align(Alignment.Center),
                    initialBitmap = initialBitmap,
                    context = LocalContext.current,
                    onBitmapChange = {testBitmap = it}
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun StartScenePreview() {
    AndroidSteganographyTheme {
        StartScene(Modifier)
    }
}