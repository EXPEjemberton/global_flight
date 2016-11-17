package com.flight.util

import com.flight.model.City
import org.neo4j.graphdb.{GraphDatabaseService, Label}

object NodeCreator {
  def create(city: City)(implicit graph: GraphDatabaseService): Unit = {
    val node = graph.createNode(Labels.City)
    node.setProperty("id", city.id)
    node.setProperty("name", city.name)
    node.setProperty("population", city.population)
    node.setProperty("latitude", city.location.latitude.toDouble)
    node.setProperty("longitude", city.location.longitude.toDouble)
  }
}

object Labels {
  val City = new Label {
    val name = "City"
  }
}