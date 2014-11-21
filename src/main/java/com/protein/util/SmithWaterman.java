package com.protein.util;

import java.util.Stack;

//Harry Hull
//Implements the following Smith-Waterman algorithm http://en.wikipedia.org/wiki/Smith_waterman

//	Affine Gap algorithm taken from:
//http://en.wikipedia.org/wiki/Gap_penalty#Affine_gap_penalty
//	gap = o + (l-1)*e;
//	o:	gap opening penalty  (o < 0)
//	l:	length of the gap
//	e:	gap extension penalty (o < e < 0)

public class SmithWaterman {
	private final String one, two;
	private final int matrix[][];
	private int gap;
	private final int match;
	private final int o;
	private int l;
	private final int e;
	private String coverageMap1;
	private String coverageMap2;

	public SmithWaterman(String one, String two) {
		this.one = "-" + one.toLowerCase();
		this.two = "-" + two.toLowerCase();
		this.match = 2;

		// Define affine gap starting values
		o = -2;
		l = 0;
		e = -1;

		// initialize matrix to 0
		matrix = new int[one.length() + 1][two.length() + 1];
		for (int i = 0; i < one.length(); i++)
			for (int j = 0; j < two.length(); j++)
				matrix[i][j] = 0;

	}

	// returns the alignment score
	public int computeSmithWaterman() {
		for (int i = 0; i < one.length(); i++) {
			for (int j = 0; j < two.length(); j++) {
				gap = o + (l - 1) * e;
				if (i != 0 && j != 0) {
					if (one.charAt(i) == two.charAt(j)) {
						// match
						// reset l
						l = 0;
						matrix[i][j] = Math.max(
								0,
								Math.max(matrix[i - 1][j - 1] + match,
										Math.max(matrix[i - 1][j] + gap, matrix[i][j - 1] + gap)));
					} else {
						// gap
						l++;
						matrix[i][j] = Math.max(
								0,
								Math.max(matrix[i - 1][j - 1] + gap,
										Math.max(matrix[i - 1][j] + gap, matrix[i][j - 1] + gap)));
					}
				}
			}
		}

		// find the highest value
		int longest = 0;
		int iL = 0, jL = 0;
		for (int i = 0; i < one.length(); i++) {
			for (int j = 0; j < two.length(); j++) {
				if (matrix[i][j] > longest) {
					longest = matrix[i][j];
					iL = i;
					jL = j;
				}
			}
		}

		// Backtrack to reconstruct the path
		int i = iL;
		int j = jL;
		Stack<String> actions = new Stack<String>();

		while (i != 0 && j != 0) {
			// diag case
			if (Math.max(matrix[i - 1][j - 1], Math.max(matrix[i - 1][j], matrix[i][j - 1])) == matrix[i - 1][j - 1]) {
				actions.push("align");
				i = i - 1;
				j = j - 1;
				// left case
			} else if (Math.max(matrix[i - 1][j - 1], Math.max(matrix[i - 1][j], matrix[i][j - 1])) == matrix[i][j - 1]) {
				actions.push("insert");
				j = j - 1;
				// up case
			} else {
				actions.push("delete");
				i = i - 1;
			}
		}

		String alignOne = new String();
		String alignTwo = new String();

		Stack<String> backActions = (Stack<String>) actions.clone();
		for (int z = 0; z < one.length(); z++) {
			alignOne = alignOne + one.charAt(z);
			if (!actions.empty()) {
				String curAction = actions.pop();
				// System.out.println(curAction);
				if (curAction.equals("insert")) {
					alignOne = alignOne + "-";
					while (actions.peek().equals("insert")) {
						alignOne = alignOne + "-";
						actions.pop();
					}
				}
			}
		}

		for (int z = 0; z < two.length(); z++) {
			alignTwo = alignTwo + two.charAt(z);
			if (!backActions.empty()) {
				String curAction = backActions.pop();
				if (curAction.equals("delete")) {
					alignTwo = alignTwo + "-";
					while (backActions.peek().equals("delete")) {
						alignTwo = alignTwo + "-";
						backActions.pop();
					}
				}
			}
		}

		// print alignment
		System.out.println(alignOne + "\n" + alignTwo);
		coverageMap1 = alignOne;
		coverageMap2 = alignTwo;
		return longest;
	}

	public String getCoverageMap1() {
		return coverageMap1;
	}

	public String getCoverageMap2() {
		return coverageMap2;
	}

	public void printMatrix() {
		System.out.println(getMatrix());
	}

	public String getMatrix() {
		String matrixString = "";
		for (int i = 0; i < one.length(); i++) {
			if (i == 0) {
				for (int z = 0; z < two.length(); z++) {
					if (z == 0)
						matrixString += "   ";
					matrixString += two.charAt(z) + "  ";

					if (z == two.length() - 1)
						matrixString += "\n";
				}
			}

			for (int j = 0; j < two.length(); j++) {
				if (j == 0) {
					matrixString += one.charAt(i) + "  ";
				}
				matrixString += matrix[i][j] + "  ";
			}
			matrixString += "\n";
		}
		matrixString += "\n";
		return matrixString;
	}

	public double getAlignmentScore() {
		if (one.length() < two.length()) {
			return (computeSmithWaterman() / (float) two.length()) / 2.0;
		} else {
			return computeSmithWaterman() / (float) one.length() / 2.0;
		}
	}

	public static void main(String[] args) {
		// DNA sequence Test:

		String bp1 = "TGRGHTNRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR";
		String bp2 = "TGRGHTNRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR";
		SmithWaterman sw = new SmithWaterman(bp1, bp2);

		double alignmentScore = sw.getAlignmentScore();

		System.out.println("Alignment score: " + alignmentScore);

		// sw = new SmithWaterman("gcgcgtgc", "gcaagtgca");
		// System.out.println("Alignment Score: " + sw.computeSmithWaterman());
		// System.out.println(sw.getMatrix());
	}
}