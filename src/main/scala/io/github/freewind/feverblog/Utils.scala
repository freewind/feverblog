package io.github.freewind.feverblog

import java.text.SimpleDateFormat
import java.util.{Locale, Date}

object Utils {

  def formatAsRssDate(date: Date): String = {
    new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US).format(date)
  }

  def allPosts(rootCategory: RootCategory): List[Post] = {
    rootCategory.subCategories.foldLeft(rootCategory.posts) {
      case (as, c) => as ::: postsOfCategory(c)
    }.filterNot(_.alias == "???")
  }

  def timeDesc(a: Post, b: Post) = a.date.getTime > b.date.getTime

  def timeAsc(a: Post, b: Post) = a.date.getTime < b.date.getTime

  def allFirstLevelCategories(rootCategory: RootCategory): List[Category] = {
    rootCategory.subCategories.sortWith((a, b) => a.order < b.order)
  }

  def postsOfCategory(category: Category): List[Post] = {
    category.subCategories.foldLeft(category.posts) {
      case (posts, sub) => posts ::: postsOfCategory(sub)
    }

  }
}
