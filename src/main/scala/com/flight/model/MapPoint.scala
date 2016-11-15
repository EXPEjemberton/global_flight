package com.flight.model

case class MapPoint(
  airport: Airport,
  name: Option[String],
  price: Option[BigDecimal]
)