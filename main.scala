import com.snapsort.codingchallenge._

val products = DataAccess.getProducts("products.txt")
val listings = DataAccess.getListings("listings.txt")

val productDictionary = new ProductDictionary(products.toList)

val classifiedListings = listings.groupBy( l => Matcher.findProduct(productDictionary, l) )

DataAccess.export( classifiedListings )


