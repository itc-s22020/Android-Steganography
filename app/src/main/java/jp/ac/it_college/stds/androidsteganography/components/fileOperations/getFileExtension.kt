package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

/*
 * ファイル拡張子を取得する関数
 * uri     : Uri?      # 取得したいファイルのパス
 * context : Context   # contentResolverを使用するために必要
 * return -> String    # 返り値として拡張子を返す
 */
fun getFileExtension(uri: Uri?, context: Context): String {
    val contentResolver = context.contentResolver

    return uri?.let {
        contentResolver.getType(it)?.let { mimeType ->
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        } ?: uri.pathSegments.lastOrNull()?.substringAfterLast('.', "") ?: "拡張子不明"
    } ?: "拡張子不明"
}