package jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion

import java.util.BitSet

/*
 * BitSetをByteArrayに変換する関数
 * bitSet : BitSet # 変換したいBitSetを渡す
 */
fun bitSetToByteArray(bitSet: BitSet): ByteArray {
    val byteArraySize = (bitSet.size() + 7) / 8
    val byteArray = ByteArray(byteArraySize)
    var byteIndex = 0
    var currentByte = 0

    for (bitIndex in 0 until bitSet.size()) {
        if (bitSet[bitIndex]) {
            val bitOffset = 7 - (bitIndex % 8)
            currentByte = currentByte or (1 shl bitOffset)
        }

        if (bitIndex % 8 == 7 || bitIndex == bitSet.size() - 1) {
            byteArray[byteIndex++] = currentByte.toByte()
            currentByte = 0
        }
    }
    return byteArray
}