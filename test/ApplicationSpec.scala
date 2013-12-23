package controllers

import org.scalatest.WordSpec

class SetSpec extends WordSpec {
  "An empty Set" should {
    "return 0 with .size is invoked" in {
      assert(Set.empty.size == 0)
    }

    "throw NoSuchElementException when .head is invoked" in {
      intercept[NoSuchElementException] {
        Set.empty.head
      }
    }
  }


  "A test containing keyword pending" when {
    "run by play test" should {
      "show up as pending in the list" in {
        pending
        fail("not implementing a body")
      }
    }
  }
}