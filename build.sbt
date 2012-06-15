
seq((
  Project.defaultSettings
): _*)

resolvers += Resolver.mavenLocal

name := "Google Safebrowsing2"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.6.4",
  "org.slf4j" % "slf4j-simple" % "1.6.4",
  "com.twitter" % "querulous" % "2.7.6",
  "org.scalaj" %% "scalaj-http" % "0.3.1",
  "junit" % "junit" % "4.10" % "test",
  "org.scalatest" %% "scalatest" % "1.7.1" % "test"
)
