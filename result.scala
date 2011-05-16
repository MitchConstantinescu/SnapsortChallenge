package com.snapsort.codingchallenge

import com.twitter.json.JsonSerializable
import com.twitter.json.Json

class ResultItem( productNamec : String, listingsc : Array[Listing] ) extends JsonSerializable {
    val product_name : String = productNamec
    val listings : Array[Listing] = listingsc

    def toJson() : String = {
    	val map = Map[String,Any]()
		.updated("product_name", product_name)
		.updated("listings", listings)

	Json.build(map).toString
    }    
}