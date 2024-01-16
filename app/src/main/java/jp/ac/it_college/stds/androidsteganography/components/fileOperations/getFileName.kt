package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

/*
 * ファイル名を取得する関数名
 * uri     : Uri     # ファイルのパス
 * context : Context # contentResolverを使うために必要
 */
fun getFileName(uri: Uri, context: Context): String {
    val contentResolver = context.contentResolver
    contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        cursor.moveToFirst()
        val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        return cursor.getString(displayNameIndex ?: 0) ?: "file"
    }
    return "file"
}