package io.github.freewind.feverblog

import java.io.{PrintWriter, ByteArrayOutputStream}

import com.github.mustachejava.DefaultMustacheFactory

class Template[T](name: String) {
  def render(data: T): String = {
    val mf = new DefaultMustacheFactory()
    val mustache = mf.compile(s"${AppConfig.baseDir.getAbsolutePath}/templates/$name.mustache")

    val out = new ByteArrayOutputStream
    mustache.execute(new PrintWriter(out), data).flush()
    out.toString("UTF-8")
  }
}
