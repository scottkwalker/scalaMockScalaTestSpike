package controllers

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import helpers._
import org.mockito.Matchers._

class MockitoSugarFromScalaTestSpec extends WordSpec with MockitoSugar {
  "MockitoSugar from ScalaTest" should {
    val expected = 123
    val unexpected = 456

    "create mock without throwing" in {
      val sut = mock[IOrder]
    }

    "return a stub value" in {
      // Arrange
      val sut = mock[IOrder]
      when(sut.submitOrder).thenReturn(expected)

      // Act
      val result: Integer = sut.submitOrder

      // Assert
      assert(result == expected)
    }

    "verify a stub was called once with any int param" in {
      // Arrange
      val sut = mock[IOrder]

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(sut).cancelOrder(anyInt())
    }

    "verify a stub was called once with expected int param" in {
      // Arrange
      val sut = mock[IOrder]

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(sut).cancelOrder(expected)
    }

    "verify func was called atLeastOnce with expected int param" in {
      // Arrange
      val sut = mock[IOrder]

      // Act
      sut.cancelOrder(expected)
      sut.cancelOrder(expected)

      // Assert
      verify(sut, atLeastOnce()).cancelOrder(expected)
    }

    "throw if func expected to be called atMost once but is called more than once" in {
      // Arrange
      val sut = mock[IOrder]

      // Act
      sut.cancelOrder(expected)
      sut.cancelOrder(unexpected)

      // Assert
      intercept[org.mockito.exceptions.base.MockitoAssertionError] {
        verify(sut, atMost(1)).cancelOrder(anyInt())
      }
    }

    "verifyNoMoreInteractions throws when there are method calls that were not stubbed" in {
      // Arrange
      val sut = mock[IOrder]

      // Act
      sut.cancelOrder(unexpected)

      // Assert
      intercept[org.mockito.exceptions.verification.NoInteractionsWanted] {
        // This is not nice as we should be able to create mock or nice mock.
        verifyNoMoreInteractions(sut)
      }
    }

    "DI - verify void func was called once" in {
      // Arrange
      val mockWarehouse = mock[IWarehouse]

      val sut: Order = new Order(mockWarehouse)

      // Act
      sut.cancelOrder(expected)

      // Assert
      verify(mockWarehouse).remove(expected)
    }

    "stub a func to throw" in {
      // Arrange
      val sut = mock[IOrder]
      when(sut.cancelOrder(anyInt())).thenThrow(new scala.RuntimeException())

      // Act & assert
      intercept[scala.RuntimeException] {
        sut.cancelOrder(expected)
      }
    }
  }
}