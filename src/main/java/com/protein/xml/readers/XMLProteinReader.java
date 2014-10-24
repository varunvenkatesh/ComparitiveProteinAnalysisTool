package com.protein.xml.readers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.protein.model.Protein;

public class XMLProteinReader {

	private static final String PROTEIN_SEQUENCE = "protein_sequence";
	private static final String PROTEIN_PROPERTIES = "protein_properties";
	private static final String SYNOYNMS = "synonyms";
	private static final String GENERAL_FUNCTION = "general_function";
	private static final String SPECIFIC_FUNCTION = "specific_function";

	public Protein read(File xmlFile) {
		Protein protein = null;
		Builder builder = new Builder();
		try {
			Document doc = builder.build(xmlFile);
			Element root = doc.getRootElement();
			protein = getProtein(root);
		} catch (ValidityException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return protein;
	}

	private String getValue(Element element) {
		if (element == null) {
			return null;
		}
		String value = element.getValue().trim();
		return value;
	}

	private Protein getProtein(Element root) {

		Element namesElement = root.getFirstChildElement(SYNOYNMS);
		Elements synonymsChildElements = namesElement.getChildElements();
		Element proteinPropertiesElement = root.getFirstChildElement(PROTEIN_PROPERTIES);
		String aminoAcidSequence = getValue(proteinPropertiesElement.getFirstChildElement(PROTEIN_SEQUENCE));

		// clean up amino acid string. removes base pair and new line characters
		try {
			aminoAcidSequence = aminoAcidSequence
					.substring(aminoAcidSequence.indexOf("\n"), aminoAcidSequence.length()).replaceAll("\n", "");
		} catch (StringIndexOutOfBoundsException e) {

		}

		String generalFunction = getValue(root.getFirstChildElement(GENERAL_FUNCTION));
		String specificFunction = getValue(root.getFirstChildElement(SPECIFIC_FUNCTION));

		// set protein sequence
		// protein.setAminoAcidSequence(proteinSequenceElement.getValue());
		ArrayList<String> names = new ArrayList<String>();
		// example of looping through child elements
		for (int i = 0; i < synonymsChildElements.size(); i++) {
			// go_class element
			String name = getValue(synonymsChildElements.get(i));
			if (name != null) {
				names.add(name);
			}

		}

		Protein protein = null;
		try {
			protein = new Protein(aminoAcidSequence, generalFunction, specificFunction, names);
		} catch (IllegalArgumentException e) {
			// System.out.println("Warning: This protein has no name");
		}

		return protein;
	}
}
