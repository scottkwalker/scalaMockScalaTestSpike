import helpers._
import org.scalatest.{FeatureSpec, GivenWhenThen}
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._

class EasyMockSugarGivenWhenThenFromScalaTestSpec extends FeatureSpec with GivenWhenThen with EasyMockSugar {
  feature("EasyMockSugar GivenWhenThen from ScalaTest") {
    val expected = 123
    val unexpected = 456

    info("As a programmer")
    info("I want to be able to submit and cancel orders")
    info("So that I can communicate with the warehouse")

    scenario("create mock using EasyMockSugar (should default to being a strict mock)") {
      When("construct mock")
      val sut = mock[IOrder]
      Then("does not throw")
    }

    scenario("create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)") {
      When("construct strict mock")
      val sut = strictMock[IOrder]
      Then("does not throw")
    }

    scenario("return a stub value for a specific input using sugar 'expecting'") {
      Given("submitOrder stubbed")
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.submitOrder.andReturn(expected)
      }
      replay(mockOrder) // Toggle framework from record to testing/replay mode.

      // Act
      When("submit order")
      val result: Integer = mockOrder.submitOrder

      // Assert
      Then("returns expected")
      assert(result == expected)
      verify(mockOrder) // You don't need to tell it which calls to verify, it will verify ALL recorded expectations.
    }

    scenario("returns a stub value using sugar 'whenExecuting'") {
      // Arrange
      Given("submitOrder stubbed")
      val mockOrder = strictMock[IOrder]
      mockOrder.submitOrder.andReturn(expected)

      whenExecuting(mockOrder) {
        // Act
        When("submit order")
        val result: Integer = mockOrder.submitOrder

        // Assert
        Then("returns expected")
        assert(result == expected)
      }
    }
  }
}