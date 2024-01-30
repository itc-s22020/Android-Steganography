package jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion

import java.util.BitSet

/*
 * MutableList<Int>をBitSetに変換する関数
 * list : MutableList<Int> # 変換したいMutableList<Int>を渡す
 */
fun listToBitSet(list: MutableList<Int>): BitSet {
    val bitSet = BitSet(list.size)
    list.forEachIndexed { index, value ->
        bitSet.set(index, value != 0)
    }
    return bitSet
}