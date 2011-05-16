package com.snapsort.codingchallenge

import scala.util.matching.Regex

object Matcher
{
    def findProduct( dictionary : ProductDictionary, listing : Listing ) : Option[Product] = 
    {
	val optManufacturer = findManufacturer( listing.manufacturer, dictionary.getManufacturers )
	if (optManufacturer.isEmpty)
	    return None
	val manufacturer = optManufacturer.get
	val optModel = findCode( listing.title, dictionary.getModels(manufacturer) )
	if (optModel.isEmpty)
	    return None
	val model = optModel.get
	val optFamily = findCode(listing.title, dictionary.getFamilies(manufacturer) )
	val products = dictionary.getProducts(manufacturer, model)
        			 .filter( product => optFamily.isEmpty || optFamily.get == product.family )
	if (products.length != 1)
	    return None
	return Some(products(0))
    }

    def findManufacturer(manufacturer: String, alternatives : List[String]) : Option[String] =
    {
	val possibleAlternatives = alternatives.filter(alternative => canMatchManufacturer(manufacturer, alternative));
	if (possibleAlternatives.length == 1) Some(possibleAlternatives(0)) else None
    }

    def canMatchManufacturer( manufacturer : String, possibleManufacturer : String ) : Boolean =
    {
        manufacturer.toUpperCase.indexOf(possibleManufacturer.toUpperCase) != -1
    }

    def findCode(code: String, alternatives : List[String]) : Option[String] =
    {
	val possibleAlternatives = alternatives.filter(alternative => canMatchCode(code, alternative));

	val possibleAlternatives_removeInclusions = possibleAlternatives.filter( 
		alternative => !possibleAlternatives.exists( 
				other => alternative != other &&
				         canMatchCode(other, alternative) )
	)

	if (possibleAlternatives_removeInclusions.length == 1) Some(possibleAlternatives_removeInclusions(0)) else None
    }

    def canMatchCode( code : String, possibleCode : String ) : Boolean = 
    {
        cleanupCode(code).indexOf(cleanupCode(possibleCode).toUpperCase) != -1
    }

    def cleanupCode( code : String ) : String =
    {
	val code_noSpecialChars = new Regex("[^a-zA-Z0-9]+").replaceAllIn(code.toUpperCase, " ") 
	val code_separatorOnBoundary = new Regex("([a-zA-Z])([0-9])").replaceAllIn(code_noSpecialChars, m => m.group(1) + " " + m.group(2)) 
	val code_separatorOnBoundary2 = new Regex("([0-9])([a-zA-Z])").replaceAllIn(code_separatorOnBoundary, m => m.group(1) + " " + m.group(2)) 
        " " + code_separatorOnBoundary2 + " "
    }
}