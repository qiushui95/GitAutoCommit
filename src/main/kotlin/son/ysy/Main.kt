package son.ysy

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import org.joda.time.DateTime

fun main() {
    val fileRepository = FileRepository(".git")
    val git = Git(fileRepository)
    git.add().addFilepattern(".").call()
    git.commit().setMessage(DateTime.now().toString("yyyy-MM-dd HH:mm:ss:sss")).call()
    git.push().setPushAll().call()
    println("任意键结束!")
    System.`in`.read()
}