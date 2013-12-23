package controllers

import org.scalatest.WordSpec
import org.scalatest.mock.EasyMockSugar
import org.easymock.EasyMock._


class EasyMockSpec extends WordSpec with EasyMockSugar {
  trait DAO {
    def persist[T](t: T)
  }

  "A Jukebox Storage Service" should {
    "should use easy mock to mock out the DAO classes" in {
      val daoMock = createMock(classOf[DAO])
    }
  }
}