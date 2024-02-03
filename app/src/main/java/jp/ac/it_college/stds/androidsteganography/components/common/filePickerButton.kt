package jp.ac.it_college.stds.androidsteganography.components.common

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.zipCreate


/*
 * 複数ファイル選択して圧縮を行うButton
 * modifier       : Modifier            # Buttonの修飾子
 * textModifier   : Modifier            # Textの修飾子
 * btText         : String              # Buttonに表示するText
 * context        : Context             # contentResolverを使うために必要
 * onFilesChange : (ByteArray) -> Unit  # ファイルが選択されたときに呼び出されるコールバック(zipデータのByteArray)
 */
@Composable
fun FilePickerButton(
    modifier: Modifier,
    textModifier : Modifier,
    btText: String,
    context: Context,
    onFilesChange: (ByteArray) -> Unit,
) {
    var fileUris: List<Uri>? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri>? ->
        if (uris != null) {
            fileUris = uris
            val contentResolver = context.contentResolver

            val filesData = mutableListOf<Pair<Uri, ByteArray>>()

            for (uri in uris) {
                val inputStream = contentResolver.openInputStream(uri)
                val fileByteArray = inputStream?.readBytes() ?: byteArrayOf()

                filesData.add(Pair(uri, fileByteArray))

                inputStream?.close()
            }

            val zipByteArray = zipCreate(uris, filesData, context)
            onFilesChange(zipByteArray)
        }
    }

    Button(
        onClick = { launcher.launch("*/*") },
        modifier = modifier
    ) {
        Text(
            modifier = textModifier,
            text = btText)
    }
}