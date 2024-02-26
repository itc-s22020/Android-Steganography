package jp.ac.it_college.stds.androidsteganography.components.fileOperations

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun saveByteArrayToFile(file: ByteArray, filename: String, outputFilePath: String) {
    try {
        // ファイルの出力先ディレクトリが存在しない場合は作成する
        val outputDir = File(outputFilePath)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        // 出力先ファイルのパスを構築
        val outputFile = File(outputDir, filename)

        // FileOutputStreamを使用してbyteArrayをファイルに書き込む
        val outputStream = FileOutputStream(outputFile)
        outputStream.write(file)
        outputStream.close()

        println("ファイルが正常に保存されました。Path: ${outputFile.absolutePath}")
    } catch (e: IOException) {
        println("ファイルの保存中にエラーが発生しました。エラーメッセージ: ${e.message}")
    }
}