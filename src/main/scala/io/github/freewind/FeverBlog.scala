package io.github.freewind

import io.github.freewind.feverblog.{GenerateSite, NewBlog, AppConfig}

final class FeverBlog extends xsbti.AppMain {

  def run(configuration: xsbti.AppConfiguration): Exit = {
    AppConfig.baseDir = configuration.baseDirectory()

    val args = configuration.arguments().toList
    args.headOption match {
      case Some("new-post") => NewBlog()
      case Some("generate") => GenerateSite()
      case Some("publish") => println("### publish to github")
      case Some(cmd) => println(s"### unknown command: $cmd")
      case _ => println("Usage: fever new-post|generate|publish")
    }

    new Exit(0)
  }

}

class Exit(val code: Int) extends xsbti.Exit
