package com.snapsort.codingchallenge

class ProductDictionary(products: List[Product]) {
    val manufacturers = products.
				map(p=>p.manufacturer.toUpperCase).
				distinct.
				sortWith( (m1,m2) => m1 < m2)
    def getManufacturers() : List[String] = manufacturers

    val models = products.
			groupBy(product => product.manufacturer.toUpperCase).
			mapValues(values => values.map(product=>product.model).distinct.sortWith( (m1,m2) => m1 < m2))
    def getModels(manufacturer: String) : List[String] = models(manufacturer.toUpperCase)

    val families = products.
			filter(product=>product.family != null).
			groupBy(product => product.manufacturer.toUpperCase).
			mapValues(values => values.map(product=>product.family).distinct.sortWith( (m1,m2) => m1 < m2))
    def getFamilies(manufacturer: String) : List[String] = 
	if (families.contains(manufacturer.toUpperCase)) families(manufacturer.toUpperCase) else List()

    def productsPerModelAndManufacturer = products.
					groupBy( product => (product.manufacturer.toUpperCase, product.model.toUpperCase) )
    def getProducts(manufacturer: String, model: String) = productsPerModelAndManufacturer( (manufacturer.toUpperCase, model.toUpperCase) )
}