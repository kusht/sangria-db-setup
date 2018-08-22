name := "sangria-subscriptions-example"
version := "0.1.0-SNAPSHOT"

description := "Sangria Subscriptions Example"

scalaVersion := "2.12.2"
scalacOptions ++= Seq("-deprecation", "-feature")
val opRabbitVersion = "2.1.0"

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.2.0",
  "org.sangria-graphql" %% "sangria-spray-json" % "1.0.0",
  "org.sangria-graphql" %% "sangria-akka-streams" % "1.0.0",

  "com.typesafe.akka" %% "akka-http" % "10.0.6",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.6",
  "de.heikoseeberger" %% "akka-sse" % "2.0.0",

  "org.scalatest" %% "scalatest" % "3.0.1" % "test",

  // akka-http still depends on 2.4 but should work with 2.5 without problems
  // see https://github.com/akka/akka-http/issues/821
  "com.typesafe.akka" %% "akka-stream" % "2.5.1",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.1" % "test",

  "com.typesafe.play" %% "play-slick" % "3.0.0",
  "mysql" % "mysql-connector-java" % "6.0.6",

  "com.spingo" %% "op-rabbit-core"        % opRabbitVersion,
  "com.spingo" %% "op-rabbit-play-json"   % opRabbitVersion,
  "com.spingo" %% "op-rabbit-json4s"      % opRabbitVersion,
  "com.spingo" %% "op-rabbit-airbrake"    % opRabbitVersion,
  "com.spingo" %% "op-rabbit-akka-stream" % opRabbitVersion
)

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

Revolver.settings
