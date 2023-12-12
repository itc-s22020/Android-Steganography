package jp.ac.it_college.stds.androidsteganography.scene

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun EncryptResultScene(modifier: Modifier = Modifier, onEndClick: () -> Unit = {}) {

}

@Preview(showBackground = true)
@Composable
fun EncryptResultScenePreview() {
    AndroidSteganographyTheme {
        EncryptScene(Modifier)
    }
}