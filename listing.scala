package com.snapsort.codingchallenge

import com.twitter.json.JsonSerializable
import com.twitter.json.Json

class Listing(titlec : String, manufacturerc : String, currencyc : String, pricec : Double) extends JsonSerializable {

  var title : String = titlec
  var manufacturer: String = manufacturerc
  var currency : String = currencyc
  var price : Double = pricec

  def toJson() : String = {
	var map = Map[String,Any]()
		.updated("title", title )
		.updated("manufacturer", manufacturer)
		.updated("currency", currency)
		.updated("price", price)
	Json.build(map).toString
  }
}