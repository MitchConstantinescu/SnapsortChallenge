import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import com.snapsort.codingchallenge.DataAccess

class DataAccessSpec extends Spec with ShouldMatchers {

  describe("Listings file parser") {

      val listings = DataAccess.getListings("listingsTest.txt")
      
      it("should parse listings file") {
	listings.length should equal (2)
	listings(0).title should equal ("LED Flash Macro Ring Light (48 X LED) with 6 Adapter Rings for For Canon/Sony/Nikon/Sigma Lenses")
	listings(0).manufacturer should equal ("Neewer Electronics Accessories")
	listings(0).currency should equal ("CAD")
	listings(0).price should equal (35.99)
      }
  }

  describe("Products file parser") {

      val products = DataAccess.getProducts("productsTest.txt")
      
      it("should parse listings file") {
	products.length should equal (2)
	products(0).name should equal ("Sony_Cyber-shot_DSC-W310")
	products(0).manufacturer should equal ("Sony")
	products(0).model should equal ("DSC-W310")
	products(0).family should equal ("Cyber-shot")
      }
  }
}