/** Copyright 2015, Adam L. Davis */
package com.adamldavis.chem

import groovy.transform.*

/**
 * Composition of the Compound.
 * 
 * @author adamldavis
 */
@EqualsAndHashCode
class Compound {

	final Map elements = [:]

	Compound(Map startMap, String str) {
		this.elements.putAll(startMap)
		def matcher = str =~ /([A-Z][a-z]*)([0-9]+)?/
		while (matcher.find()) add(
			new Element(matcher.group(1)), 
			(matcher.group(2) ?: 1) as Integer)
	}

	Compound(String str) {
		this([:], str)
	}

	Compound(final Map elements) {
		this.elements.putAll(elements)
	}

	/** Multiplies every number for each element in this Compound. */
	def multiply(int x) {
		def map = [:]
		elements.each { key,num -> map.put(key, num * x) }
		new Compound(map)
	}

	/** The quantity of given element in this compound. */
	def div(Element e) {
		elements[e] ?: 0
	}

	/** The percentage of Compound for given element. */
	def mod(Element e) {
		percentage(e)
	}

	def plus(Compound other) {
		def c = new Compound(this.elements)
		other.elements.each {e, n -> c.add(e, n)}
		c
	}

	/** Subtracts the given Compound from this Compound and returns a new Compound from results. */
	def minus(Compound other) {
		def c = new Compound(this.elements)
		other.elements.each {e, n -> c.remove(e, n)}
		c
	}

	/** Subtracts the given elements from this Compound. */
	def minus(Element e) {
		this - new Compound( [(e): 1] )
	}

	def and(Compound other) { plus(other) }

	def missingMethod(String name, args) {
		new Compound(this.elements, name)
	}

	String toString() {
		elements.inject("") { str, e -> str + "${e.value}${e.key} " }
	}

	void add(Element e, int num) {
		if (elements[e]) elements[e] += num
		else elements[e] = num
	}

	/** Removes the given number for given Element from this Compound. Throws exception if not found. */
	void remove(Element e, int num) {
		if (elements[e]) elements[e] -= num
		else throw new IllegalArgumentException("$e not found")
		if (0 <= elements[e]) elements.remove(e)
	}
	
	/** The percentage of Compound for given element by weight. */
	double percentage(Element element) {
		(element.weight * elements[element]) / getWeight()
	}

	double getWeight() {
		elements.keySet().inject(0.0d) { sum, key ->
			sum + (key.weight * elements[key])
		} as double
	}
	
}

