package io.github.freewind.feverblog

import java.util.{Date, List => JList}

import io.github.freewind.feverblog.Utils._

object FeedPage {

  case class FeedItem(title: String, link: String, pubDate: String, creator: String, category: String, content: String, guid: String)

  case class Data(siteConfig: SiteConfig, items: JList[FeedItem], lastBuildDate: String)

  def generate(rootCategory: RootCategory): String = {
    val items = allPostsWithoutDraft(rootCategory).sortWith(timeDesc).map(a =>
      FeedItem(a.title, a.link(), a.dateAsPubDate, "Freewind", a.category.map(_.name).getOrElse("未分类"), a.contentAsHtml, a.id)
    )
    html.feed.render(siteConfig, items, lastBuildDate).toString()
  }

  def lastBuildDate = formatAsRssDate(new Date)
}


object CategoryPage {

  case class Data(siteConfig: SiteConfig, category: Category, posts: JList[Post])

  def generate(category: Category): String = {
    val posts = postsOfCategory(category).sortWith(timeAsc)
    html.category.render(siteConfig, category, posts).toString()
  }
}


object IndexPage {

  def generate(rootCategory: RootCategory): String = {
    case class Data(siteConfig: SiteConfig, categories: JList[Category], posts: JList[Post])
    val posts = allPostsWithoutDraft(rootCategory).sortWith(timeDesc)
    val categories = allFirstLevelCategories(rootCategory)
    html.index.render(siteConfig, categories, posts).toString()
  }
}


object PostPage {

  def generate(post: Post): String = {
    post.layout match {
      case "post" => html.post.render(siteConfig, post).toString()
      case "slide" => html.slide.render(siteConfig, post).toString()
      case unknown => throw new RuntimeException(s"Unknown layout: $unknown")
    }
  }

}
