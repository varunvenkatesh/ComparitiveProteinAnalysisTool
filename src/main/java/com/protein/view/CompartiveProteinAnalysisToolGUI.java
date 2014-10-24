package com.protein.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.protein.model.Protein;
import com.protein.xml.readers.XMLProteinReader;

public class CompartiveProteinAnalysisToolGUI extends JFrame {

	private final JTextField minimumLength = new JTextField();

	public void start() {
		List<Protein> proteins = new ArrayList<Protein>();
		XMLProteinReader xmlProteinReader = new XMLProteinReader();
		URL directoryURL = getClass().getResource("/proteinDatabase/hmdb_proteins");
		File directory = null;
		try {
			directory = new File(directoryURL.toURI());

			// get all the files from a directory
			File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					// read file
					Protein protein = xmlProteinReader.read(file);
					if (protein != null) {
						proteins.add(protein);
					}
				}
			}
			System.out.println("finished reading files");
			CompartiveProteinAnalysisToolGUI frame = new CompartiveProteinAnalysisToolGUI();
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			frame.getContentPane().setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 0;
			c.insets = new Insets(0, 150, 0, 150);
			frame.getContentPane().add(new AutoSuggest(proteins, "Input first protein"), c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 1;
			c.gridy = 0;
			c.insets = new Insets(0, 150, 0, 150);
			frame.getContentPane().add(new AutoSuggest(proteins, "Input second protein"), c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			c.insets = new Insets(0, 450, 0, 450);
			c.gridwidth = 2;
			c.gridx = 0;
			c.gridy = 1;
			frame.getContentPane().add(minimumLength, c);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 0.5;
			c.gridx = 0;
			c.gridy = 2;
			c.insets = new Insets(0, 0, 0, 0);
			frame.getContentPane().add(new Table(), c);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		CompartiveProteinAnalysisToolGUI compartiveProteinAnalysisToolGUI = new CompartiveProteinAnalysisToolGUI();
		compartiveProteinAnalysisToolGUI.start();
	}
}
