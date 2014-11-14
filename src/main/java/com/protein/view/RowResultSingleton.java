package com.protein.view;

import java.util.Observable;

import com.protein.model.SelectedProtein;

public class RowResultSingleton extends Observable {

	private static RowResultSingleton instance = null;

	private SelectedProtein protein1;
	private SelectedProtein protein2;
	private SelectedProtein lastUpdatedProtein;

	protected RowResultSingleton() {
		// Exists only to defeat instantiation.
	}

	public static RowResultSingleton getInstance() {
		if (instance == null) {
			instance = new RowResultSingleton();
		}
		return instance;
	}

	public void updateProtein(SelectedProtein p) {

		if (lastUpdatedProtein == null) {
			protein1 = p;
			lastUpdatedProtein = protein1;
		}

		else if (protein1 == lastUpdatedProtein) {
			protein2 = p;
			lastUpdatedProtein = protein2;

		} else {
			protein1 = p;
			lastUpdatedProtein = protein1;
		}

		setChanged();
		notifyObservers();
	}

	public SelectedProtein getProtein1() {
		return protein1;
	}

	public SelectedProtein getProtein2() {
		return protein2;
	}
}
