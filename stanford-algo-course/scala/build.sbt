scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "tests"

scalaVersion := "2.11.1"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.1.6" % "test"

libraryDependencies += "com.github.axel22" %% "scalameter" % "0.5-SNAPSHOT" % "test"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered := false
