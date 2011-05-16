import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import com.snapsort.codingchallenge.Matcher

class DataAccessSpec extends Spec with ShouldMatchers {

  describe("Manufacturer matching algorithm") {

      val manufacturers = new List( "Olympus", "Sony" )
      
      it("Should find manufacturer even with prefixes/suffixes") {
	  Matcher.findManufacturer("Mady by OLYMPUS Gmbh") should equal(Some("Olympus"))	  
      }

      it("Should reject if not a valid manufacturer") {
	  Matcher.findManufacturer("Mady by HELLO KITTY Gmbh") should equal(None)	  
      }

      it("Should reject if ambiguous") {
	  Matcher.findManufacturer("Mady by Sony Olympus Gmbh") should equal(None)	  
      }
  }

  describe("Products file parser") {

      val products = DataAccess.getProducts("productsTest.txt")
      
      it("should parse listings file") {
	products.length should equal (2)
	products(0).description should equal ("Sony_Cyber-shot_DSC-W310")
	products(0).manufacturer should equal ("Sony")
	products(0).model should equal ("DSC-W310")
	products(0).family should equal ("Cyber-shot")
      }
  }
}