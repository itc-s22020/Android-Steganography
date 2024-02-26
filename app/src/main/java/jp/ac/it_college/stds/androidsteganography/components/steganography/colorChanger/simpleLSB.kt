package jp.ac.it_college.stds.androidsteganography.components.steganography.colorChanger

import android.graphics.Color


object SimpleLSB{
    /*
     * ステガノグラフィの作成に使う関数 LSB方式
     * (color%3)==bit になるようにpixelsを変換して返す
     * pixels   : IntArray         # 変換するpixels
     * bit      : MutableList<Int> # 埋め込むbit
     * bmHeight : Int              # 画像の縦
     * bmWidth  : Int              # 画像の横
     */
    fun create(pixels: IntArray ,bits: MutableList<Int>, bmWidth:Int, bmHeight: Int): IntArray {
        bits.add(2)
        for (y in 0 until bmHeight) {
            for (x in 0 until bmWidth) {
                val bitsSize = bits.size
                val pixelIndex = x + y * bmWidth
                val bitsIndex = pixelIndex*3
                val pixel = pixels[pixelIndex]

                //既存のARGBデータ
                val pixelAlpha: Int = (pixel shr 24) and 0xff
                val pixelRed  : Int = (pixel shr 16) and 0xff
                val pixelGreen: Int = (pixel shr 8) and 0xff
                val pixelBlue : Int = pixel and 0xff


                //新しいRGBデータ
                val newRed   = if (bitsSize <= bitsIndex)   pixelRed   else changeColor(pixelRed  , bits[bitsIndex])
                val newGreen = if (bitsSize <= bitsIndex+1) pixelGreen else changeColor(pixelGreen, bits[bitsIndex+1])
                val newBlue  = if (bitsSize <= bitsIndex+2) pixelBlue  else changeColor(pixelBlue , bits[bitsIndex+2])

                //set
                pixels[x + y * bmWidth] = Color.argb(255, newRed, newGreen, newBlue)
            }
        }
        return pixels
    }


    /*
     * 作成したステガノグラフィの読み取りに使う関数 LSB方式
     *　(color%3)が2になるまで(color%3)=bitとして情報を取り出す
     * pixels  : IntArray # 読み取る画像のpixel情報
     * bmHeight: Int      # 画像の縦
     * bmWidth : Int      # 画像の横
     */
    fun read(pixels: IntArray, bmWidth:Int, bmHeight: Int): MutableList<Int> {
        val steganographyBits = mutableListOf<Int>()
        var end = false
        for (y in 0 until bmHeight) {
            if (end) { break }
            for (x in 0 until bmWidth) {
                val pixelIndex = x + y * bmWidth
                val pixel = pixels[pixelIndex]

                //pixelのARGBデータ
                val pixelAlpha: Int = (pixel shr 24) and 0xff
                val pixelRed  : Int = (pixel shr 16) and 0xff
                val pixelGreen: Int = (pixel shr 8) and 0xff
                val pixelBlue : Int = pixel and 0xff

                val pixelRGBs = listOf(pixelRed, pixelGreen, pixelBlue)

                for (color in pixelRGBs) {
                    if (end) {break}
                    val modColor = color % 3
                    if (modColor == 2) {
                        end = true
                        break
                    }
                    steganographyBits.add(modColor)
                }
            }
        }
        println(steganographyBits.size)
        return steganographyBits
    }

    /*
    * ステガノグラフィの作成に使う関数 LSB方式
    * (color%3)==bit になるようにcolorを変換して返す
    * color : Int # 変換する色
    * bit   : Int # 埋め込むbit
    */
    private fun changeColor(color: Int, bit: Int): Int {
        val colorMod = color % 3
        //既にcolorModとbitが一致している場合はそのまま返す
        if (colorMod == bit) {return color}

        //colorが126以上の場合は減算処理で色を変換
        return if (color >= 126) {
            if (colorMod > bit) { color - colorMod + bit }
            else { color - (3 - bit + colorMod) }
            //colorが125以下の場合は加算処理で色を変換
        } else {
            if (colorMod > bit) { color + 2 * colorMod + bit }
            else { color + bit - colorMod }
        }
    }
}
