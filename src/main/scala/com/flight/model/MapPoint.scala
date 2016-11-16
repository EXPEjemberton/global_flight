package com.flight.model

case class MapPoint(
  city: City,
  nameFlag: Boolean,
  price: Option[BigDecimal]
)

object MapPoint {
  def withPrice(city: City) =
    // Needs to actually retrieve the price.
    MapPoint(city, true, Some(1.0))

  def withName(city: City) =
    MapPoint(city, true, None)

  def withoutName(city: City) =
    MapPoint(city, false, None)

}