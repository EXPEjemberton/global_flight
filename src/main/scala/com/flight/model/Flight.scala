package com.flight.model

import java.time.LocalDateTime

case class Flight(
  origin: Airport,
  destination: Airport,
  airline: Airline,
  price: BigDecimal,
  takeoff: LocalDateTime
)

case class FlightWithFinalPrice(
  flight: Flight,
  roundTrip: Boolean,
  finalPrice: BigDecimal
)