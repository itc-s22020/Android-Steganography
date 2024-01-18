package jp.ac.it_college.stds.androidsteganography.components.steganography


/*
 * ステガノグラフィの作成に使う関数 LSB方式
 * (color%3)==bit になるようにcolorを変換して返す
 * color : Int # 変換する色
 * bit   : Int # 埋め込むbit
 */
fun simpleLSB(color: Int ,bit: Int): Int {
    val colorMod = color % 3
    //既にcolorModとbitが一致している場合はそのまま返す
    if (colorMod == bit) {return color}

    //colorが126以上の場合は減算処理で色を変換
    return if (color >= 126) {
        if (colorMod > bit) { color - colorMod + bit }
        else { color - (3 - bit) }
    //colorが125以下の場合は加算処理で色を変換
    } else {
        if (colorMod > bit) { color + 2 * colorMod + bit }
        else { color + bit - colorMod }
    }
}
