package com.flight.services

import java.time.{LocalDate, LocalDateTime}

import com.flight.model.{Airline, City, Flight}
import com.flight.util.{DistanceUtil, RandomUtil}

import scala.util.Random

object MockFlightGenerator {

  def generate(originCity: City, destCity: City, date: LocalDate): List[Flight] = {
    val maxFlights = Math.max(((originCity.population + destCity.population) / 200000L).toDouble.ceil.toInt, 20)
    val num = RandomUtil.pick(Range(1,maxFlights).toList)
    generate(originCity, destCity, date, num)
  }

  def generate(originCity: City, destCity: City, date: LocalDate, num: Int): List[Flight] = {

    val random = new Random
    val distance = DistanceUtil.calculate(originCity.location, destCity.location)
    val avgAirlineRating = avg(Airline.supported.map(_.rating))

    val emptyList: List[Flight] = List()

    (0 until num).map { x =>
      val time = LocalDateTime.of(date.getYear, date.getMonthValue, date.getDayOfMonth, random.nextInt(24), random.nextInt(60))
      val airline = RandomUtil.pick(Airline.supported)
      val originAirport = RandomUtil.pick(AirportFinder.findByCity(originCity))
      val destAirport = RandomUtil.pick(AirportFinder.findByCity(destCity))

      val randCoefficient = (random.nextGaussian / 10 + 1) * (1 + (airline.rating - avgAirlineRating) / avgAirlineRating)
      val estPrice = distance / (originCity.population + destCity.population) * 1000000L
      val adjustedPrice = estPrice * randCoefficient

      Flight(
        origin = originAirport,
        destination = destAirport,
        airline = airline,
        price = adjustedPrice,
        takeoff = time
        )
    }.foldRight(emptyList)(_ :: _)

  }

  private def avg(list: List[BigDecimal]): BigDecimal = list.sum / list.length

}
