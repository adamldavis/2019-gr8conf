/** Copyright 2015-2019, Adam L. Davis */
package com.adamldavis.chem

import groovy.transform.CompileStatic

/**
 * Entry point for Chemistry DSL.
 *
 * @author adamldavis
 */
@CompileStatic
class Chemistry {

	/** Calls given closure using Chemistry as delegate. */
	static void exec(@DelegatesTo(Chemistry) Closure block) {
		block.delegate = new Chemistry()
		block()
	}

	static void calc(@DelegatesTo(Chemistry) Closure block) { exec(block) }

	/** Creates either a Compound (or Element if only one) based on arbitrary text. */
	def propertyMissing(String name) {
		def comp = new Compound(name)
		(comp.elements.size() == 1 && comp.elements.values()[0]==1) ? comp.elements.keySet()[0] : comp
	}

}
