package io.github.freewind

final class Feverblog extends xsbti.AppMain {
  def run(configuration: xsbti.AppConfiguration): xsbti.MainResult = {
    val baseDir = configuration.baseDirectory()
    println("Hello feverblog!")
    println("### project files:")
    baseDir.listFiles().foreach(println)
    new sbt.Exit(0)
  }
}
