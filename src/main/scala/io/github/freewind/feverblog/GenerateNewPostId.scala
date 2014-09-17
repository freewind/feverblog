package io.github.freewind.feverblog

object GenerateNewPostId {

  def apply(posts: List[Post]): Int = {
    val x = posts.map(_.id.toInt)
    if (x.toSet.size != x.size) {

      val dupIds = x.filter(i => x.count(i == _) > 1)
      throw new RuntimeException(s"there are duplicated ids： $dupIds, max: " + x.max)
    }
    x.max + 1
  }

}
