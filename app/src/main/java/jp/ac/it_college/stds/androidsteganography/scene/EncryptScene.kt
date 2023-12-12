package jp.ac.it_college.stds.androidsteganography.scene

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun EncryptScene(modifier: Modifier = Modifier, onEncryptResult: () -> Unit = {}) {

}

@Preview(showBackground = true)
@Composable
fun EncryptScenePreview() {
    AndroidSteganographyTheme {
        EncryptScene(Modifier)
    }
}