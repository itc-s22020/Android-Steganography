package jp.ac.it_college.stds.androidsteganography.scene

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.stds.androidsteganography.components.common.ImagePickerView
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun StartScene(modifier: Modifier = Modifier, onEncryptClick: () -> Unit = {}, onDecryptClick: () -> Unit = {})  {
}

@Preview(showBackground = true)
@Composable
fun StartScenePreview() {
    AndroidSteganographyTheme {
        StartScene(Modifier)
    }
}