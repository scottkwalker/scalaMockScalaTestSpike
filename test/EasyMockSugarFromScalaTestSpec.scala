import org.scalatest.WordSpec
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._
import org.easymock.EasyMock

class EasyMockSugarFromScalaTestSpec extends WordSpec with EasyMockSugar {
  "EasyMock" should {
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

    "return a stub value for a specific input using sugar 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      mockOrder.add.andReturn(expected)

      whenExecuting(mockOrder){
        // Act
        val result: Integer = mockOrder.add

        // Assert
        assert(result == expected)
      }
    }

    "return a stub value for a specific input using sugars 'expecting' and 'whenExecuting'" in {
      // Arrange
      val expected = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.add.andReturn(expected)
      }

      whenExecuting(mockOrder){
        // Act
        val result: Integer = mockOrder.add

        // Assert
        assert(result == expected)
      }
    }

    "verify func called with any int input" in {
      // Arrange
      val randomInt = 123
      val mockOrder = strictMock[IOrder]
      expecting {
        mockOrder.remove(anyInt())
      }

      whenExecuting(mockOrder){
        // Act
        mockOrder.remove(randomInt)
      } // Assert
    }

    "DI - verify void method was called once" in {
      // arrange
      val index = 50
      val mockWarehouse = strictMock[IWarehouse]
      expecting {
        mockWarehouse.remove(index)
        EasyMock.expectLastCall().once()
      }

      val sut: Order = new Order(mockWarehouse)

      whenExecuting(mockWarehouse){
        // Act
        sut.remove(index)
      } // Assert
    }
  }
}