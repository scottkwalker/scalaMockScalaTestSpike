package helpers

trait IWarehouse {
  def add: Integer
  def remove(index: Integer): Unit
}