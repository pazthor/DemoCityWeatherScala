import Services._
import org.scalatest.FunSuite


class TicketServiceTest extends FunSuite {

  test("TicketService.existAirport") {
    val ticket = new TicketService()
    ticket.readCsv("dataset.csv")
    assert(ticket.existAirport("MEX") === true)
    assert(ticket.existAirport("CUN") === true)
  }

  test("ApiOpenWeatherService.apiCall"){
    val api = new ApiOpenWeatherService()
    val result  = api.apiCall(25.7785f,-100.107f)

    result match {
      case WeatherReport( city, temp, min, max, conditions, lastUpdate)  =>
        assert("overcast clouds"=== conditions)
      case Error("") =>
        println(" Error")
    }
  }
}