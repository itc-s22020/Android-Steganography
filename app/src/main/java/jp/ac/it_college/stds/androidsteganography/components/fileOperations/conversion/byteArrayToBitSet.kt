package jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion

import java.util.BitSet


/*
 * ByteArrayをBitSetに変換する関数
 * byteArray : ByteArray # 変換したいByteArrayを渡す
 */
fun byteArrayToBitSet(byteArray: ByteArray): BitSet {
    val bitSet = BitSet(byteArray.size * 8)
    var bitIndex = 0

    for (byte in byteArray) {
        for (i in 7 downTo 0) {
            val isSet = (byte.toInt() shr i and 1) == 1
            bitSet.set(bitIndex++, isSet)
        }
    }
    return bitSet
}