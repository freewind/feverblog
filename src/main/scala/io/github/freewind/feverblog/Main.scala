package io.github.freewind.feverblog

import java.io.File
import java.text.SimpleDateFormat

import io.github.freewind.feverblog.Utils._
import org.apache.commons.io.FileUtils

import scala.collection.JavaConversions._

case class SiteConfig(title: String, baseUrl: String, googleAnalyticsId: String)

object GenerateSite {

  def apply() {
    Seq("category", "css", "posts", "user_images").foreach { d =>
      FileUtils.deleteDirectory(new File(siteRoot, d))
    }

    val rootCategory = PostFiles.find(postRoot)

    val indexPageContent = IndexPage.generate(rootCategory)
    FileUtils.writeStringToFile(new File(siteRoot, "index.html"), indexPageContent, "UTF-8")

    allPosts(rootCategory).foreach { a =>
      val content = PostPage.generate(a)
      FileUtils.writeStringToFile(new File(siteRoot, s"posts/${a.alias}/index.html"), content, "UTF-8")
    }

    val feed = FeedPage.generate(rootCategory)
    FileUtils.writeStringToFile(new File(siteRoot, "feed.xml"), feed, "UTF-8")

    FileUtils.copyDirectory(new File(AppConfig.baseDir, "assets"), siteRoot)

    allFirstLevelCategories(rootCategory).foreach { category =>
      val content = CategoryPage.generate(category)
      FileUtils.writeStringToFile(new File(siteRoot, s"category/${category.alias}/index.html"), content, "UTF-8")
    }

    FileUtils.copyDirectory(new File(postRoot, "../user_images"), new File(siteRoot, "user_images"))
  }
}

object PostFiles {
  def find(postRoot: File): RootCategory = {
    findAvailableFilesAndDirs(postRoot) match {
      case (files, dirs) =>
        new RootCategory(files.map(toArticle).toList, dirs.map(toCategory).toList)
    }
  }

  def findAvailableFilesAndDirs(dir: File): (List[File], List[File]) = {
    dir.listFiles.toList.filterNot(_.getName.startsWith(".")).partition(_.isFile)
  }

  private def toArticle(file: File): Post = {
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

    new Post(id, title, content, alias, date, layout, tags)
  }

  private def toCategory(dir: File): Category = {
    val NamePattern = """(\d+)-(.*?)($|-@(.*))""".r
    val NamePattern(order, alias, _, name) = dir.getName
    val (articles, subCategories) = findAvailableFilesAndDirs(dir) match {
      case (files, dirs) => (files.map(toArticle), dirs.map(toCategory))
    }
    new Category(order.toInt, alias, if (name == null) alias else name, articles, subCategories)
  }

  implicit class RichString(s: String) {
    def isBlank: Boolean = s.trim.isEmpty
  }

}


object Links {
  def postLink(article: Post) = {
    s"${siteConfig.baseUrl}/posts/${article.alias}"
  }
}












