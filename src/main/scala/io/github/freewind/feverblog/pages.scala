package io.github.freewind.feverblog

import java.util.Date

import io.github.freewind.feverblog.Utils._

object FeedPage {

  case class FeedItem(title: String, link: String, pubDate: String, creator: String, category: String, content: String, guid: String)

  def generate(rootCategory: RootCategory): String = {
    val items = allPostsWithoutDraft(rootCategory).sortWith(timeDesc).map(p =>
      FeedItem(p.title, p.link(), p.dateAsPubDate, "Freewind", p.category.map(_.name).getOrElse("未分类"), p.contentAsHtml, p.id)
    )
    xml.feed.render(siteConfig, items, lastBuildDate).toString().trim
  }

  def lastBuildDate = formatAsRssDate(new Date)
}


object CategoryPage {

  def generate(currentCategory: Category, rootCategory: RootCategory): String = {
    val posts = postsOfCategory(currentCategory).sortWith(timeAsc)
    val bigCategories = allFirstLevelCategories(rootCategory)
    html.category.render(siteConfig, currentCategory, posts, bigCategories).toString()
  }
}


object IndexPage {

  def generate(rootCategory: RootCategory): String = {
    val posts = allPostsWithoutDraft(rootCategory).sortWith(timeDesc)
    val categories = allFirstLevelCategories(rootCategory)
    html.index.render(siteConfig, categories, posts).toString()
  }
}


object PostPage {

  def generate(post: Post, rootCategory: RootCategory): String = {
    post.layout match {
      case "post" =>
        val bigCategories = allFirstLevelCategories(rootCategory)
        html.post.render(siteConfig, post, bigCategories).toString()
      case "slide" => html.slide.render(siteConfig, post).toString()
      case unknown => throw new RuntimeException(s"Unknown layout: $unknown")
    }
  }

}
