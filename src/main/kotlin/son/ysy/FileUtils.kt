package son.ysy

import java.io.*

object FileUtils {

    private fun copyFile(srcFile: File, dstParentDir: File) {
        val dstFile = File(dstParentDir, srcFile.name)
        if (!dstFile.exists()) {
            dstFile.createNewFile()
        }
        BufferedInputStream(FileInputStream(srcFile)).use { srcInput ->
            BufferedOutputStream(FileOutputStream(dstFile)).use { dstOut ->
                val buffer = ByteArray(1024 * 4)
                var length = srcInput.read(buffer)
                while (length != -1) {
                    dstOut.write(buffer, 0, length)
                    length = srcInput.read(buffer)
                }
            }
        }
    }

    private fun copyDir(srcDir: File, dstParentDir: File) {
        val childName = srcDir.name
        val dstDir = File(dstParentDir, childName)
        dstDir.mkdirs()
        srcDir.listFiles()
            ?.forEach {
                if (it.isDirectory) {
                    copyDir(it, dstDir)
                } else if (it.isFile) {
                    copyFile(it, dstDir)
                }
            }
    }

    fun copyAll(
        srcDir: File,
        dstParentDir: File,
        ignoreFileName: Array<String> = emptyArray(),
        ignoreDirName: Array<String> = emptyArray()
    ) {
        if (!dstParentDir.exists()) {
            dstParentDir.mkdirs()
        }
        srcDir.listFiles()
            ?.forEach {
                if (it.isDirectory && it.name !in ignoreDirName) {
                    copyDir(it, dstParentDir)
                } else if (it.isFile && it.name !in ignoreFileName) {
                    copyFile(it, dstParentDir)
                }
            }
    }
}