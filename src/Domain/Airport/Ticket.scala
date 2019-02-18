package Airport

class Ticket(originIata:String, destinationIata:String, airline: String, flightNumber: Int) {
  val OriginIataCode:String = originIata
  val DestinationIataCode:String  = destinationIata
  val Airline: String = airline
  val FlightNumber: Int = flightNumber
}
