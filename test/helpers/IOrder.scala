package helpers

trait IOrder {
  def add: Integer
  def remove(index: Integer): Unit
}