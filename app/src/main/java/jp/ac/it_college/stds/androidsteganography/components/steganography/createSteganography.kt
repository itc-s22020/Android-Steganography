package jp.ac.it_college.stds.androidsteganography.components.steganography

import android.graphics.Bitmap
import android.graphics.Color

/*
 * ステガノグラフィーを作成する関数
 * bitsIntArray : MutableList<Int>  # 画像に埋め込むbitデータ
 * changePixels : (IntArray, MutableList<Int>, Int, Int) -> IntArray # 画像情報と埋め込むbitを渡してステガノグラフィーのpixelsを作成する関数
 */
fun Bitmap.createSteganography(bitsIntArray: MutableList<Int>, changePixels:(IntArray, MutableList<Int>, Int, Int) -> IntArray ): Bitmap {

    if (config == Bitmap.Config.HARDWARE) {
        // Config#HARDWARE の場合は変換して再帰処理
        val softwareBitmap = copy(Bitmap.Config.ARGB_8888, false)
        return softwareBitmap.createSteganography(bitsIntArray, changePixels)
    }
    val pixels = IntArray(width * height)
    getPixels(pixels, 0, width, 0, 0, width, height)


    val newPixels = changePixels(pixels, bitsIntArray, width, height)

    println(bitsIntArray.size)
    return copy(Bitmap.Config.ARGB_8888, true).apply {
        setPixels(newPixels, 0, width, 0, 0, width, height)
    }
}