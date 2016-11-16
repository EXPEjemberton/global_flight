package com.flight.services

import com.flight.model.{City, MapPoint}

import scala.collection.mutable.ListBuffer

object MapPointRetriever {

  val cities = CityFinder.getAll

  def retrieve(northEdge: BigDecimal, southEdge: BigDecimal, westEdge: BigDecimal, eastEdge: BigDecimal): List[MapPoint] = {

    def fitsBounds(city: City) =
      city.location.latitude > southEdge &&
        city.location.latitude < northEdge &&
        city.location.longitude > westEdge &&
        city.location.longitude < eastEdge

    val cityList: ListBuffer[City] = ListBuffer()
    for (city <- cities) {
      if (fitsBounds(city)) cityList += city
    }
    val sortedCities = cityList.sortWith(_.population > _.population).toList

    // Prepare list of MapPoints to return.
    val mapPoints: ListBuffer[MapPoint] = ListBuffer()

    // Add first 10 cities, with price and name displayed.
    mapPoints ++= sortedCities.take(10).map(MapPoint.withPrice)

    // Add next 30 cities, with name and without price.
    mapPoints ++= sortedCities.slice(10, 40).map(MapPoint.withName)

    // Add next 60 cities, without name or price.
    mapPoints ++= sortedCities.slice(40, 100).map(MapPoint.withoutName)

    mapPoints.toList
  }
}
