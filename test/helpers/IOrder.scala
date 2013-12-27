package helpers

trait IOrder {
  def submitOrder: Integer
  def cancelOrder(index: Integer): Unit
}