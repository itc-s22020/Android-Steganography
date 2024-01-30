package jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion

import java.util.BitSet

/*
 * BitSetをMutableList<Int>に変換する関数
 * bitSet : BitSet # 変換したいBitSetを渡す
 */
fun bitSetToList(bitSet: BitSet): MutableList<Int> {
    val list = mutableListOf<Int>()
    for (i in 0 until bitSet.size()) {
        list.add(if (bitSet[i]) 1 else 0)
    }
    return list
}