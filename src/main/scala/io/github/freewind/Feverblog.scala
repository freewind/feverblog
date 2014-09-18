package io.github.freewind

import io.github.freewind.feverblog.{GenerateSite, NewBlog, AppConfig}

final class FeverBlog extends xsbti.AppMain {

  def run(configuration: xsbti.AppConfiguration): Exit = {
    AppConfig.baseDir = configuration.baseDirectory()

    val args = configuration.arguments().toList
    args match {
      case Nil => println("Usage: fever new-post|generate|publish")
      case "new" :: "post" :: _ => NewBlog()
      case "generate" :: _ => GenerateSite()
      case "publish" :: _ => println("### publish to github")
      case cmds => println( s"""### unknown command: ${cmds.mkString(" ")}""")
    }

    new Exit(0)
  }

}

class Exit(val code: Int) extends xsbti.Exit
