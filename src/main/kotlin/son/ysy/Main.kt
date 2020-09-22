package son.ysy

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.joda.time.DateTime
import java.io.File

fun main() {
    val gitDir = File(".git")
    if (!gitDir.exists()) {
        exit("不是git仓库!")
        return
    }
    val fileRepository = FileRepository(File(gitDir.absolutePath))
    val git = Git(fileRepository)
    if (git.status().call().isClean) {
        exit("文件无改动")
        return
    }
    git.add().addFilepattern(".").call()
    git.commit().setMessage(DateTime.now().toString("yyyy-MM-dd HH:mm:ss:sss")).call()
    git.push().setPushAll().call()
    exit("任意键结束!")
}

private fun exit(message: String) {
    println(message)
    System.`in`.read()
}