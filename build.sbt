name := "FeverBlog"

version := "0.1.1"

organization := "io.github.freewind"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "ibiblio" at "http://mirrors.ibiblio.org/pub/mirrors/maven2",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "org.scala-sbt" % "launcher-interface" % sbtVersion.value,
  "commons-io" % "commons-io" % "2.4",
  "org.commonjava.googlecode.markdown4j" % "markdown4j" % "2.2-cj-1.0",
  "org.scalatra.scalate" %% "scalate-web" % "1.7.0",
  "com.github.spullara.mustache.java" %	"compiler" %	"0.8.16",
  "org.jsoup" % "jsoup" % "1.7.3",
  "org.specs2" %% "specs2" % "2.4" % "test"
)

mainClass in run := Some("io.github.freewind.Feverblog")
