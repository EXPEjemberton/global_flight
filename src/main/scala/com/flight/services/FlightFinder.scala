package com.flight.services

import com.flight.model.Airport
import eu.fakod.neo4jscala.{Neo4jWrapper, SingletonEmbeddedGraphDatabaseServiceProvider, TypedTraverser}

import scala.collection.mutable
import scala.sys.ShutdownHookThread

class FlightFinder extends Neo4jWrapper with SingletonEmbeddedGraphDatabaseServiceProvider with TypedTraverser {

  def neo4jStoreDir = "/Users/jemberton/Documents/Neo4j/default.graphdb"

  ShutdownHookThread {
    shutdown(ds)
  }

  val nodeMap: mutable.Map[String, String] = mutable.Map()

  Airport.supported.foreach { first =>
    Airport.supported.foreach { second =>
      nodeMap += (first -> second)
    }
  }

}
