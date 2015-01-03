name := "FeverBlog"

version := "0.2.3"

organization := "io.github.freewind"

scalaVersion := "2.10.2"

ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

resolvers ++= Seq(
  "ibiblio" at "http://mirrors.ibiblio.org/pub/mirrors/maven2",
  "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

libraryDependencies ++= Seq(
  "org.scala-sbt" % "launcher-interface" % sbtVersion.value,
  "commons-io" % "commons-io" % "2.4",
  "com.typesafe.play" %% "twirl-api" % "1.0.4",
  "org.commonjava.googlecode.markdown4j" % "markdown4j" % "2.2-cj-1.0",
  "org.specs2" %% "specs2" % "2.4" % "test"
)

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

TwirlKeys.templateImports += "io.github.freewind.feverblog._"

mainClass in run := Some("io.github.freewind.Feverblog")
