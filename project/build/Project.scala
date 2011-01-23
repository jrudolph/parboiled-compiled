import sbt._

class ParboiledCompiler(info: ProjectInfo) extends DefaultProject(info) {
  val snapshots = ScalaToolsSnapshots

  lazy val scalatest    = "org.scalatest" % "scalatest" % "1.2" % "test" withSources()
  val specs = "org.scala-tools.testing" %% "specs" % "1.6.6" % "test" withSources()
  val testng       = "org.testng" % "testng" % "5.14.1" % "test" withSources()

  val parboiledC   = "org.parboiled" % "parboiled-core" % "0.11.0-SNAPSHOT" withSources()
  val parboiledS   = "org.parboiled" % "parboiled-scala" % "0.11.0-SNAPSHOT" withSources()

  val mnemonics = "virtualvoid" %% "mnemonics" % "2.2-SNAPSHOT"
}
