package jp.ac.it_college.stds.androidsteganography.scene

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
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
import jp.ac.it_college.stds.androidsteganography.components.common.SimpleLSB
import jp.ac.it_college.stds.androidsteganography.components.common.readSteganography
import kotlinx.coroutines.launch
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


lateinit var prefSetting: SharedPreferences

@Composable
fun DecodeScene(modifier: Modifier = Modifier, onDecodeClick: () -> Unit = {}, decReceive: Bitmap) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var zipBitList: MutableList<Int> = remember { mutableListOf<Int>().toMutableList() }
    var kara: ByteArray? by remember { mutableStateOf(null) }
    var fileName: String by remember {
        mutableStateOf("")
    }
    var text by remember { mutableStateOf("") }


    println("kara: $kara")

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
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.padding(20.dp)
            )
        }

        Button(onClick = { kara = decReceive.readSteganography(zipBitList, (SimpleLSB::read)) }) {
            Text(text = "読み込み")
        }
//        fileName = "example24.zip"
        Button(onClick = {
            scope.launch {
                saveAndExtractZip(context, kara!!, "${text}.zip", "extracted_files")
            }
        }) {
            Text(text = "保存")
        }
        Button(onClick = onDecodeClick) {
            Text(text = "終了")
        }
    }
}


fun saveAndExtractZip(context: Context, byteArray: ByteArray, zipFileName: String, extractToDirName: String) {
    try {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, zipFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "application/zip")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            ?: throw Exception("ファイル作成に失敗")

        resolver.openOutputStream(uri).use { outputStream ->
            outputStream?.write(byteArray)
        }

        val zipInputStream = ZipInputStream(byteArray.inputStream())
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val extractDir = File(downloadsDir, extractToDirName)

        var zipEntry: ZipEntry? = zipInputStream.nextEntry
        while (zipEntry != null) {
            val file = File(extractDir, zipEntry.name)
            if (zipEntry.isDirectory) {
                file.mkdirs()
            } else {
                file.parentFile?.mkdirs()
                FileOutputStream(file).use { fileOutputStream ->
                    BufferedOutputStream(fileOutputStream).use { bufferedOutputStream ->
                        zipInputStream.copyTo(bufferedOutputStream)
                    }
                }
            }
            zipEntry = zipInputStream.nextEntry
        }
        zipInputStream.closeEntry()
        zipInputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}


@Preview(showBackground = true)
@Composable
fun DecodePreview() {
    AndroidSteganographyTheme {
        DecodeScene(Modifier, decReceive = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))
    }
}