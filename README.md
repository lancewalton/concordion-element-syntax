# Concordion Element Syntax

This is some syntax for contributing to Concordion's output HTML in your own extensions.

To use it, add com.casualmiracles.concordion.elementSyntax.ElementSyntaxExtension as an
extension to your test fixture class. e.g.

```scala
@org.concordion.api.extension.Extension(classOf[com.casualmiracles.concordion.elementSyntax.ElementSyntaxExtension])
```

This extension adds no commands. The reason for registering it as an extension is to allow the css to be added.

The output HTML also needs [Bootstrap](http://getbootstrap.com). This is not included in this
distribution. The distributions has been tested with Bootstrap version v4.0.0-beta. The
Bootstrap resources also need to be added to the test fixture class:

```scala
@org.concordion.api.ConcordionResources(
  Array("/web/bootstrap.min.css",
        "/web/bootstrap.min.js")
)
```

Altogether, it should look like this:

```scala
import org.concordion.api.extension.Extension
import org.concordion.api.ConcordionResources
import com.casualmiracles.concordion.elementSyntax.ElementSyntaxExtension

@Extension(classOf[ElementSyntaxExtension])
@ConcordionResources(
  Array("/web/bootstrap.min.css",
        "/web/bootstrap.min.js")
)
class MyTestFixture {
  ...
}
```