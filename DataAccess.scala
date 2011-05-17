package com.snapsort.codingchallenge

import java.io._
import com.twitter.json.Json

object DataAccess {

    def getListings(fileName : String) : Array[Listing] =
    {
        val lines = scala.io.Source.fromFile(fileName)("UTF-8").getLines
	lines
	    .map( line => parseListing(line) )
	    .filter( parsed => parsed.isDefined )
	    .map( line => line.get )
            .toArray
    }

    def getProducts(fileName : String) : Array[Product] =
    {
        val lines = scala.io.Source.fromFile(fileName)("UTF-8").getLines
	lines
	    .map( line => parseProduct(line) )
	    .filter( parsed => parsed != null )
            .toArray
    }

    def parseListing(line : String ) : Option[Listing] =
    {
	ProgressDisplay.tick()

	val parsedLine = scala.util.parsing.json.JSON.parseFull(line)
	return parsedLine.map(pl => {
                val typedLine = pl.asInstanceOf[Map[String,Any]]
   		def title = typedLine("title").toString
		def manufacturer = typedLine("manufacturer").toString
        	def currency = typedLine("currency").toString
		def price = typedLine("price").toString.toDouble
   		new Listing( title, manufacturer, currency, price )
 	    })
    }

    def parseProduct(line : String ) : Product =
    {
	val parsedLine = scala.util.parsing.json.JSON.parseFull(line)
	parsedLine match {
	    case None => return null
	    case _ => {
                val typedLine = parsedLine.get.asInstanceOf[Map[String,Any]]
   		def name = typedLine("product_name").toString
		def manufacturer = typedLine("manufacturer").toString
        	def family = if (typedLine.contains("family")) typedLine("family").toString else null
		def model = typedLine("model").toString
   		return new Product( name, manufacturer, family, model )
 	    }
	}
    }
	
    def export( listings : Map[Option[Product], Array[Listing]])
    {
	val resultItems = listings.keys.
				toList.
				filter( p => !p.isEmpty ).
	    			sortBy( p => p.get.name ).
	    			map( p => new ResultItem( p.get.name, listings(p).sortBy( l => l.title ).toArray ) )
	
	printToFile(new File("results.txt"))(p => {
  		resultItems.foreach(ri => p.println( Json.build(ri) ) )
	})

    }

    def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
        val p = new java.io.PrintWriter(f)
        try { op(p) } finally { p.close() }
    }
}

