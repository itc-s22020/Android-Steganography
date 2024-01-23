package jp.ac.it_college.stds.androidsteganography.components.steganography

import android.graphics.Bitmap
import android.graphics.Color

/*
 * ステガノグラフィーを作成する関数
 * bitsIntArray : MutableList<Int>  # 画像に埋め込むbitデータ
 * changeColor  : (Int, Int) -> Int # 現在のRGBと埋め込むbitを渡してステガノグラフィーに使用する色を作成する関数
 * endPoint     : Int               # ステガノグラフィの終了位置に使用するInt
 */
fun Bitmap.createSteganography(bitsIntArray: MutableList<Int>, changeColor:(Int, Int) -> Int, endPoint: Int): Bitmap {
    if (config == Bitmap.Config.HARDWARE) {
        // Config#HARDWARE の場合は変換して再帰処理
        val softwareBitmap = copy(Bitmap.Config.ARGB_8888, false)
        return softwareBitmap.createSteganography(bitsIntArray, changeColor, endPoint)
    }

    //byteArrayにendpointを追加
    bitsIntArray.add(endPoint)

    val pixels = IntArray(width * height)
    getPixels(pixels, 0, width, 0, 0, width, height)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val bitsSize = bitsIntArray.size
            val pixelIndex = x + y * width
            val bitsIndex = pixelIndex*3
            val pixel = pixels[pixelIndex]

            //既存のARGBデータ
            val pixelAlpha: Int = (pixel shr 24) and 0xff
            val pixelRed  : Int = (pixel shr 16) and 0xff
            val pixelGreen: Int = (pixel shr 8) and 0xff
            val pixelBlue : Int = pixel and 0xff

            //新しいRGBデータ
            val newRed   = if (bitsSize <= bitsIndex) pixelRed   else changeColor(pixelRed  , bitsIntArray[bitsIndex])
            val newGreen = if (bitsSize <= bitsIndex+1) pixelGreen else changeColor(pixelGreen, bitsIntArray[bitsIndex+1])
            val newBlue  = if (bitsSize <= bitsIndex+2) pixelBlue  else changeColor(pixelBlue , bitsIntArray[bitsIndex+2])

            //set
            pixels[x + y * width] = Color.argb(255, newRed, newGreen, newBlue)
        }
    }
    return copy(Bitmap.Config.ARGB_8888, true).apply {
        setPixels(pixels, 0, width, 0, 0, width, height)
    }
}