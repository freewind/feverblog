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

lazy val install = taskKey[Unit]("Install this tool as 'fever' to your ~/bin")

install <<= assembly map { (file: File) =>
  val bin = new File(Path.userHome.absolutePath, "bin")
  val jar = new File(bin, "fever-blog.jar")
  val shell = new File(bin, "fever")
  IO.createDirectory(bin)
  IO.copyFile(file, jar)
  println("copied to " + jar)
  IO.write(shell, s"java -jar $jar $$(pwd) $$@")
  s"chmod +x $shell".!
  println("created executable file: " + shell)
}
