# Concordion Element Syntax

This project provides syntax for contributing to Concordion's output HTML in your own extensions.

To use it, add `com.casualmiracles.concordion.elementSyntax.ElementSyntaxExtension` as an
extension to your test fixture class. e.g.

```scala
import org.concordion.api.extension.Extension
import com.casualmiracles.concordion.elementSyntax.ElementSyntaxExtension

@Extension(classOf[ElementSyntaxExtension])
```

This extension adds no commands. The reason for registering it as an extension is to allow the css included in the jar file
to be included in the HTML output.

The output HTML also needs [Bootstrap](http://getbootstrap.com). This is not included in this
distribution. The distribution has been tested with Bootstrap version v4.0.0-beta. The
Bootstrap resources also need to be added to the test fixture class:

```scala
import org.concordion.api.ConcordionResources

@ConcordionResources(
  Array("/web/bootstrap.min.css",
        "/web/bootstrap.min.js")
)
```

This assumes `bootstrap.min.css` and `bootstrap.min.js` are on the classpath in the `web` directory. e.g. if you have the
conventional project structure:

```
project
  src
    main
      resources
      scala
    test
      resources
      scala
```

Then the `project/src/test/resources` directory will have a web subdirectory containining `bootstrap.min.css` and `bootstrap.min.js`.

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