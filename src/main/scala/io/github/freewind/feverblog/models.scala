package io.github.freewind.feverblog


import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import io.github.freewind.feverblog.Utils._
import org.markdown4j.Markdown4jProcessor

case class Category(order: Int, alias: String, name: String, posts: List[Post], subCategories: List[Category])

case class Post(id: String, title: String, content: String, alias: String, date: Date, layout: String, tags: List[String], file: File, category: Option[Category] = None) {
  def contentAsHtml: String = {
    new Markdown4jProcessor().process(content)
  }

  def dateAsPubDate: String = formatAsRssDate(date)

  def dateOnly: String = new SimpleDateFormat("yyyy-MM-dd").format(date)

  def link: String = Links.postLink(this)
}

case class RootCategory(posts: List[Post], subCategories: List[Category])
