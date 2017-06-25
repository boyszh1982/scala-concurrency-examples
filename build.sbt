name := "scala-concurrency-examples"

version := "1.0"

scalaVersion := "2.11.8"

// After the main thread exits , the child thread is still running until the end.
fork := false

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases"
)

libraryDependencies += "commons-io" % "commons-io" % "2.4"
        