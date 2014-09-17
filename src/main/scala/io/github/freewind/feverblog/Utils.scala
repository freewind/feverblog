package io.github.freewind.feverblog

import java.text.SimpleDateFormat
import java.util.{Locale, Date}

object Utils {

  def formatAsRssDate(date: Date): String = {
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).format(date)
  }

  def allPosts(rootCategory: RootCategory): List[Post] = {
    rootCategory.subCategories.foldLeft(rootCategory.articles) {
      case (as, c) => as ::: articlesOfCategory(c)
    }.filterNot(_.alias == "???")
  }

  def timeDesc(a: Post, b: Post) = a.date.getTime > b.date.getTime

  def timeAsc(a: Post, b: Post) = a.date.getTime < b.date.getTime

  def allFirstLevelCategories(rootCategory: RootCategory): List[Category] = {
    rootCategory.subCategories.sortWith((a, b) => a.order < b.order)
  }

  def articlesOfCategory(category: Category): List[Post] = {
    category.subCategories.foldLeft(category.articles) {
      case (articles, sub) => articles ::: articlesOfCategory(sub)
    }

  }
}
