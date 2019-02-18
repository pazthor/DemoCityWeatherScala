package Airport

import scala.collection.mutable

class Airline(airline: String) {
  val AirLine:String  = airline
  private val Flights: mutable.Map[String, String] =  collection.mutable.Map[String, String]()// TODO: debe ser lista o diccionario?

}
