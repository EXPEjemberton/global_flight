package com.flight.services

import com.flight.model.{City, GeographicLocation}

import scala.collection.mutable.ListBuffer

object CityFinder {

  def cities = io.Source.fromFile(CSVStore.cities)

  def getByName(name: String): Option[City] = {
    for (city <- cities.getLines) {
      if (toCity(city).name == name) Some(city)
    }
    None
  }

  def getAll: List[City] = {
    val cityList: ListBuffer[City] = ListBuffer()
    for (city <- cities.getLines) cityList += city
    cityList.toList
  }

  implicit def toCity(row: String): City = {
    val split = row.split(",")
    City(
      id = split(0).toInt,
      name = split(1),
      population = split(5).toDouble.toLong,
      location = GeographicLocation(latitude = split(3),
      longitude = split(4)))
  }
}
