cls
call scalac extensions.scala json.scala
call scalac DataAccess.scala Listing.scala product.scala Matcher.scala ProductDictionary.scala Result.scala ProgressDisplay.scala -deprecation
call scala main.scala