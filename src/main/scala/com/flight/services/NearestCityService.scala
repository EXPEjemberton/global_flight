package com.flight.services

import com.flight.model.{City, GeographicLocation}
import com.flight.util.DistanceUtil

object NearestCityService {
  def find(userLocation: GeographicLocation): City = {

    var closestCity: City = null
    var closestDistance: BigDecimal = 100000 // Arbitrary large number.
    val airportCities = io.Source.fromFile("data/populated_airport_cities.csv")

    for (city <- airportCities.getLines) {
      val cityArr = city.split(",")
      val (id, name, latitude, longitude, population) = (cityArr(0), cityArr(1), BigDecimal(cityArr(3)), BigDecimal(cityArr(4)), cityArr(5))
      val cityLocation = GeographicLocation(latitude, longitude)
      val distance = DistanceUtil.calculate(userLocation, cityLocation)

      if (distance < closestDistance) {
        closestDistance = distance
        closestCity = City(id.toInt, name, population.toDouble.toLong, cityLocation)
      }
    }

    closestCity
  }
}