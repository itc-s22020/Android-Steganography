package jp.ac.it_college.stds.androidsteganography.components.steganography.colorChanger

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion.byteArrayToIntList
import java.io.ByteArrayOutputStream
import java.util.EnumMap

object QrCodeLSB {
    /*
     * qrコードステガノグラフィの作成に使う関数 LSB方式
     * 画像に正方形のQRコードを埋め込んで返す
     * pixels   : IntArray         # 変換するpixels
     * qrBits   : MutableList<Int> # 埋め込むqrデータ
     * bmHeight : Int              # 画像の縦
     * bmWidth  : Int              # 画像の横
     */
    fun create(pixels: IntArray, qrBits: MutableList<Int>, bmWidth:Int, bmHeight: Int): IntArray {
        val bmHW = if(bmWidth < bmHeight) bmWidth else bmHeight
        for (y in 0 until bmHW) {
            for (x in 0 until bmHW) {
                val bitsSize = qrBits.size
                val pixelIndex = x + y * bmWidth
                val qrIndex = x + y * bmHW
                val pixel = pixels[pixelIndex]

                //既存のARGBデータ
                val pixelAlpha: Int = (pixel shr 24) and 0xff
                val pixelRed: Int = (pixel shr 16) and 0xff
                val pixelGreen: Int = (pixel shr 8) and 0xff
                val pixelBlue: Int = pixel and 0xff


                //新しいRGBデータ
                val newRed = if (bitsSize <= qrIndex) pixelRed else changeColor(pixelRed, qrBits[qrIndex])
                val newGreen = if (bitsSize <= qrIndex) pixelGreen else changeColor(pixelGreen, qrBits[qrIndex])
                val newBlue = if (bitsSize <= qrIndex) pixelBlue else changeColor(pixelBlue, qrBits[qrIndex])

                //set
                pixels[pixelIndex] = Color.argb(255, newRed, newGreen, newBlue)
            }
        }
        return pixels
    }

    /*
     * 作成したqrコードステガノグラフィの読み取りに使う関数 LSB方式
     * 受け取った画像をcolor%3を視覚化した画像で取り出す
     * pixels  : IntArray # 読み取る画像のpixel情報
     * bmHeight: Int      # 画像の縦
     * bmWidth : Int      # 画像の横
     */
    fun read(pixels: IntArray, bmWidth:Int, bmHeight: Int): MutableList<Int> {
        for (y in 0 until bmHeight) {
            for (x in 0 until bmWidth) {
                val pixelIndex = x + y * bmWidth
                val pixel = pixels[pixelIndex]

                //pixelのARGBデータ
                val pixelAlpha: Int = (pixel shr 24) and 0xff
                val pixelRed  : Int = (pixel shr 16) and 0xff
                val pixelGreen: Int = (pixel shr 8) and 0xff
                val pixelBlue : Int = pixel and 0xff

                val pixelRGBs = mutableListOf(pixelRed, pixelGreen, pixelBlue)

                for ( (index, color) in pixelRGBs.withIndex()) {
                    val modColor = color % 3
                    pixelRGBs[index] = if (modColor == 1) 255 else if (modColor == 0) 0 else 127
                }

                pixels[x + y * bmWidth] = Color.argb(255, pixelRGBs[0], pixelRGBs[1], pixelRGBs[2])
            }
        }
        val bm = Bitmap.createBitmap(bmWidth, bmHeight, Bitmap.Config.ARGB_8888)
        bm.setPixels(pixels, 0, bmWidth, 0, 0, bmWidth, bmHeight)
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imageByte = stream.toByteArray()
        return byteArrayToIntList(imageByte)
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
        if (colorMod == bit) {
            return color
        }

        //colorが126以上の場合は減算処理で色を変換
        return if (color >= 126) {
            if (colorMod > bit) {
                color - colorMod + bit
            } else {
                color - (3 - bit + colorMod)
            }
            //colorが125以下の場合は加算処理で色を変換
        } else {
            if (colorMod > bit) {
                color + 2 * colorMod + bit
            } else {
                color + bit - colorMod
            }
        }
    }

    /*
     * qrコードをMutableList<Int>の状態で生成する
     * data                : String # QRコードの文字列
     * height              : Int    # QRコードの縦
     * width               : Int    # QRコードの横
     * errorCorrectionLevel: String # QRコードの誤り訂正機能のレベル (L<M<Q<H)
     */
    fun generateQR(data: String, height: Int, width: Int, errorCorrectionLevel: String): MutableList<Int> {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.ERROR_CORRECTION] = errorCorrectionLevel

        val writer = QRCodeWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height, hints)

            val pixels = IntArray(width * height)
            val result = mutableListOf<Int>()

            for (y in 0 until height) {
                for (x in 0 until width) {
                    pixels[y * width + x] = if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE
                    result.add(if (bitMatrix.get(x, y)) 1 else 0)
                }
            }

            return result

        } catch (e: WriterException) {
            e.printStackTrace()
            return mutableListOf(-1)
        }
    }
}