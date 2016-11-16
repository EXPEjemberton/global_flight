package com.flight.services

import com.flight.model.{Airport, City, GeographicLocation}

import scala.collection.mutable.ListBuffer

object AirportFinder {
  def findByCity(city: City): List[Airport] = {
    val list: ListBuffer[Airport] = ListBuffer()

    val airports = io.Source.fromFile("/Users/jemberton/global_flight/data/us_airports.csv")
    for (airport <- airports.getLines) {
      val airportSplit = airport.split(",")
      if (city.name == airportSplit(2)) {
        list += Airport(id = airportSplit(0).toInt, code = airportSplit(4), city = CityFinder.getByName(airportSplit(2)).orNull, location = GeographicLocation(latitude = airportSplit(5), longitude = airportSplit(6)))
      }
    }
    list.toList
  }
}
