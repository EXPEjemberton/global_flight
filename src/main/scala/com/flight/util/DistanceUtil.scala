package com.flight.util

import com.flight.model.GeographicLocation

object DistanceUtil {

  def calculate(location1: GeographicLocation, location2: GeographicLocation): BigDecimal = {

      implicit def toDouble(x: BigDecimal): Double = x.toDouble

      val radiusOfEarth = 6371
      val latitudinalDifference = Math.toRadians(location2.latitude-location1.latitude)
      val longitudinalDifference = Math.toRadians(location2.longitude-location1.longitude)
      val a = Math.pow(Math.sin(latitudinalDifference / 2), 2) +
          Math.cos(Math.toRadians(location1.latitude)) * Math.cos(Math.toRadians(location2.latitude)) *
          Math.pow(Math.sin(longitudinalDifference / 2), 2)
      val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

      radiusOfEarth * c

  }
}
