package com.flight.model

case class GeographicLocation(
  latitude: BigDecimal,
  longitude: BigDecimal
)

object GeographicLocation {
  def apply(latitude: String, longitude: String): GeographicLocation =
    GeographicLocation(BigDecimal(latitude), BigDecimal(longitude))
}