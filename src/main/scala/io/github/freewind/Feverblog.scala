package io.github.freewind

import io.github.freewind.feverblog.{NewBlog, AppConfig}

final class Feverblog extends xsbti.AppMain {

  def run(configuration: xsbti.AppConfiguration): Exit = {
    AppConfig.baseDir = configuration.baseDirectory()

    val args = configuration.arguments().toList
    args.headOption match {
      case Some("new-post") => newPost()
      case Some("generate") => generate()
      case Some("publish") => println("### publish to github")
      case Some(cmd) => println(s"### unknown command: $cmd")
      case _ => println("Usage: fever new-post|generate|publish")
    }

    new Exit(0)
  }

  def generate() = {

  }

  def newPost() {
    NewBlog()
  }
}

class Exit(val code: Int) extends xsbti.Exit
