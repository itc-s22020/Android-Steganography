package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/*
 * 複数のファイルを圧縮する関数
 * uris      : List<Uri>                  # 圧縮したいファイルのパスをまとめたList
 * filesData : List<Pair<Uri, ByteArray>> # 圧縮したいファイルのパスとファイルのByteArrayのペアのList
 * context   : Context                    # getFileNameをしようするために必要
 */
fun zipCreate(uris: List<Uri>, filesData: List<Pair<Uri, ByteArray>>, context: Context): ByteArray {
    return ByteArrayOutputStream().use { byteArrayOutputStream ->
        ZipOutputStream(byteArrayOutputStream).use { zipOutputStream ->
            for ((index, uri) in uris.withIndex()) {
                val fileName = getFileName(uri, context)
                val data = filesData[index].second

                zipOutputStream.putNextEntry(ZipEntry(fileName))
                zipOutputStream.write(data)
                zipOutputStream.closeEntry()
            }
        }
        byteArrayOutputStream.toByteArray()
    }
}