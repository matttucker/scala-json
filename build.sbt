name := "json"

scalaVersion := "2.9.1"

//
// Library dependencies
//
// libraryDependencies ++= Seq(
//	groupID % artifactID % revision,
//	groupID % otherID % otherRevision
// )
//
// If you use groupID %% artifactID % revision rather than groupID % artifactID % revision 
// (the difference is the double %% after the groupID), sbt will add your project's Scala version to the artifact name. 
// This is just a shortcut. 
//
libraryDependencies ++= Seq(
  "net.liftweb" %% "lift-json" % "2.4-M5",
  "org.specs2" %% "specs2" % "1.10" % "test",
  "junit" % "junit" % "4.9" % "test" 
)


resolvers ++= Seq("snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
                    "releases"  at "http://oss.sonatype.org/content/repositories/releases")


