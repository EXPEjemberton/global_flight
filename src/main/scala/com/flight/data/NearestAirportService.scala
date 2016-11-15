package com.flight.data

import com.flight.model.{City, GeographicLocation}
import com.flight.util.DistanceUtil

class NearestAirportService(latitude: BigDecimal, longitude: BigDecimal) {
  val userLocation = GeographicLocation(latitude = latitude, longitude = longitude)
  def findNearestAirportCity: City = {
    NearestAirportService.findNearestAirportCity(userLocation)
  }
}

object NearestAirportService {
  def findNearestAirportCity(userLocation: GeographicLocation): City = {

    var closestCity: City = null
    var closestDistance: BigDecimal = 100000
    val airportCities = io.Source.fromFile("/Users/jemberton/global_flight/data/populated_airport_cities.csv")

    for (city <- airportCities.getLines) {
      val cityArr = city.split(",")
      val (id, name, latitude, longitude, population) = (cityArr(0), cityArr(1), cityArr(3), cityArr(4), cityArr(5))
      val cityLocation = GeographicLocation(latitude = BigDecimal(latitude), longitude = BigDecimal(longitude))
      val distance = DistanceUtil.calculate(userLocation, cityLocation)
      if (distance < closestDistance) {
        closestDistance = distance
        closestCity = City(id.toInt, name, population.toDouble.toLong)
      }
    }

    closestCity
  }
}