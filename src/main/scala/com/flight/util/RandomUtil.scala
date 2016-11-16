package com.flight.util

import scala.util.Random

object RandomUtil {
  def pick[T](list: List[T]): T = Random.shuffle(list).head
}
