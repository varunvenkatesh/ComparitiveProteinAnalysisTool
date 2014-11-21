package com.protein.model;

public class SelectedProtein {

	private Protein protein;
	private String selectedName;

	public SelectedProtein(Protein protein, String selectedName) {
		this.protein = protein;
		this.selectedName = selectedName;
	}

	public String getSelectedName() {
		return selectedName;
	}

	public Protein getProtein() {
		return protein;
	}

}
