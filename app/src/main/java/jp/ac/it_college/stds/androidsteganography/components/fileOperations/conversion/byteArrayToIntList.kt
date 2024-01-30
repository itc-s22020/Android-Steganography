package jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion

/*
 * byteArrayをBitSetを経由してMutableList<Int>に変換する関数
 * byteArray : ByteArray? # 変換したいByteArrayを渡す
 */
fun byteArrayToIntList(
    zipFile: ByteArray?,
): MutableList<Int> {
    if (zipFile != null) {
        return bitSetToList(byteArrayToBitSet(zipFile))
    }
    return mutableListOf(-1)
}