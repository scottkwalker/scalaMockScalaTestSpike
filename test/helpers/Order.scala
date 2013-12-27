package helpers

class Order(warehouse: IWarehouse) extends IOrder {
  override def submitOrder: Integer = ???

  override def cancelOrder(index: Integer): Unit = {
    warehouse.remove(index)
  }
}
