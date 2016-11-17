package com.flight.services

import com.flight.model.{City, Flight, GeographicLocation}
import com.flight.util.{Labels, NodeCreator}
import org.neo4j.graphdb.RelationshipType
import org.neo4j.graphdb.factory.GraphDatabaseFactory

object NodeConnector {

  def neo4jStoreDir = "/Users/jemberton/Documents/Neo4j"
  implicit val graph = new GraphDatabaseFactory().newEmbeddedDatabase(neo4jStoreDir)

  val KNOWS = new RelationshipType {
    override val name = "KNOWS"
  }

  val cities = io.Source.fromFile(CSVStore.cities)

  // Registers all cities in the CSV as nodes in Neo4j.
  def registerCities = {
    val tx = graph.beginTx()
    for (city <- cities.getLines) {
      NodeCreator.create(toCity(city))
    }
    tx.success()
  }

  def getCityByName(name: String): String = {
    graph.findNode(Labels.City, "name", "Chicago").getProperty("name").toString
  }

  def registerFlights(flights: List[Flight]) = ???

  implicit def toCity(row: String): City = {
    val split = row.split(",")
    City(
      id = split(0).toInt,
      name = split(1),
      population = split(5).toDouble.toLong,
      location = GeographicLocation(latitude = split(3),
        longitude = split(4))
    )
  }

}

