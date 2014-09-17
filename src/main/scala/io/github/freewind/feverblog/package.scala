package io.github.freewind

import java.io.File

package object feverblog {

  def postRoot = new File(s"${AppConfig.baseDir.getAbsolutePath}/_posts")

  def siteRoot = new File("/Users/freewind/workspace/freewind.github.io")

  def siteConfig = new SiteConfig("Freewind @ Thoughtworks", "http://freewind.github.io", "UA-54316895-1")

}
