package controllers

import org.scalatest.WordSpec
import org.scalatest.mock.{MockitoSugar}
import org.mockito.Mockito._

class MockitoSugarFromScalaTestSpec extends WordSpec with MockitoSugar  {
  "MockitoLatest" should {
    trait Sut {
      def get(index: Integer): Integer = ???
    }

    "create mock without throwing" in {
      val sut = mock[Sut]
    }

    "verify a stub was called once" in {
      val expected = 123
      val sut = mock[Sut]
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      verify(sut).get(0)
    }

    "verify a stub was called atLeastOnce" in {
      val expected = 123
      val sut = mock[Sut]
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      verify(sut, atLeastOnce()).get(0)
    }

    "verifyNoMoreInteractions throws when there are method calls that were not stubbed" in {
      val expected = 123
      val sut = mock[Sut]
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(1)

      intercept[org.mockito.exceptions.verification.NoInteractionsWanted] {
        verifyNoMoreInteractions(sut)
      }
    }

    "return a stub value" in {
      val expected = 123
      val sut = mock[Sut]
      when(sut.get(0)).thenReturn(expected)

      val result: Integer = sut.get(0)

      assert(result == expected)
      verify(sut).get(0)
    }

    "stub to throw" in {
      val sut = mock[Sut]
      when(sut.get(1)).thenThrow(new RuntimeException())

      intercept[scala.RuntimeException] {
        sut.get(1)
      }
    }
  }
}