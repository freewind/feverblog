package io.github.freewind.feverblog

import java.io.{ByteArrayOutputStream, PrintWriter}
import java.util.{Date, List => JList}

import com.github.mustachejava.DefaultMustacheFactory
import io.github.freewind.feverblog.Utils._

import scala.collection.JavaConversions._

object FeedPage {

  case class FeedItem(title: String, link: String, pubDate: String, creator: String, category: String, content: String, guid: String)

  case class Data(siteConfig: SiteConfig, items: JList[FeedItem], lastBuildDate: String)

  def generate(rootCategory: RootCategory): String = {
    val items = allPostsWithoutDraft(rootCategory).sortWith(timeDesc).map(a =>
      FeedItem(a.title, a.link, a.dateAsPubDate, "Freewind", a.category.map(_.name).getOrElse("未分类"), a.contentAsHtml, a.id)
    )
    val template = new Template[Data]("feed")
    template.render(Data(siteConfig, items, lastBuildDate))
  }

  def lastBuildDate = formatAsRssDate(new Date)
}


object CategoryPage {

  case class Data(siteConfig: SiteConfig, category: Category, posts: JList[Post])

  def generate(category: Category): String = {
    val template = new Template[Data]("category")
    val posts = postsOfCategory(category).sortWith(timeAsc)
    template.render(Data(siteConfig, category, posts))
  }
}


object IndexPage {

  def generate(rootCategory: RootCategory): String = {
    case class Data(siteConfig: SiteConfig, categories: JList[Category], posts: JList[Post])
    val posts = allPostsWithoutDraft(rootCategory).sortWith(timeDesc)
    val categories = allFirstLevelCategories(rootCategory)
    val template = new Template[Data]("index")
    template.render(Data(siteConfig, categories, posts))
  }
}


object PostPage {

  def generate(post: Post): String = {
    case class Data(siteConfig: SiteConfig, post: Post)
    val template = new Template[Data]("post")
    template.render(Data(siteConfig, post))
  }

}
