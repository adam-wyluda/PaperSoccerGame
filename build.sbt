name := "PaperSoccerGame"
version := "1.0"
scalaVersion := "2.12.2"

libraryDependencies += "com.lihaoyi" %% "utest" % "0.4.5" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.10.0"

libraryDependencies += "com.lihaoyi" % "ammonite" % "0.8.4" cross CrossVersion.full

libraryDependencies += "com.lihaoyi" %% "acyclic" % "0.1.7" % "provided"
autoCompilerPlugins := true
addCompilerPlugin("com.lihaoyi" %% "acyclic" % "0.1.7")
scalacOptions += "-P:acyclic:force"
