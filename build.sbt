name := "simple-login"
 
version := "1.0" 
      
lazy val `simple_login` = (project in file(".")).enablePlugins(PlayScala)

resolvers ++= Seq(
  "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases",
  "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
)
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc , ehcache , ws , specs2 % Test , guice
  , "org.json4s" %% "json4s-native" % "3.6.0"
  , "org.json4s" %% "json4s-ext" % "3.6.0"
  , "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"
  
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      