import org.scalatest.WordSpec
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._
import org.easymock.EasyMock

class EasyMockNativeToScalaTestSpec extends WordSpec with EasyMockSugar {
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

    "create mock using EasyMockSugar (should default to being a strict mock)" in {
      val sut = mock[Sut]
    }

    "create strict mock using EasyMockSugar (which creates EasyMock version native to this version of ScalaTest)" in {
      val sut = strictMock[Sut]
    }

    "return a stub value for a specific input using sugar 'expecting'" in {
      // Arrange
      val expected = 123
      val sut = strictMock[Sut]
      expecting {
        sut.get(0).andReturn(expected)
      }
      replay(sut) // Set mocks into testing mode.

      // Act
      val result: Integer = sut.get(0)

      // Assert
      assert(result == expected)
      verify(sut) // You don't need to tell it which calls to verify, it will verify ALL recorded expectations.
    }

    "return a stub value for a specific input using sugar 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val sut = strictMock[Sut]
      call(sut.get(0)).andReturn(expected)

      whenExecuting(sut){
        // Act
        val result: Integer = sut.get(0)

        // Assert
        assert(result == expected)
      }
    }

    "return a stub value for a specific input using sugars 'expecting' and 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val sut = strictMock[Sut]
      expecting {
        sut.get(0).andReturn(expected)
      }

      whenExecuting(sut){
        // Act
        val result: Integer = sut.get(0)

        // Assert
        assert(result == expected)
      }
    }

    "return a stub value for any int input" in {
      // Arrange
      val expected = 123
      val sut = strictMock[Sut]
      expecting {
        sut.get(anyInt()).andReturn(expected)
      }

      whenExecuting(sut){
        // Act
        val result: Integer = sut.get(0)

        // Assert
        assert(result == expected)
      }
    }

    "verify void method was called once" in {
      // arrange
      val index = 50
      val mockWarehouse = strictMock[Warehouse]
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