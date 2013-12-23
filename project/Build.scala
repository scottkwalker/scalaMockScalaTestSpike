import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "scalaMockScalaTestSpike"
  val appVersion      = "1.0-SNAPSHOT"



  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    cache,
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1" % "test" // Compatible with ScalaTest
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here
    resolvers ++= Seq(
      "snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "releases" at "http://oss.sonatype.org/content/repositories/releases"
    )
  )

}
