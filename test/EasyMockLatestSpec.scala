package controllers

import org.scalatest.WordSpec
import org.easymock.EasyMock._
import org.easymock.EasyMock

class EasyMockLatestSpec extends WordSpec {
  "EasyMock" should {
    trait Warehouse {
      def remove(index: Integer): Unit
    }

    trait Sut {
      def get(index: Integer): Integer = ???
      def fill(warehouse: Warehouse): Unit
    }

    class Order(index: Integer) extends Sut {
      override def fill(warehouse: Warehouse): Unit = {
        warehouse.remove(index)
      }
    }

    "create mock using the latest version of EasyMock" in {
      val sut = createMock(classOf[Sut])
    }

    "create strict mock using the latest version of EasyMock" in {
      val sut = createStrictMock(classOf[Sut])
    }

    "create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)" in {
      val sut = createStrictMock(classOf[Sut])
    }

    "return a stub value for a specific int input" in {
      val expected = 123
      val sut = createStrictMock(classOf[Sut])
      EasyMock.expect(sut.get(0)).andReturn(expected)
      EasyMock.replay(sut) // Set mocks into testing mode.

      val result: Integer = sut.get(0)

      assert(result == expected)
      verify(sut)
    }

    "return a stub value for any int input" in {
      val expected = 123
      val sut = createStrictMock(classOf[Sut])
      EasyMock.expect(sut.get(EasyMock.anyInt())).andReturn(expected)
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      val result: Integer = sut.get(0)

      assert(result == expected)
      verify(sut)
    }

    "verify void method was called once" in {
      // arrange
      val index = 50
      val mockWarehouse = createStrictMock(classOf[Warehouse])
      mockWarehouse.remove(index)
      EasyMock.expectLastCall().once()
      EasyMock.replay(mockWarehouse)

      val order: Order = new Order(index)

      //act
      order.fill(mockWarehouse)

      // assert
      EasyMock.verify(mockWarehouse)
    }
  }
}