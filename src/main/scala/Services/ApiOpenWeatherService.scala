package Services

import scala.io.Source
import scala.util.{Failure, Success, Try}
import scala.xml.{XML}



sealed trait WeatherResult
case class Error(error: String) extends WeatherResult
case class WeatherReport( nameCity:String, temp: Double, min: Double, max: Double, conditions: String, lastUpdate:String) extends WeatherResult
case class Main( temp: Double, min: Double, max: Double) extends WeatherResult
case class Weather( conditions: String, description: String) extends WeatherResult
class ApiOpenWeatherService {
  val api_key:String =  "WEATHER_API_KEY"
  val maxTries = 5
  val threadSleep = 1000
  val mode = "xml"

  def makeUrl(latitude:Float, longitude: Float) : String = s"https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&mode=$mode&units=metric&lang=en&appid=$api_key"

  def apiCall(latitude:Float, longitude: Float): WeatherResult = {
    val url = makeUrl(latitude, longitude)
    for (tries <- 1 to maxTries) {
      val response = Try(Source.fromURL(url))
      response match {
        case Success(apiSuccess) =>
          return parseApiResponse(apiSuccess.mkString)

        case Failure(e) =>
          Thread.sleep(threadSleep)
      }
    }
    Error("error descargando url")
  }

  def parseDouble(str: String) : Double = Try(str.toDouble).getOrElse(0.0)

  def parseApiResponse(xmlResponse: String): WeatherResult = {
    val xml = XML.loadString(xmlResponse)
    val current = xml \\ "current"
    if (current.isEmpty)
      Error("error parsing xml response")
    else {
      val city = xml \\ "city" \ "@name"
      val temp = xml \\ "temperature" \ "@value"
      val min  = xml \\ "temperature" \ "@min"
      val max  = xml \\ "temperature" \ "@max"
      val weather = xml \\ "weather" \ "@value"
      val lastUpdate = xml \\ "lastupdate" \ "@value"
      WeatherReport(city.text, parseDouble(temp.text), parseDouble(min.text), parseDouble(max.text), weather.text, lastUpdate.text)
    }
  }

}

