package io.github.freewind

import java.io.File

import io.github.freewind.feverblog.{AppConfig, GenerateSite, NewBlog}

object FeverBlog {

  def main(args: Array[String]): Unit = {
    args.toList match {
      case Nil | _ :: Nil => println("Usage: fever new-post|generate")
      case baseDir :: real =>
        AppConfig.baseDir = new File(baseDir)
        real match {
          case "new" :: "post" :: Nil => NewBlog()
          case "generate" :: Nil => GenerateSite()
          case cmds => println( s"""unknown command: ${cmds.mkString(" ")}""")
        }
    }
  }
}

