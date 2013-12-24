package controllers

import org.scalatest.WordSpec
import org.mockito.Mockito._

class MockitoLatestSpec extends WordSpec {
  "MockitoLatest" should {
    trait Sut {
      def get(index: Integer): Integer = ???
    }

    "create mock without throwing" in {
      val sut = mock(classOf[Sut])
    }

    "verify a stub was called once with exact int param" in {
      val expected = 123
      val sut = mock(classOf[Sut])
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      verify(sut).get(0)
    }

    "verify a stub was called atLeastOnce with exact int param" in {
      val expected = 123
      val sut = mock(classOf[Sut])
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      verify(sut, atLeastOnce()).get(0)
    }

    "verify a stub was called once with any int param" in {
      val expected = 123
      val sut = mock(classOf[Sut])
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      verify(sut).get(0)
    }

    "verifyNoMoreInteractions throws when there are method calls that were not stubbed" in {
      val expected = 123
      val sut = mock(classOf[Sut])
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(1)

      intercept[org.mockito.exceptions.verification.NoInteractionsWanted] {
        // This is not nice as we should be able to create mock or nice mock.
        verifyNoMoreInteractions(sut)
      }
    }

    "return a stub value" in {
      val expected = 123
      val sut = mock(classOf[Sut])
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      assert(result == expected)
    }

    "stub to throw" in {
      val sut = mock(classOf[Sut])
      when(sut.get(1)).thenThrow(new RuntimeException())

      intercept[scala.RuntimeException] {
        sut.get(1)
      }
    }
  }
}