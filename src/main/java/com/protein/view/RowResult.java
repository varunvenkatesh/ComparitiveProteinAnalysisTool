package com.protein.view;

import com.protein.model.Protein;

public class RowResult {

	private static RowResult instance = null;

	private Protein protein1;
	private Protein protein2;
	private Protein lastUpdatedProtein;

	protected RowResult() {
		// Exists only to defeat instantiation.
	}

	public static RowResult getInstance() {
		if (instance == null) {
			instance = new RowResult();
		}
		return instance;
	}

	public void updateProtein(Protein p) {
		if (protein1 == lastUpdatedProtein) {
			protein2 = p;
			lastUpdatedProtein = protein2;

		} else {
			protein1 = p;
			lastUpdatedProtein = protein1;
		}
		System.out.println(lastUpdatedProtein.getName());
	}

	public Protein getProtein1() {
		return protein1;
	}

	public Protein getProtein2() {
		return protein2;
	}
}
