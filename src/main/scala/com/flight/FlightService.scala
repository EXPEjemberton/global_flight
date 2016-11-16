package com.flight

import akka.actor.Actor
import com.flight.model.GeographicLocation
import com.flight.services.NearestCityService
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
    path("isActive") {
      get {
        respondWithMediaType(`text/html`) {
          // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>This shit is <i>active</i>!</h1>
              </body>
            </html>
          }
        }
      }
    } ~
      path("nearest_city") {
        get {
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
        }
      }
  }
}