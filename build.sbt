name := "spray-jax-rs"

organization := "nl.spockz"

scalaVersion := "2.11.4"

scalacOptions := Seq("-deprecation")

libraryDependencies += "javax.ws.rs"    % "javax.ws.rs-api" % "2.0"


// TODO: Get rid of this dependency, without it the code breaks on missing Symbols for Cookie
libraryDependencies += "org.glassfish.jersey.core"    % "jersey-common" % "2.14" % "provided"

libraryDependencies += "com.typesafe.akka"                       %%  "akka-actor"                  % "2.3.6" % "provided"

libraryDependencies += "io.spray"      %% "spray-routing"   % "1.3.1"

libraryDependencies += "io.spray"      %% "spray-testkit"   % "1.3.1" % "test"

//libraryDependencies += "org.scalatest" %% "scalatest"       % "2.2.1" % "test"

libraryDependencies += "org.specs2"    %% "specs2"          % "2.3.13" % "test"

libraryDependencies += "org.scala-lang" % "scala-reflect"   % scalaVersion.value