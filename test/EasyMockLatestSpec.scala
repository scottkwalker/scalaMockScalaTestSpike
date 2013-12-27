package controllers

import org.scalatest.WordSpec
import org.easymock.EasyMock._
import org.easymock.EasyMock
import helpers._

class EasyMockLatestSpec extends WordSpec {
  "EasyMockLatest" should {
    val expected = 123
    val unexpected = 456

    "create mock (it is probably a strictMock)" in {
      val sut = createMock(classOf[IOrder])
    }

    "create strict mock" in {
      val sut = createStrictMock(classOf[IOrder])
    }

    "returns a stub value" in {
      val mockOrder = createStrictMock(classOf[IOrder])
      EasyMock.expect(mockOrder.submitOrder).andReturn(expected)
      EasyMock.replay(mockOrder) // Set mocks into testing mode.

      val result: Integer = mockOrder.submitOrder

      assert(result == expected)
      verify(mockOrder)
    }

    "verify func called with any int input" in {
      // Arrange
      val sut = createStrictMock(classOf[IOrder])
      EasyMock.expect(sut.cancelOrder(EasyMock.anyInt()))
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(sut)
    }

    "verify func called with exact int input" in {
      // Arrange
      val sut = createStrictMock(classOf[IOrder])
      EasyMock.expect(sut.cancelOrder(expected))
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(sut)
    }

    "verify func called atLeastOnce with exact int input" in {
      // Arrange
      val sut = createStrictMock(classOf[IOrder])
      EasyMock.expect(sut.cancelOrder(expected)).atLeastOnce()
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(sut)
    }

    "throw if func expected to be called once but is called more than once" in {
      // Arrange
      val sut = createStrictMock(classOf[IOrder])
      EasyMock.expect(sut.cancelOrder(anyInt())).once()
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      // Act
      sut.cancelOrder(expected)

      // Assert
      intercept[java.lang.AssertionError] {
        sut.cancelOrder(unexpected)
      }
    }

    "DI - verify void func was called once" in {
      // Arrange
      val mockWarehouse = createStrictMock(classOf[IWarehouse])
      EasyMock.expect(mockWarehouse.remove(expected)).once()
      EasyMock.replay(mockWarehouse)

      val order: Order = new Order(mockWarehouse)

      // Act
      order.cancelOrder(expected)

      // Assert
      EasyMock.verify(mockWarehouse)
    }

    "stub a func to throw" in {
      // Arrange
      val mockOrder = createStrictMock(classOf[IOrder])
      EasyMock.expect(mockOrder.cancelOrder(anyInt())).andThrow(new scala.RuntimeException())
      EasyMock.replay(mockOrder) // Toggle framework from record to testing/replay mode.

      // Act & assert
      intercept[scala.RuntimeException] {
        mockOrder.cancelOrder(expected)
      }
    }
  }
}