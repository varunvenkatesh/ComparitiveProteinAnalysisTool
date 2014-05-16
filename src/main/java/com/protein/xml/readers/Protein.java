package com.protein.xml.readers;

import java.util.ArrayList;
import java.util.Collections;
/*
 * Author: Varun Venkatesh
 * Summary: This object stores information about a given protein
 */
import java.util.List;
import java.util.Collection;

public class Protein {

	private String name;
	private String aminoAcidSequence;
	private String generalFunction;
	private String specificFunction;
	private List<String> names = new ArrayList<String>();

	public Protein(String aminoAcidSequence, String generalFunction,
			String specificFunction, List<String> names) {
		this.aminoAcidSequence = aminoAcidSequence;
		this.generalFunction = generalFunction;
		this.specificFunction = specificFunction;
		updateNames(names);
	}

	private void updateNames(List<String> names) {
		if (names == null || names.isEmpty()) {
			throw new IllegalArgumentException(
					"Error: No name. Must have at least one name ");
		}
		Collections.sort(names);
		this.name = names.get(0);
		this.names = names;
	}

	public String getName() {
		return this.name;
	}

	public void setAminoAcidSequence(String aminoAcidSequence) {
		this.aminoAcidSequence = aminoAcidSequence;

	}

	public String getAminoAcidSequence() {
		return this.aminoAcidSequence;
	}

	public void setGeneralFunction(String generalFunction) {
		this.generalFunction = generalFunction;
	}

	public String getGeneralFunction() {
		return this.generalFunction;
	}

	public void setSpecificFunction(String specificFunction) {
		this.specificFunction = specificFunction;
	}

	public String getSpecificFunction() {
		return this.specificFunction;
	}

	public List<String> getNames() {
		return Collections.unmodifiableList(names);
	}

	// add list of synonyms and get and add functions
	// for the getter pass in an index value, return value of that array
	// location

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((aminoAcidSequence == null) ? 0 : aminoAcidSequence
						.hashCode());
		result = prime * result
				+ ((generalFunction == null) ? 0 : generalFunction.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((names == null) ? 0 : names.hashCode());
		result = prime
				* result
				+ ((specificFunction == null) ? 0 : specificFunction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Protein other = (Protein) obj;
		if (aminoAcidSequence == null) {
			if (other.aminoAcidSequence != null)
				return false;
		} else if (!aminoAcidSequence.equals(other.aminoAcidSequence))
			return false;
		if (generalFunction == null) {
			if (other.generalFunction != null)
				return false;
		} else if (!generalFunction.equals(other.generalFunction))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (names == null) {
			if (other.names != null)
				return false;
		} else if (!names.equals(other.names))
			return false;
		if (specificFunction == null) {
			if (other.specificFunction != null)
				return false;
		} else if (!specificFunction.equals(other.specificFunction))
			return false;
		return true;
	}

}
