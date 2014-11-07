package com.protein.view;

import com.protein.model.Protein;

public class RowResult {

	private Protein protein1;
	private Protein protein2;
	private Protein lastUpdatedProtein;

	public void updateProtein(Protein p) {
		if (protein1 == lastUpdatedProtein) {
			protein2 = p;
			lastUpdatedProtein = protein2;

		} else {
			protein1 = p;
			lastUpdatedProtein = protein1;
		}
	}

	public Protein getProtein1() {
		return protein1;
	}

	public Protein getProtein2() {
		return protein2;
	}
}
