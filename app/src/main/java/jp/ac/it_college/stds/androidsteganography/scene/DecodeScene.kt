package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme
import androidx.compose.ui.platform.LocalContext
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.saveAndExtractZip
import jp.ac.it_college.stds.androidsteganography.components.steganography.colorChanger.SimpleLSB
import jp.ac.it_college.stds.androidsteganography.components.steganography.readSteganography

import kotlinx.coroutines.launch

@Composable
fun DecodeScene(modifier: Modifier = Modifier, onDecodeClick: () -> Unit = {}, bm: Bitmap, decSelect: Int) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var zipBitList: MutableList<Int> = remember { mutableListOf<Int>().toMutableList() }
    var byteArray: ByteArray? by remember { mutableStateOf(null) }
    var text by remember { mutableStateOf("") }
    var bool: Boolean by remember {
        mutableStateOf(false)
    }
    var unzipBool: Boolean by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedCard(
            modifier = Modifier
                .width(350.dp)
                .height(220.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Box(modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.align(Alignment.Center),
                    painter = rememberAsyncImagePainter(bm),
                    contentDescription = null
                )
            }
        }
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.padding(20.dp)
            )
        }

        Button(onClick = {
            byteArray = bm.readSteganography(SimpleLSB::read)
            bool = !bool
        }) {
            Text(text = "読み込み")
        }
        Button(onClick = {unzipBool = !unzipBool}) {
            Text(text = "解凍する？")

        }
        Button(onClick = {
            scope.launch {
                saveAndExtractZip(context, byteArray!!, "${text}.zip", "${text}_files", unzipBool)
            }
        },
            enabled = bool
        ) {
            Text(text = "保存")
        }
        Button(onClick = onDecodeClick) {
            Text(text = "終了")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DecodePreview() {
    AndroidSteganographyTheme {
        DecodeScene(Modifier, bm = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888), decSelect = 0)
    }
}