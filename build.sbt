inThisBuild(
  List(
    organization := "com.michaelpollmeier",
    homepage := Some(url("https://github.com/mpollmeier/sbt-ci-release")),
    licenses := Seq(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "olafurpg",
        "Ólafur Páll Geirsson",
        "olafurpg@gmail.com",
        url("https://geirsson.com")
      ),
      Developer(
        "mpollmeier",
        "Michael Pollmeier",
        "michael@michaelpollmeier.com",
        url("https://michaelpollmeier.com")
      )
    ),
    resolvers += Resolver.sonatypeRepo("releases"),
    scalaVersion := "2.12.6"
  )
)

skip in publish := true // don't publish the root project

lazy val plugin = project
  .settings(
    moduleName := "sbt-ci-release",
    sbtPlugin := true,
    scriptedBufferLog := false,
    scriptedLaunchOpts += s"-Dplugin.version=${version.value}",
    addSbtPlugin("com.dwijnand" % "sbt-dynver" % "3.0.0"),
    addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "1.0.0"),
    addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3"),
    addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.1")
  )
