package com.casualmiracles.concordion

import java.util.UUID

import org.concordion.api.{Element, Resource}

import scala.xml.{Node, PrettyPrinter}

package object elementsyntax {
  type ParentElement = Element
  type ChildElement  = Element

  implicit class ElementSyntax(element: ParentElement) {
    def styles(classes: String*): ParentElement = {
      classes.foreach(element.addStyleClass)
      element
    }

    def success: ParentElement = element.styles("success")

    def failure: ParentElement = element.styles("failure")

    def cond(b: Boolean, t: ⇒ ParentElement ⇒ Unit, f: ⇒ ParentElement ⇒ Unit): ParentElement = {
      if (b) t(element) else f(element)
      element
    }

    def text(s: String): ParentElement = element.appendText(s)

    def ul(fs: (ChildElement ⇒ Unit)*): ParentElement = create0("ul", fs: _*)

    def li(fs: (ChildElement ⇒ Unit)*): ParentElement = create0("li", fs: _*)

    def noteList(notes: String*): ParentElement =
      ul(_.styles("element-syntax-notes"),
        ul ⇒
          notes.foreach { n ⇒
            ul.li(_.text(n))
          })

    def a(href: String, fs: (ChildElement ⇒ Unit)*): ParentElement =
      create1("a", _.addAttribute("href", href), fs: _*)

    def img(src: String, fs: (ChildElement ⇒ Unit)*): ParentElement =
      create1("img", _.addAttribute("src", src), fs: _*)

    def span(fs: (ChildElement ⇒ Unit)*): ParentElement = create0("span", fs: _*)
    def div(fs: (ChildElement ⇒ Unit)*): ParentElement  = create0("div", fs: _*)

    def twoColumn(left: Element ⇒ Unit,
                  right: Element ⇒ Unit,
                  fs: (ChildElement ⇒ Unit)*): ParentElement =
      div(_.styles("element-syntax-two-col"),
        liftElementUpdates(fs: _*),
        _.div(_.styles("element-syntax-equal-width"), left(_)),
        _.div(_.styles("element-syntax-equal-width"), right(_)))

    def columnTitle(title: String): ParentElement =
      div(
        _.styles("element-syntax-column-title")
          .text(title)
      )

    def tooltip(resource: Resource, body: String, bodyStyles: String*): ParentElement = {
      def pathToImage(r: Resource): String =
        if (r.getParent == null) "image/info16.png"
        else "../" + pathToImage(r.getParent)

      a(
        "#",
        _.styles("tt"),
        _.img(
          pathToImage(resource.getParent),
          _.addAttribute("alt", "i"),
          _.appendNonBreakingSpace(),
          _.span(
            _.styles("tooltip"),
            _.span(_.styles("top"), _.appendNonBreakingSpaceIfBlank),
            _.span(_.styles("middle"), _.styles(bodyStyles: _*), _.text(body)),
            _.span(_.addStyleClass("bottom"), _.appendNonBreakingSpaceIfBlank)
          )
        )
      )
    }

    def collapsible(buttonText: String, body: (ChildElement ⇒ Unit)): ParentElement = {
      val uuid = "collapsible_" + UUID.randomUUID.toString.replace("-", "")

      create0(
        "button",
        _.addAttribute("type", "button"),
        _.addAttribute("data-toggle", "collapse"),
        _.addAttribute("data-target", s"#$uuid"),
        _.styles("btn", "btn-info", "btn-xs"),
        _.appendText(buttonText)
      )

      div(_.addAttribute("id", uuid), _.styles("collapse"), body)
    }

    def xml(node: Node): ParentElement = pre(new PrettyPrinter(80, 2).format(node))

    def code(text: String, fs: (ChildElement ⇒ Unit)*): ParentElement = pre(text, fs: _*)

    def pre(text: String, fs: (ChildElement ⇒ Unit)*): ParentElement =
      create1("pre", _.appendText(text), fs: _*)

    def collapsibleCode(buttonName: String, code: String, codeFs: (ChildElement ⇒ Unit)*): Unit =
      element
        .appendNonBreakingSpace()
        .collapsible(
          buttonName,
          _.div(
            _.styles("container"),
            _.div(
              _.styles("row"),
              _.div(_.styles("col-md-2"), _.appendNonBreakingSpaceIfBlank),
              _.div(
                _.code(code, codeFs: _*)
                  .styles("col-md-8", "pre-scrollable")
              ),
              _.div(_.styles("col-md-2"), _.appendNonBreakingSpaceIfBlank)
            )
          )
        )

    def table(columnNames: List[String], rows: List[List[String]]): Unit =
      create1(
        "table",
        e ⇒ {
          e.addStyleClass("table table-bordered")
          e.create1("tr", e ⇒ columnNames.map(name ⇒ e.create1("th", _.text(name))))
          rows.foreach { r =>
            e.create1("tr", e ⇒ r.map(v ⇒ e.create1("td", _.text(v))))
          }
        }
      )

    def create0(tag: String, fs: (ChildElement ⇒ Unit)*): ParentElement = {
      val child = new Element(tag)
      fs.foreach { _(child) }
      element.appendChild(child)
      element
    }

    def create1(tag: String,
                        f: ChildElement ⇒ Unit,
                        fs: (ChildElement ⇒ Unit)*): ParentElement =
      create0(tag, f :: fs.toList: _*)

    def liftElementUpdates(fs: (ChildElement ⇒ Unit)*): ChildElement ⇒ Unit = e ⇒ fs.foreach(_(e))
  }
}
