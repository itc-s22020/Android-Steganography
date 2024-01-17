package jp.ac.it_college.stds.androidsteganography.components.fileOperations

/*
 * byteArrayをMutableList<Int>に変換する関数
 * byteArray : ByteArray? # 変換したいByteArrayを渡す
 */
fun byteArrayToIntList(
    byteArray: ByteArray?,
): MutableList<Int> {
    val bits = mutableListOf<Int>()
    if (byteArray != null) {
        for (byte in byteArray) {
            val target = byte.toInt()
            bits += target and 0x01
        }
    }
    return bits
}