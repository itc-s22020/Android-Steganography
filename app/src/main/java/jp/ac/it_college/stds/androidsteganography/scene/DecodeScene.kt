package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme
import java.nio.ByteBuffer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.toUpperCase
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.text.StringBuilder


@Composable
fun DecodeScene(modifier: Modifier = Modifier, onDecodeClick: () -> Unit = {}, decReceive: Bitmap) {
    val context = LocalContext.current


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
                    painter = rememberAsyncImagePainter(decReceive),
                    contentDescription = null
                )
            }
        }
        val bitList = extractHiddenImage(decReceive)

        val byteArrayList = convertListToByteArray(bitList)
//        println("test : ${restoreFileFromByteArray(byteArrayList, context.filesDir.absolutePath + "/restored_file.zip")}")

        Button(onClick = { TODO() }) {
            Text(text = "保存")
        }
        Button(onClick = onDecodeClick) {
            Text(text = "終了")
        }
    }
}



fun extractHiddenImage(bitmap: Bitmap): MutableList<Int> {
    val pixels = IntArray(bitmap.width * bitmap.height)
    val stegoSoft = bitmap.copy(Bitmap.Config.ARGB_8888, true)

    stegoSoft.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    val hiddenData = mutableListOf<Int>()


    for (y in 0 until stegoSoft.width) {
        for (x in 0 until stegoSoft.height) {
            val pixel = pixels[x + y * bitmap.width]

//            val pixelAlpha = (pixels[x + y * bitmap.width] shr 24) and 1
            val pixelRed = (pixel shr 16) and 0xff
            val pixelGreen = (pixel shr 8) and 0xff
            val pixelBlue = pixel and 0xff

            hiddenData.apply {
                add(pixelRed % 3)
                add(pixelGreen % 3)
                add(pixelBlue % 3)
            }
        }
    }
    return hiddenData
}

fun convertListToByteArray(list: List<Int>): ByteArray {
    val byteArray = ByteArray(list.size)
    for (i in list.indices) {
        byteArray[i] = list[i].toByte()
    }
    return byteArray
}


@Preview(showBackground = true)
@Composable
fun DecodePreview() {
    AndroidSteganographyTheme {
        DecodeScene(Modifier, decReceive = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
}

