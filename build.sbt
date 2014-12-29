name := "spray-jax-rs"

organization := "nl.spockz"

scalaVersion := "2.11.4"

scalacOptions := Seq("-deprecation")

libraryDependencies += "io.spray"       % "spray-routing"   % "1.3.1"


libraryDependencies += "javax.ws.rs"    % "javax.ws.rs-api" % "2.0.1"

libraryDependencies += "io.spray"       % "spray-testkit"   % "1.3.1" % "test"

libraryDependencies += "org.scalatest" %% "scalatest"       % "2.2.1" % "test"

libraryDependencies += "org.specs2"    %% "specs2"          % "2.3.13" % "test"
