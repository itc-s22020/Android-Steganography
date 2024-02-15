package jp.ac.it_college.stds.androidsteganography.components.steganography

import android.graphics.Bitmap
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion.bitSetToByteArray
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion.listToBitSet

/*
 * ステガノグラフィーの読み取りを行う関数名
 * readPixels  : readPixels: (IntArray, Int, Int) -> MutableList<Int> # ステガノグラフィーの読み取りに使用する関数
 */
fun Bitmap.readSteganography(readPixels: (IntArray, Int, Int) -> MutableList<Int>): ByteArray {

    if (config == Bitmap.Config.HARDWARE) {
        // Config#HARDWARE の場合は変換して再帰処理
        val softwareBitmap = copy(Bitmap.Config.ARGB_8888, false)
        return softwareBitmap.readSteganography(readPixels)
    }

    val pixels = IntArray(width * height)
    getPixels(pixels, 0, width, 0, 0, width, height)
    return(bitSetToByteArray(listToBitSet(readPixels(pixels, width, height))))
}

