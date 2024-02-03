package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun EncryptResultScene(modifier: Modifier = Modifier, onEndClick: () -> Unit = {}, bm: Bitmap) {
    Column {
        Text(text = "hello")
    }

}

@Preview(showBackground = true)
@Composable
fun EncryptResultScenePreview() {
    AndroidSteganographyTheme {
        EncryptResultScene(Modifier, bm = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
}