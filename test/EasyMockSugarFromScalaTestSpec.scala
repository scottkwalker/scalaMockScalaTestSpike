import helpers._
import org.scalatest.WordSpec
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._

class EasyMockSugarFromScalaTestSpec extends WordSpec with EasyMockSugar {
  "EasyMockSugar from ScalaTest" should {
    "create mock using EasyMockSugar (should default to being a strict mock)" in {
      val sut = mock[IOrder]
    }

    "create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)" in {
      val sut = strictMock[IOrder]
    }

    "return a stub value for a specific input using sugar 'expecting'" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.add.andReturn(expected)
      }
      replay(mockOrder) // Toggle framework from record to testing/replay mode.

      // Act
      val result: Integer = mockOrder.add

      // Assert
      assert(result == expected)
      verify(mockOrder) // You don't need to tell it which calls to verify, it will verify ALL recorded expectations.
    }

    "returns a stub value using sugar 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      mockOrder.add.andReturn(expected)

      whenExecuting(mockOrder) {
        // Act
        val result: Integer = mockOrder.add

        // Assert
        assert(result == expected)
      }
    }

    "returns a stub value using sugars 'expecting' and 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.add.andReturn(expected)
      }

      whenExecuting(mockOrder) {
        // Act
        val result: Integer = mockOrder.add

        // Assert
        assert(result == expected)
      }
    }

    "verify func was called with any int input" in {
      // Arrange
      val randomInt = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.remove(anyInt())
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.remove(randomInt)
      } // Assert
    }

    "verify func called with expected int param" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.remove(expected)
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.remove(expected)
      } // Assert
    }

    "verify func called atLeastOnce with expected int param" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.remove(expected).atLeastOnce()
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.remove(expected)
      } // Assert
    }

    "throw if func expected to be called once but is called more than once" in {
      // Arrange
      val expected = 123
      val unexpected = 456
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.remove(anyInt()).once
      }

      whenExecuting(mockOrder) {
        // Act
        mockOrder.remove(expected)

        // Assert
        intercept[java.lang.AssertionError] {
          mockOrder.remove(unexpected)
        }
      }
    }

    "DI - verify void func was called once" in {
      // arrange
      val index = 50
      val mockWarehouse = strictMock[IWarehouse]
      expecting {
        mockWarehouse.remove(index).once()
      }

      val sut: Order = new Order(mockWarehouse)

      whenExecuting(mockWarehouse) {
        // Act
        sut.remove(index)
      } // Assert
    }
  }
}