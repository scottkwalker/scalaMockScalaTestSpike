package helpers

class Order(warehouse: IWarehouse) extends IOrder {
  override def add: Integer = ???

  override def remove(index: Integer): Unit = {
    warehouse.remove(index)
  }
}
