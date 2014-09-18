package io.github.freewind.feverblog

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import io.github.freewind.feverblog.Utils._
import org.apache.commons.io.FileUtils

object NewBlog {

  def apply() {
    val today = new SimpleDateFormat("yyyy-MM-dd").format(new Date)
    val now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)

    val rootCategory = PostFiles.find(postRoot)

    val newPostId = GenerateNewPostId(allPosts(rootCategory))

    val newPostName = s"$today-$newPostId-???.md"

    val template =
      s"""
      |---
      |layout: post
      |id: $newPostId
      |alias: ???
      |tags: ???
      |date: $now
      |title: ???
      |---
    """.stripMargin.trim

    val targetFile: File = new File(postRoot, newPostName)

    FileUtils.writeStringToFile(targetFile, template, "UTF-8")

    println("targetFile: " + targetFile)
  }
}
