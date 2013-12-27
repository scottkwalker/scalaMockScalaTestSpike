import helpers._
import org.scalatest.WordSpec
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._

class EasyMockSugarFromScalaTestSpec extends WordSpec with EasyMockSugar {
  "EasyMockSugar from ScalaTest" should {
    val expected = 123
    val unexpected = 456

    "create mock using EasyMockSugar (should default to being a strict mock)" in {
      val sut = mock[IOrder]
    }

    "create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)" in {
      val sut = strictMock[IOrder]
    }

    "return a stub value for a specific input using sugar 'expecting'" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.submitOrder.andReturn(expected)
      }
      replay(mockOrder) // Toggle framework from record to testing/replay mode.

      // Act
      val result: Integer = mockOrder.submitOrder

      // Assert
      assert(result == expected)
      verify(mockOrder) // You don't need to tell it which calls to verify, it will verify ALL recorded expectations.
    }

    "returns a stub value using sugar 'whenExecuting'" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      mockOrder.submitOrder.andReturn(expected)

      whenExecuting(mockOrder) {
        // Act
        val result: Integer = mockOrder.submitOrder

        // Assert
        assert(result == expected)
      }
    }

    "returns a stub value using sugars 'expecting' and 'whenExecuting'" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.submitOrder.andReturn(expected)
      }

      whenExecuting(mockOrder) {
        // Act
        val result: Integer = mockOrder.submitOrder

        // Assert
        assert(result == expected)
      }
    }

    "verify func was called with any int input" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.cancelOrder(anyInt())
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.cancelOrder(expected)
      } // Assert
    }

    "verify func called with expected int param" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.cancelOrder(expected)
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.cancelOrder(expected)
      } // Assert
    }

    "verify func called atLeastOnce with expected int param" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.cancelOrder(expected).atLeastOnce()
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.cancelOrder(expected)
      } // Assert
    }

    "throw if func expected to be called once but is called more than once" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.cancelOrder(anyInt()).once
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.cancelOrder(expected)

        // Assert
        intercept[java.lang.AssertionError] {
          mockOrder.cancelOrder(unexpected)
        }
      }
    }

    "DI - verify void func was called once" in {
      // Arrange
      val mockWarehouse = strictMock[IWarehouse]
      expecting {
        mockWarehouse.remove(expected).once()
      }

      val sut: Order = new Order(mockWarehouse)

      whenExecuting(mockWarehouse) {
        // Act & assert
        sut.cancelOrder(expected)
      }
    }

    "stub a func to throw" in {
      // Arrange
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.cancelOrder(anyInt()).andThrow(new scala.RuntimeException())
      }

      whenExecuting(mockOrder) {
        intercept[scala.RuntimeException] {
          // Act & assert
          mockOrder.cancelOrder(expected)
        }
      }
    }
  }
}