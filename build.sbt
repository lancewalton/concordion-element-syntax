val buildSettings: Seq[Setting[_]] = Defaults.coreDefaultSettings ++ Seq[Setting[_]](
  organization := "com.casualmiracles",
  name := "concordion-element-syntax",
  version := "1.0",
  scalaVersion := "2.12.3",
  scalaBinaryVersion := "2.12",
  scalacOptions := Seq(
    "-language:_",
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
//    "-Ywarn-value-discard",
    "-Xfuture",
    "-Ywarn-unused-import")
)

enablePlugins(GhpagesPlugin)
enablePlugins(SiteScaladocPlugin)

val websiteSettings = Seq[Setting[_]](
  git.remoteRepo := "git@github.com:lancewalton/concordion-element-syntax.git"
)

resolvers += Resolver.sonatypeRepo("releases")

val allDependencies = Seq(
  "org.concordion"           % "concordion"                               % "2.1.0",
  "org.scala-lang.modules"  %% "scala-xml"                                % "1.0.6"
)

/* see http://www.scala-sbt.org/using_sonatype.html and http://www.cakesolutions.net/teamblogs/2012/01/28/publishing-sbt-projects-to-nexus/
 * Instructions from sonatype: https://issues.sonatype.org/browse/OSSRH-2841?focusedCommentId=150049#comment-150049
 * Deploy snapshot artifacts into repository https://oss.sonatype.org/content/repositories/snapshots
 * Deploy release artifacts into the staging repository https://oss.sonatype.org/service/local/staging/deploy/maven2
 * Promote staged artifacts into repository 'Releases'
 * Download snapshot and release artifacts from group https://oss.sonatype.org/content/groups/public
 * Download snapshot, release and staged artifacts from staging group https://oss.sonatype.org/content/groups/staging
 */
def publishSettings: Seq[Setting[_]] = Seq(
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ ⇒ false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  },

  pomExtra := <licenses>
    <license>
      <name>MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license/</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
    <scm>
      <url>https://github.com/lancewalton/concordion-element-syntax.git</url>
      <connection>scm:https://github.com/lancewalton/concordion-element-syntax.git</connection>
    </scm>
    <url>https://github.com/lancewalton/concordion-element-syntax</url>
    <developers>
      <developer>
        <id>lcw</id>
        <name>Lance Walton</name>
        <email>lance [dot] walton [at] casualmiracles [dot] com</email>
        <organization>Casual Miracles Ltd</organization>
      </developer>
      <developer>
        <id>cjw</id>
        <name>Channing Walton</name>
        <email>channing [dot] walton [at] casualmiracles [dot] com</email>
        <organization>Casual Miracles Ltd</organization>
      </developer>
    </developers>)

lazy val elementSyntax = (project in file("."))
  .settings(
    buildSettings ++
      publishSettings ++
      websiteSettings ++
      Seq(resolvers := Seq(Classpaths.typesafeReleases),
        libraryDependencies ++= allDependencies))
