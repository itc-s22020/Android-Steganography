package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import jp.ac.it_college.stds.androidsteganography.FilePickerButton
import jp.ac.it_college.stds.androidsteganography.components.common.SimpleLSB
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion.byteArrayToIntList
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.saveBitmapAsPNG
import jp.ac.it_college.stds.androidsteganography.components.steganography.createSteganography
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun EncryptScene(
    modifier: Modifier = Modifier,
    onEncryptResult: (Bitmap) -> Unit = {},
    bm: Bitmap,
    encSelect: Int
) {
    var zipBitList: MutableList<Int> = remember { mutableListOf<Int>().toMutableList() }
    var context = LocalContext.current
    var zipByte: ByteArray? by remember { mutableStateOf(null) }

//    zipBitList = zipRead(fileUri)
    zipBitList = byteArrayToIntList(zipByte)


    Column {
        FilePickerButton(Modifier, context, onFilesChange = { zipByte = it })
        Button(onClick = {
            when (encSelect) {
                0 -> saveBitmapAsPNG(
                    bm.createSteganography(zipBitList, (SimpleLSB::create)),
                    "/sdcard/Download/image.png"
                )
                1 -> saveBitmapAsPNG(
                    bm.createSteganography(zipBitList, (SimpleLSB::create)),
                    "/sdcard/Download/image.png"
                )
//                2 ->
            }
        }) {
            Text(text = "保存")
        }
        Button(onClick = { onEncryptResult(bm) }) {
            Text(text = "結果画面へ")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EncryptScenePreview() {
    AndroidSteganographyTheme {
        EncryptScene(
            Modifier,
            bm = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
            encSelect = 0
        )
    }
}