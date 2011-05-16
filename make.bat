cls
call scalac extensions.scala json.scala
call scalac DataAccessSpec.scala DataAccess.scala Listing.scala product.scala Matcher.scala MatcherSpec.scala ProductDictionary.scala Result.scala -deprecation
call scala -cp scalatest-1.0.jar org.scalatest.tools.Runner -p . -o -s DataAccessSpec
call scala -cp scalatest-1.0.jar org.scalatest.tools.Runner -p . -o -s MatcherSpec