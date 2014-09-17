name := "FeverBlog"

version := "0.1.0"

organization := "io.github.freewind"

scalaVersion := "2.10.2"

sbtPlugin := true

resolvers ++= Seq(
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "local_nexus" at "http://localhost:8888"
)

libraryDependencies ++= Seq(
  "org.scala-sbt" % "launcher-interface" % sbtVersion.value,
  "org.specs2" %% "specs2" % "2.4" % "test"
)

mainClass in run := Some("io.github.freewind.Feverblog")
