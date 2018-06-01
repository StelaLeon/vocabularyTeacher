import play.sbt.routes.RoutesKeys.routesImport

name := """scala-dci"""

version := "1.0-SNAPSHOT"

lazy val `simple-vocabulary-teacher` = (project in file(".")).enablePlugins(PlayScala)

com.typesafe.sbt.SbtScalariform.scalariformSettings

routesGenerator := InjectedRoutesGenerator


scalaVersion := "2.11.1"

routesImport += "binders.PathBinders._"

routesImport += "binders.QueryStringBindable._"

libraryDependencies ++= Seq()

libraryDependencies += filters

libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.6.15"
