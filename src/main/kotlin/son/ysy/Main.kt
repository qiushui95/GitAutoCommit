package son.ysy

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.eclipse.jgit.transport.SshSessionFactory
import org.eclipse.jgit.transport.SshTransport
import org.joda.time.DateTime
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

fun main() {
    val gitDir = File(File(File(".").absolutePath).parentFile.parentFile.parentFile, ".git")
    if (!gitDir.exists()) {
        println("不是git仓库!")
        return
    }
    val fileRepository = FileRepository(gitDir.absolutePath)
    val git = Git(fileRepository)

    backup(gitDir)

    when (readInput("pull-->0\npush-->1")) {
        '0'.toInt() -> pull()
        '1'.toInt() -> push(git)
    }
}

private fun readInput(message: String): Int {
    println(message)
    return System.`in`.read()
}

private fun backup(gitDir: File) {
    val ignoreFile = File(gitDir.parentFile, ".gitignore")
    if (!ignoreFile.exists()) {
        ignoreFile.createNewFile()
    }
    val backupDirName = "backup"
    val backupDirIgnore = "$backupDirName/"
    val list = ignoreFile.readLines()
    if (backupDirIgnore !in list) {
        ignoreFile.appendText("\n$backupDirIgnore")
    }
    val backupDir = File(
        gitDir.parentFile,
        "$backupDirName${File.separator}${DateTime.now().toString("yyyy_MM_dd_HH_mm_ss_sss")}"
    )
    FileUtils.copyAll(gitDir.parentFile, backupDir, ignoreDirName = arrayOf(".git", "CrossPromotions", backupDirName))
    println("备份完成")
}

private fun pull() {
    execCmd("git pull")
}

private fun push(git: Git) {
    if (git.status().call().isClean) {
        println("文件无改动")
    } else {
        git.add().addFilepattern(".").call()
        git.commit().setMessage(DateTime.now().toString("yyyy-MM-dd HH:mm:ss:sss")).call()
        println("文件已提交")
    }

    execCmd("git push")

    println("文件已上传")
}

private fun execCmd(cmd: String) {
    val process = Runtime.getRuntime().exec(cmd)
    BufferedReader(InputStreamReader(process.inputStream)).useLines {
        it.forEach(::println)
    }
}