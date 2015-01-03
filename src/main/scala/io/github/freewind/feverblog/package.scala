package io.github.freewind

import java.io.File

package object feverblog {

  def postRoot = new File(s"${AppConfig.baseDir.getAbsolutePath}/_posts")

  def siteRoot = new File("/Users/twer/workspace/freewind.github.io")

  def siteConfig = new SiteConfig("Freewind @ Thoughtworks", "http://freewind.in", "UA-54316895-1")

}
