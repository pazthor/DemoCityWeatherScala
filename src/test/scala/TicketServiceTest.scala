import Services.TicketService
import org.scalatest.FunSuite

class TicketServiceTest extends FunSuite {

  test("TicketService.existAirport") {
    val ticket = new TicketService()
    ticket.readCsv("dataset.csv")
    assert(ticket.existAirport("MEX") === true)
    assert(ticket.existAirport("CUN") === true)
  }

}