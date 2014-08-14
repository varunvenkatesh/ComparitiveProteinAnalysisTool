package com.protein.xml.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.protein.model.Protein;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class XMLProteinReaderTest {

	private XMLProteinReader xmlProteinReader;

	@Before
	public void setup() {
		xmlProteinReader = new XMLProteinReader();
	}

	@Test
	public void read() {
		URL file = getClass().getResource("/proteinDatabase/testProtein.xml");
		// Turn the resource into a File object
		File dir = null;
		try {
			dir = new File(file.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Protein protein = xmlProteinReader.read(dir);
		assertThat(protein.getNames(), is(not(empty())));
		List<String> names = protein.getNames();
		assertThat(names.get(0), is("5'-NT"));
		assertThat(names.get(1), is("CD73 antigen"));
		assertThat(names.get(2), is("Ecto-5'-nucleotidase"));
		assertThat(protein.getGeneralFunction(), is("Involved in hydrolase activity"));
		assertThat(protein.getName(), is("5'-NT"));
		assertThat(
				protein.getSpecificFunction(),
				is("Hydrolyzes extracellular nucleotides into membrane permeable nucleosides."));
		assertThat(
				protein.getAminoAcidSequence(),
				is("MCPRAARAPATLLLALGAVLWPAAGAWELTILHTNDVHSRLEQTSEDSSKCVNASRCMGGVARLFTKVQQIRRAEPN"
				+ "VLLLDAGDQYQGTIWFTVYKGAEVAHFMNALRYDAMALGNHEFDNGVEGLIEPLLKEAKFPILSANIKAKGPLASQISGLY"
				+ "LPYKVLPVGDEVVGIVGYTSKETPFLSNPGTNLVFEDEITALQPEVDKLKTLNVNKIIALGHSGFEMDKLIAQKVRGVDVVV"
				+ "GGHSNTFLYTGNPPSKEVPAGKYPFIVTSDDGRKVPVVQAYAFGKYLGYLKIEFDERGNVISSHGNPILLNSSIPEDPSIKA"
				+ "DINKWRIKLDNYSTQELGKTIVYLDGSSQSCRFRECNMGNLICDAMINNNLRHTDEMFWNHVSMCILNGGGIRSPIDERNNG"
				+ "TITWENLAAVLPFGGTFDLVQLKGSTLKKAFEHSVHRYGQSTGEFLQVGGIHVVYDLSRKPGDRVVKLDVLCTKCRVPSYDP"
				+ "LKMDEVYKVILPNFLANGGDGFQMIKDELLRHDSGDQDINVVSTYISKMKVIYPAVEGRIKFSTGSHCHGSFSLIFLSLWAVIFVLYQ"));
	}

}
// test for all the variable in the protein object