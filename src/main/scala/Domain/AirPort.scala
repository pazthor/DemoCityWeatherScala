package Domain

class AirPort(iataCode:String, shortName: String, nameAirPort: String, latitude:Float, longitude: Float) {
  val IataCode:String = iataCode
  val ShortName: String = shortName
  val NameAirport: String = nameAirPort
  val Latitude: Float = latitude
  val Longitude: Float = longitude
  var Weather: Weather = null


}
