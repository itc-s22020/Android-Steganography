package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream



fun saveAndExtractZip(context: Context, byteArray: ByteArray, zipFileName: String, extractToDirName: String, unzipBool: Boolean) {
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

        if (unzipBool) {
            val zipInputStream = ZipInputStream(byteArray.inputStream())
            val downloadsDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
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
            zipInputStream.close()
        }

    } catch (e: Exception) {
        e.printStackTrace()
    }
}

