package com.flight.services

import com.flight.model.{City, GeographicLocation}
import eu.fakod.neo4jscala.{Neo4jWrapper, SingletonEmbeddedGraphDatabaseServiceProvider, TypedTraverser}

object NeoCityRegister extends Neo4jWrapper with SingletonEmbeddedGraphDatabaseServiceProvider with TypedTraverser {

  def neo4jStoreDir = "/Users/jemberton/Documents/Neo4j/default.graphdb"

  val cities = io.Source.fromFile(CSVStore.cities)

  // Registers all cities in the CSV as nodes in Neo4j.
  def registerCities(list: List[City]) = {
    withTx {
      implicit neo =>
        for {
          city <- cities.getLines
        } createNode(toCity(city))
    }
  }

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
