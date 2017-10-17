package com.casualmiracles.concordion.util

import org.concordion.api.{CommandCall, Element}

class InsertOncePerCallElement() {
  var previousCallElement: Option[Element] = None

  def apply(commandCall: CommandCall, f: Element ⇒ Unit): Unit = {
    if (!previousCallElement.contains(commandCall.getElement)) {
      previousCallElement = Some(commandCall.getElement)
      f(commandCall.getElement)
    }
  }
}


