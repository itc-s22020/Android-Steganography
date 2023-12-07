package jp.ac.it_college.stds.androidsteganography.components.common

import android.content.Context
import android.graphics.Bitmap
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

@Composable
fun InputImageView(
    modifier: Modifier,
    viewBitmap: Bitmap,
    context: Context,
    onBitmapChange: (Bitmap) -> Unit,
) {
    var imageUri: Uri? by remember { mutableStateOf(null) }
    var bitmap: Bitmap by remember { mutableStateOf(viewBitmap) }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
            if (uri == null) return@rememberLauncherForActivityResult
            imageUri = uri
        }
    imageUri?.let {
        val source = ImageDecoder
            .createSource(
                context.contentResolver,
                it,
            )
        bitmap = ImageDecoder.decodeBitmap(source)
        onBitmapChange(bitmap)
    }
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = modifier.clickable
            { launcher.launch("image/*") },
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "画像受取"
        )
    }
}