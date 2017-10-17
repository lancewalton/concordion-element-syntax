package com.casualmiracles.concordion.elementsyntax

import org.concordion.api.Resource
import org.concordion.api.extension.{ConcordionExtender, ConcordionExtension}

class ElementSyntaxExtension extends ConcordionExtension {
  def addTo(concordionExtender: ConcordionExtender): Unit = {
    val path = "/" + getClass.getPackage.getName.replaceAll("\\.", "/")
    concordionExtender.withLinkedCSS(path + "/elementsyntax.css", new Resource("/elementsyntax.css"))
  }
}
