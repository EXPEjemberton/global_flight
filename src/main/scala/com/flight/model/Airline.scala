package com.flight.model

case class Airline(
  id: Int,
  name: String,
  rating: BigDecimal
)

object Airline {
  val supported = List(
    Airline(1, "American Airlines", 2.5),
    Airline(2, "United Airlines", 2.9),
    Airline(3, "Alaska Airlines", 4.5),
    Airline(4, "Jetblue Airways", 4.3),
    Airline(5, "Spirit Airlines", 1.6),
    Airline(6, "Frontier Airlines", 1.9)
  )
}