package WeatherDemo

import Services.TicketService

object Main {
  def main(args: Array[String]) {

    val service = new TicketService()
    service.readCsv("dataset.csv")
    service.getWeather()

  }
}
