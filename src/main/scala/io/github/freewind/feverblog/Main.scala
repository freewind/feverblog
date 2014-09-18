package io.github.freewind.feverblog

import java.io.File

import io.github.freewind.feverblog.Utils._
import org.apache.commons.io.FileUtils

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

object Links {
  def postLink(post: Post) = {
    s"${siteConfig.baseUrl}/posts/${post.alias}"
  }
}












