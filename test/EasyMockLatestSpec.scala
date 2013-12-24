package controllers

import org.scalatest.WordSpec
import org.easymock.EasyMock._
import org.easymock.EasyMock

class EasyMockLatestSpec extends WordSpec {
  "EasyMockLatest" should {
    trait IWarehouse {
      def add: Integer
      def remove(index: Integer): Unit
    }

    trait IOrder {
      def add: Integer
      def remove(index: Integer): Unit
    }

    class Order(warehouse: IWarehouse) extends IOrder {
      override def add: Integer = ???

      override def remove(index: Integer): Unit = {
        warehouse.remove(index)
      }
    }

    "create mock using the latest version of EasyMock" in {
      val sut = createMock(classOf[IOrder])
    }

    "create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)" in {
      val sut = createStrictMock(classOf[IOrder])
    }

    "returns a stub value" in {
      val expected = 123
      val mockOrder = createStrictMock(classOf[IOrder])
      EasyMock.expect(mockOrder.add).andReturn(expected)
      EasyMock.replay(mockOrder) // Set mocks into testing mode.

      val result: Integer = mockOrder.add

      assert(result == expected)
      verify(mockOrder)
    }

    "verify func called with any int input" in {
      // Arrange
      val randomInt = 123
      val sut = createStrictMock(classOf[IOrder])
      EasyMock.expect(sut.remove(EasyMock.anyInt()))
      EasyMock.replay(sut) // Toggle framework from record to testing/replay mode.

      // Act
      sut.remove(randomInt)

      // Assert
      verify(sut)
    }

    "DI - verify void func was called once" in {
      // arrange
      val index = 50
      val mockWarehouse = createStrictMock(classOf[IWarehouse])
      mockWarehouse.remove(index)
      EasyMock.expectLastCall().once()
      EasyMock.replay(mockWarehouse)

      val order: Order = new Order(mockWarehouse)

      //act
      order.remove(index)

      // assert
      EasyMock.verify(mockWarehouse)
    }
  }
}