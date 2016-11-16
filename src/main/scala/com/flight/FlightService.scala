package com.flight

import java.time.LocalDate

import akka.actor.Actor
import com.flight.model.GeographicLocation
import com.flight.services.{CityFinder, MapPointRetriever, MockFlightGenerator, NearestCityService}
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization.write
import spray.http.MediaTypes._
import spray.routing._

class FlightServiceActor extends Actor with FlightService {

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(route)
}


// this trait defines our service behavior independently from the service actor
trait FlightService extends HttpService {

  implicit val formats = DefaultFormats

  val route = get {
    path("") {
      getFromResource("webapp/index.html")
    } ~ pathPrefix("webapp") {
      compressResponse() {
        getFromResourceDirectory("webapp")
      }
    } ~ pathPrefix("bower_components") {
      compressResponse() {
        getFromResourceDirectory("webapp/bower_components")
      }
    } ~ pathPrefix("assets") {
      compressResponse() {
        getFromResourceDirectory("webapp/assets")
      }
    } ~ path("isActive") {
      respondWithMediaType(`text/html`) {
        complete {
          <html>
            <body>
              <h1>Active!</h1>
            </body>
          </html>
        }
      }
    } ~ path("find_nearest_city") {
      parameters('latitude, 'longitude) { (latitude, longitude) =>
        respondWithMediaType(`application/json`) {
          complete {
            val nearestCity = NearestCityService.find {
              GeographicLocation(BigDecimal(latitude), BigDecimal(longitude))
            }
            write(nearestCity)
          }
        }
      }
    } ~ path("retrieve_map_points") {
        parameters('north, 'south, 'east, 'west) { (north, south, east, west) =>
          respondWithMediaType(`application/json`) {
            complete {
              val mapPoints = MapPointRetriever.retrieve(north, south, east, west)
              write(mapPoints)
            }
          }
        }
    } ~ path("get_flights") {
      parameters('origin, 'dest, 'year, 'month, 'day) { (origin, dest, year, month, day) =>
        respondWithMediaType(`application/json`) {
          complete {
            val flights = MockFlightGenerator.generate(
              CityFinder.getByName(origin).get,
              CityFinder.getByName(dest).get,
              LocalDate.of(year.toInt, month.toInt, day.toInt)
            )
            write(flights)
          }
        }
      }
    }
  }
}
