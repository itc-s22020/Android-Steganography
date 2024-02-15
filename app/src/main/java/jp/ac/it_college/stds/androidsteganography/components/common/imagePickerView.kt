package jp.ac.it_college.stds.androidsteganography.components.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import kotlin.math.min
import kotlin.math.roundToInt

/*
 * 画像選択可能なImageView
 * modifier       : Modifier          # Columnの修飾子
 * viewBitmap     : Bitmap            # 起動時に表示する画像
 * context        : Context           # contentResolverを使うために必要
 * onBitmapChange : (Bitmap) -> Unit  # 選択された画像が変更されたときに呼び出されるコールバック
 */
@Composable
fun ImagePickerView(
    modifier: Modifier,
    initialBitmap: Bitmap,
    context: Context,
    onBitmapChange: (Bitmap) -> Unit,
) {
    var imageUri: Uri? by remember { mutableStateOf(null) }
    var bitmap: Bitmap by remember { mutableStateOf(initialBitmap) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imageUri = uri
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            val newBitmap = ImageDecoder.decodeBitmap(source)
            if (newBitmap != bitmap) {
                bitmap = newBitmap
                onBitmapChange(bitmap)
            }
        }
    }

    Column(modifier = modifier) {
        Image(
            modifier = Modifier.clickable { launcher.launch("image/*") },
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Image $imageUri"
        )
    }
}

