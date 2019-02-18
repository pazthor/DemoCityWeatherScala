package Services

import Domain.{AirPort, Ticket}

class TicketService {
  var Tickets:List[Ticket] =  List[Ticket]()
  var AirPorts:List[AirPort] = List[AirPort]()
  var IataCodeWeather = scala.collection.mutable.Map[String, WeatherResult]()

  val apiOpenWeatherService = new ApiOpenWeatherService()

    def readCsv(fileName: String): Unit = {
      // airport_origin
      val origintIataCodeCol = 4
      val originShortNameCol = 0
      val originNameAirportCol = 5
      val originLatitudeCol = 6
      val originLongitudeCol = 7

      // airport_destination
      val destinationShortNameCol = 1
      val destinationIataCodeCol = 8
      val destinationNameAirportCol = 9
      val destinationLatitudeCol = 10
      val destinationLongitudeCol = 11

      // Flies // tickets
      val airlineCol = 2
      val flightNumCol= 3

      val bufferedSource = scala.io.Source.fromFile(fileName)
      for (line <- bufferedSource.getLines.drop(1)) {
        val col = line.split(",").map(_.trim)

        val oIataCode: String = col(origintIataCodeCol)
        val oShortName:String = col(originShortNameCol)
        val oNameAirPort :String = col(originNameAirportCol)
        val oLatitude:Float = col(originLatitudeCol).toFloat
        val oLongitude:Float = col(originLongitudeCol).toFloat

        val dIataCode: String = col(destinationIataCodeCol)
        val dShortName:String = col(destinationShortNameCol)
        val dNameAirPort :String = col(destinationNameAirportCol)
        val dLatitude:Float = col(destinationLatitudeCol).toFloat
        val dLongitude:Float = col(destinationLongitudeCol).toFloat

        val airLineName:String = col(airlineCol)
        val flightNum :String= col(flightNumCol)

        val originAirport = findOrCreateAirport(oIataCode, oShortName, oNameAirPort, oLatitude, oLongitude )
        val destinyAirport = findOrCreateAirport(dIataCode, dShortName, dNameAirPort, dLatitude, dLongitude )

        val ticket = new Ticket(airLineName, flightNum, originAirport, destinyAirport )

        Tickets =  ticket:: Tickets
        AirPorts = originAirport:: destinyAirport:: AirPorts
      }
    }

  private def findOrCreateAirport(iataCode: String, shortName:String, nameAirPort:String, latitude:Float, longitude:Float ):AirPort ={
    if(existAirport(iataCode)){
      return AirPorts.filter(p => p.IataCode== iataCode).head
    }
    new AirPort(iataCode, shortName, nameAirPort, latitude, longitude)
  }

  def printerAirPortName(): Unit ={
    for( element <- AirPorts ){
      println(element.NameAirport)
    }
  }
  def existAirport(iataCode: String): Boolean = AirPorts.exists(airport => airport.IataCode.contains(iataCode))

  def getWeather(): Unit ={

    var originWeather : WeatherResult = null
    var destinynWeather : WeatherResult = null
    var count = 0
    for(ticket <- Tickets ){
      //origin
      if(IataCodeWeather.exists(p => p._1.contains( ticket.OriginAirport.IataCode) ) ){
        originWeather = IataCodeWeather(ticket.OriginAirport.IataCode)
      }
      else {
        IataCodeWeather +=
          (ticket.OriginAirport.IataCode->apiOpenWeatherService.apiCall(ticket.OriginAirport.Latitude ,ticket.OriginAirport.Longitude) )
        Thread.sleep(100)
        originWeather=IataCodeWeather(ticket.OriginAirport.IataCode)

      }

      if(IataCodeWeather.exists(p => p._1.contains(ticket.DestinyAirport.IataCode) ) ){
        destinynWeather = IataCodeWeather(ticket.DestinyAirport.IataCode)
      }
      else {
        IataCodeWeather +=
          (ticket.DestinyAirport.IataCode -> apiOpenWeatherService.apiCall(ticket.DestinyAirport.Latitude ,ticket.DestinyAirport.Longitude) )
        Thread.sleep(100)
        destinynWeather= IataCodeWeather(ticket.DestinyAirport.IataCode)
      }
      val originFormatWeather = showWeather(originWeather)
      val destinyFormatWeather = showWeather(destinynWeather)

      printer(count, ticket,originFormatWeather , destinyFormatWeather )
      count= count+1
    }
  }

  def showWeather(weather: WeatherResult):String={
    weather match {
      case Error(err) =>
        s"Error: $err"
      case WeatherReport(nameCity, temp, min, max, conditions, lastUpdate) =>
         s"Temperature: $temp, temperature min:$min, temperature max: $max, weather: $conditions. Last Update: $lastUpdate."
    }

  }
  def printer(count:Int,ticket: Ticket, originWeather:String, destinyWeather:String): Unit ={
    println(s"$count — [FlightNum:${ticket.FlightNum}] Weather of the airPort origin ${ticket.OriginAirport.ShortName} :$originWeather -> " +
      s"Weather of the airPort Destiny ${ticket.DestinyAirport.ShortName} :$destinyWeather")
  }

}
