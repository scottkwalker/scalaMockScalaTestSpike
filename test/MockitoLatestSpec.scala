package controllers

import org.scalatest.WordSpec
import org.mockito.Mockito._
import org.mockito.Matchers._
import helpers._

class MockitoLatestSpec extends WordSpec {
  "MockitoLatest" should {
    val expected = 123
    val unexpected = 456

    "create mock (it is probably a strictMock)" in {
      val sut = mock(classOf[IOrder])
    }

    "return a stub value" in {
      // Arrange
      val sut = mock(classOf[IOrder])
      when(sut.add).thenReturn(expected)

      // Act
      val result: Integer = sut.add

      // Assert
      assert(result == expected)
    }

    "verify a stub was called once with any int param" in {
      // Arrange
      val sut = mock(classOf[IOrder])

      // Act
      sut.remove(expected)

      // Assert
      verify(sut).remove(anyInt())
    }

    "verify a stub was called once with expected int param" in {
      // Arrange
      val sut = mock(classOf[IOrder])

      // Act
      sut.remove(expected)

      // Assert
      verify(sut).remove(expected)
    }

    "verify func was called atLeastOnce with expected int param" in {
      // Arrange
      val sut = mock(classOf[IOrder])

      // Act
      sut.remove(expected)
      sut.remove(expected)

      // Assert
      verify(sut, atLeastOnce()).remove(expected)
    }

    "throw if func expected to be called atMost once but is called more than once" in {
      // Arrange
      val sut = mock(classOf[IOrder])

      // Act
      sut.remove(expected)
      sut.remove(unexpected)

      // Assert
      intercept[org.mockito.exceptions.base.MockitoAssertionError] {
        verify(sut, atMost(1)).remove(anyInt())
      }
    }

    "verifyNoMoreInteractions throws when there is a call to a func that were not stubbed" in {
      // Arrange
      val sut = mock(classOf[IOrder])

      // Act
      sut.remove(unexpected)

      // Assert
      intercept[org.mockito.exceptions.verification.NoInteractionsWanted] {
        // This is not nice as we should be able to create mock or nice mock.
        verifyNoMoreInteractions(sut)
      }
    }

    "DI - verify void func was called once" in {
      // Arrange
      val mockWarehouse = mock(classOf[IWarehouse])

      val sut: Order = new Order(mockWarehouse)

      // Act
      sut.remove(expected)

      // Assert
      verify(mockWarehouse).remove(expected)
    }

    "stub a func to throw" in {
      // Arrange
      val sut = mock(classOf[IOrder])
      when(sut.remove(anyInt())).thenThrow(new scala.RuntimeException())

      // Act & assert
      intercept[scala.RuntimeException] {
        sut.remove(expected)
      }
    }
  }
}