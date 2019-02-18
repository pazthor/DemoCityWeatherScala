package Domain

class Ticket(airLineName: String, flightNum: String, originAirport:AirPort, destinyAirport: AirPort ) {
  val AirLineName: String= airLineName
  val FlightNum: String= flightNum
  val OriginAirport:AirPort= originAirport
  val DestinyAirport: AirPort= destinyAirport

}
