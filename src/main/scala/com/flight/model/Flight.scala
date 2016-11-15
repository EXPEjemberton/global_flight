package com.flight.model

case class Flight(
  id: Int,
  origin: Airport,
  destination: Airport,
  airline: Airline,
  price: BigDecimal
)