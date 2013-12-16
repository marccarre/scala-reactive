organization := "com.carmatech"

name := "scala-reactive"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.3"

resolvers ++= Seq(
	"Typesafe" at "http://repo.typesafe.com/typesafe/releases/",
	"spray" at "http://repo.spray.io"
)

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

libraryDependencies ++= Seq (
		// Reactive processing:
    "com.netflix.rxjava" % "rxjava-scala" % "0.15.1",
    "play" %% "play-iteratees" % "2.1.5",
    "io.spray" % "spray-client" % "1.2.0",
    "com.typesafe.akka" %% "akka-actor" % "2.2.3",
    // Deserialization:
    "io.spray" %% "spray-json" % "1.2.5",
    // "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.3.0",
    // Testing
    "org.specs2" %% "specs2" % "2.3.6" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")