package com.flight.services

import com.flight.model.Flight
import com.flight.util.DistanceUtil

// The semantics of a MultiFlightScorer is that the higher the score, the worse it is.
sealed trait MultiFlightScorer extends Function1[List[Flight], BigDecimal]

object DistanceScore extends MultiFlightScorer {

  override def apply(flights: List[Flight]): BigDecimal = flights.map(f => DistanceUtil.calculate(f.origin.location, f.destination.location)).sum
}

object PriceScore extends MultiFlightScorer {

  override def apply(flights: List[Flight]): BigDecimal = flights.map(_.price).sum
}

// A hacky attempt to fuse distance and price into a single value.  It does this by using distance as a proxy for time and setting a $30/hour penalty.
object CompositeScore extends MultiFlightScorer {

  override def apply(flights: List[Flight]): BigDecimal = {
    val approximateRate = 400.0
    val costPerhour = 30.0

    val price = PriceScore(flights)
    val time = DistanceScore(flights) / approximateRate // time = distance / rate

    val timePenalty = time * costPerhour

    price + timePenalty
  }

}



