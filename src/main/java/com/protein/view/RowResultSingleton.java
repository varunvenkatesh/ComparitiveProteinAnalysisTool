package com.protein.view;

import java.util.Observable;

import com.protein.model.Protein;

public class RowResultSingleton extends Observable {

	private static RowResultSingleton instance = null;

	private Protein protein1;
	private Protein protein2;
	private Protein lastUpdatedProtein;

	protected RowResultSingleton() {
		// Exists only to defeat instantiation.
	}

	public static RowResultSingleton getInstance() {
		if (instance == null) {
			instance = new RowResultSingleton();
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

		setChanged();
		notifyObservers();
	}

	public Protein getProtein1() {
		return protein1;
	}

	public Protein getProtein2() {
		return protein2;
	}
}
