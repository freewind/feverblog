package io.github.freewind.feverblog

import java.io.File
import java.text.SimpleDateFormat

import org.apache.commons.io.FileUtils

import scala.collection.JavaConversions._

object PostFiles {
  def find(postRoot: File): RootCategory = {
    findAvailableFilesAndDirs(postRoot) match {
      case (files, dirs) =>
        new RootCategory(files.map(toPost).toList, dirs.map(toCategory).toList)
    }
  }

  def findAvailableFilesAndDirs(dir: File): (List[File], List[File]) = {
    dir.listFiles.toList.filterNot(_.getName.startsWith(".")).partition(_.isFile)
  }

  private def toPost(file: File): Post = {
    println("##### file: " + file)
    val lines = FileUtils.readLines(file, "UTF-8").toList

    val separateLine: String = "[-]{3,}"
    val count = lines.count(_.matches(separateLine))
    if (count < 2) {
      throw new RuntimeException("Should contain at least 2 ---, but get: " + count)
    }

    val (metaLines, contentLines) = lines.dropWhile(line => line.isBlank || line.matches(separateLine))
      .span(!_.matches(separateLine))

    val metaMap = metaLines.filterNot(_.isBlank).foldLeft(Map.empty[String, String]) {
      case (map, line) => map + {
        val KeyValue = """(.*?):(.*)""".r
        val KeyValue(key, value) = line
        key.trim -> value.trim
      }
    }

    val title = metaMap.get("title").get
    val id = metaMap.get("id").get
    val layout = metaMap.getOrElse("layout", "post")
    val date = metaMap.get("date").map(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse).get
    val alias: String = metaMap.get("alias").get
    val tags = metaMap.get("tags").map(_.split("\\s+").toList).getOrElse(Nil)
    val content = contentLines.mkString("\n")

    new Post(id, title, content, alias, date, layout, tags, file)
  }

  private def toCategory(dir: File): Category = {
    val NamePattern = """(\d+)-(.*?)($|-@(.*))""".r
    val NamePattern(order, alias, _, name) = dir.getName
    val (posts, subCategories) = findAvailableFilesAndDirs(dir) match {
      case (files, dirs) => (files.map(toPost), dirs.map(toCategory))
    }
    new Category(order.toInt, alias, if (name == null) alias else name, posts, subCategories)
  }

  implicit class RichString(s: String) {
    def isBlank: Boolean = s.trim.isEmpty
  }

}
