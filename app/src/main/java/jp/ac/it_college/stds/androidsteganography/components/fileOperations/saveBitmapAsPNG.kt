package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/* ビットマップをPNGファイルとして指定のパスに保存する関数
 * bm       : Bitmap # 保存するBitmapの画像
 * filePath : String # 保存する場所のpath
 */
fun saveBitmapAsPNG(bm: Bitmap, filePath: String) {
    // ファイルの絶対パスを取得
    val file = File(filePath)

    try {
        // ファイルに書き込むためのストリームを作成
        val stream = FileOutputStream(file)
        // ビットマップをPNG形式でファイルに保存
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.close()
        println("保存完了 $filePath")
    } catch (e: IOException) {
        e.printStackTrace()
    }
}