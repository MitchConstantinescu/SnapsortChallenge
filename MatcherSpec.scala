import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

import com.snapsort.codingchallenge.Matcher
import com.snapsort.codingchallenge.Product
import com.snapsort.codingchallenge.Listing
import com.snapsort.codingchallenge.ProductDictionary

class MatcherSpec extends Spec with ShouldMatchers {

  describe("Manufacturer matching algorithm") {

      val manufacturers = List( "Olympus", "Sony" )
      
      it("Should find manufacturer even with prefixes/suffixes") {
	  Matcher.findManufacturer("Mady by OLYMPUS Gmbh", manufacturers) should equal(Some("Olympus"))	  
      }

      it("Should reject if not a valid manufacturer") {
	  Matcher.findManufacturer("Mady by HELLO KITTY Gmbh", manufacturers) should equal(None)	  
      }

      it("Should reject if ambiguous") {
	  Matcher.findManufacturer("Mady by Sony Olympus Gmbh", manufacturers) should equal(None)	  
      }
  }

  describe("Model and fammily matching algorithm") {
      val models = List("X-100", "Y-75")

      it ("Should find codes even if the punctuation is misspelled") {
          Matcher.findCode("Nikon X100 black", models) should equal(Some("X-100"))
      }

      it ("Should find codes even at the end of the string") {
          Matcher.findCode("Nikon black X100", models) should equal(Some("X-100"))
      }

      it ("Should reject duplicate codes") {
          Matcher.findCode("Nikon X100 or maybe Y-75 black", models) should equal(None)
      }

      it ("Should reject if no code was found") {
          Matcher.findCode("Hello kitty", models) should equal(None)
      }   
      it ("Should filter out included families - e.g. IXUS and Digital IXUS") {
      	  Matcher.findCode("Something Digital IXUS bleurch", List("Digital IXUS", "IXUS") ) should equal(Some("Digital IXUS"))
      }
  }

  describe("Product dictionary") {
      val p1 = new Product( "Canon_1", "Canon", null, "C1")
      val p2 = new Product( "Canon_2", "Canon", "2", "C2")
      val p3 = new Product( "Canon_3", "Canon", "3", "C3")
      val p4 = new Product( "Olympus_1", "Olympus", "OF1", "OM")
      val p5 = new Product( "Olympus_1", "Olympus", "OF2", "OM")
      val dictionary = new ProductDictionary( List(p1, p2, p3, p4, p5) )

      it ("Gathers all manufacturer names from products and removes duplicates") {
          dictionary.getManufacturers() should equal( List("CANON", "OLYMPUS") )
      }
      it ("Gathers all model names for a manufacturer") {
      	  dictionary.getModels("canon") should equal( List("C1", "C2", "C3") )
      }
      it ("Gathers all family names for a manufacturer") {
	  dictionary.getFamilies("canon") should equal( List("2", "3" ) )
      }
      it ("Finds all products for a model name") {
	  dictionary.getProducts("olympus", "om") should equal( List(p4, p5) )
      }
  }

  describe("Product identification algorithm") {
      val p1 = new Product( "Canon_1", "Canon", null, "C1")
      val p2 = new Product( "Canon_2", "Canon", "second", "C2")
      val p3 = new Product( "Canon_3", "Canon", "third", "C3")
      val p4 = new Product( "Olympus_1", "Olympus", "OF1", "OM")
      val p5 = new Product( "Olympus_2", "Olympus", "OF2", "OM")
      val dictionary = new ProductDictionary( List(p1, p2, p3, p4, p5) )

      it ("Should reject if there is no manufacturer") {
	Matcher.findProduct( dictionary, new Listing( "Hello Kitty", "XXXz", "CAD", 5) ) should equal(None)
      }

      it ("Should reject if there is no model") {
	Matcher.findProduct( dictionary, new Listing( "Hello Kitty", "Olympus", "CAD", 5) ) should equal(None)
      }
      it ("Should accept if there is only one model, no family and model gives an unique product") {
	Matcher.findProduct( dictionary, new Listing( "canon C1", "canon", "CAD", 5 )) should equal(Some(p1))
      }
      it ("Should accept if there is only one model, only one family and they match") {
	Matcher.findProduct( dictionary, new Listing( "canon C3 third", "canon", "CAD", 5  )) should equal(Some(p3))
      }
      it ("Should reject if there is only one model, only one family but they don't match") {
	Matcher.findProduct( dictionary, new Listing( "canon C2 third", "canon", "CAD", 5  )) should equal(None)
      }
      it ("Should reject if there is only one model, no family and model by itself is ambiguous") {
	Matcher.findProduct( dictionary, new Listing( "Olympus OM", "Olympus", "CAD", 5) ) should equal(None)
      }
  }
}