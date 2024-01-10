package jp.ac.it_college.stds.androidsteganography.scene

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import jp.ac.it_college.stds.androidsteganography.R
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme
import java.nio.ByteBuffer
import android.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        println(extractHiddenImage(decReceive))
    }
}

fun extractHiddenImage(bitmap: Bitmap) : StringBuilder {
    val pixels = IntArray(bitmap.width * bitmap.height)
    val stegoSoft = bitmap.copy(Bitmap.Config.ARGB_8888, true)

    stegoSoft.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    val hiddenData = StringBuilder()


    for (y in 0 until bitmap.height) {
        for (x in 0 until bitmap.width) {
            val pixel = pixels[x + y * bitmap.width]

//            val pixelAlpha = (pixels[x + y * bitmap.width] shr 24) and 1
            val pixelRed = pixel and 1
            val pixelGreen = (pixel shr 8) and 1
            val pixelBlue = (pixel shr 16) and 1

            hiddenData.append(pixelRed, pixelGreen, pixelBlue)
        }
    }
    return hiddenData
}

@Preview(showBackground = true)
@Composable
fun DecodePreview() {
    AndroidSteganographyTheme {
        DecodeScene(Modifier, decReceive = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
}



//@Composable
//fun DecodeScene(modifier: Modifier = Modifier, onDecodeClick: () -> Unit = {}, decReceive: Bitmap) {
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        OutlinedCard(
//            modifier = Modifier
//                .width(350.dp)
//                .height(220.dp)
//                .align(Alignment.CenterHorizontally),
//        ) {
//            Image(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally),
//                painter = rememberAsyncImagePainter(decReceive),
//                contentDescription = null,
//            )
//        }
//        val extractedBitmap = extractHiddenImage(decReceive)
//        OutlinedCard(
//            modifier = Modifier
//                .width(350.dp)
//                .height(220.dp)
//                .align(Alignment.CenterHorizontally)
//                .background(
//                    color = Color(0xFFFF0000)
//                ),
//        ) {
//            Image(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .background(
//                        color = Color(0xFFFF0000)
//                    ),
//                painter = rememberAsyncImagePainter(extractedBitmap),
//                contentDescription = null,
//            )
//        }
//    }
//}

fun extractHiddenImage2(stego: Bitmap): Bitmap {

    val stegoSoft = stego.copy(Bitmap.Config.ARGB_8888, true)

    val width = stegoSoft.width
    val height = stegoSoft.height
    val extractedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val buffer = ByteBuffer.allocate(stegoSoft.byteCount)

    stegoSoft.copyPixelsToBuffer(buffer)
    buffer.rewind()

    for (x in 0 until width) {
        for (y in 0 until height) {
            val stegoPixel = buffer.int

            val hiddenAlpha = (stegoPixel shr 24) and 1
            val hiddenRed = (stegoPixel shr 16) and 1
            val hiddenGreen = (stegoPixel shr 8) and 1
            val hiddenBlue = stegoPixel and 1


            val extractedPixel = (hiddenAlpha shl 24) or
                    (hiddenRed shl 16) or
                    (hiddenGreen shl 8) or
                    hiddenBlue

            extractedBitmap.setPixel(x, y, extractedPixel)
        }
    }
    return extractedBitmap
}

//@Preview(showBackground = true)
//@Composable
//fun DecodePreview() {
//    AndroidSteganographyTheme {
//        DecodeScene(Modifier, decReceive = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
//    }
//}


//-------
//
//@Composable
//fun extractHiddenImage(stego: Bitmap): Bitmap {
//
//    val width = stego.width
//    val height = stego.height
//    var extractedBitmap by remember {
//        mutableStateOf(
//            Bitmap.createBitmap(
//                1,
//                1,
//                Bitmap.Config.ARGB_8888
//            )
//        )
//    }
//
//    for (x in 0 until width) {
//        for (y in 0 until height) {
//            val stegoPixel = stego.getPixel(x, y)
//
//            // フォーマットはRGBA（32ビット）と仮定
//            val hiddenAlpha = (stegoPixel shr 24) and 1
//            val hiddenRed = (stegoPixel shr 16) and 1
//            val hiddenGreen = (stegoPixel shr 8) and 1
//            val hiddenBlue = stegoPixel and 1
//
//            //隠された画像を再構築
//            val extractedPixel = (hiddenAlpha shl 24) or
//                    (hiddenRed shl 16) or
//                    (hiddenGreen shl 8) or
//                    hiddenBlue
//
//            extractedBitmap.setPixel(x, y, extractedPixel)
//        }
//    }
//    return extractedBitmap
//}
//
//