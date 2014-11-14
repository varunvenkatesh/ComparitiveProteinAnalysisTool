package com.protein.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.protein.model.Protein;

public class AutoSuggest extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField tf;
	private final JComboBox combo = new JComboBox();
	private final Vector<Protein> dictionary = new Vector<Protein>();

	public AutoSuggest(List<Protein> proteins, String title) {
		super(new BorderLayout());
		combo.setEditable(true);
		tf = (JTextField) combo.getEditor().getEditorComponent();
		tf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						String text = tf.getText();
						if (text.length() == 0) {
							combo.hidePopup();
							setModel(new DefaultComboBoxModel(dictionary), "");
						} else {
							DefaultComboBoxModel m = getSuggestedModel(dictionary, text);
							if (m.getSize() == 0 || hide_flag) {
								combo.hidePopup();
								hide_flag = false;
							} else {
								setModel(m, text);
								combo.showPopup();
							}
						}
					}
				});
			}

			@Override
			public void keyPressed(KeyEvent e) {
				String text = tf.getText();
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_ESCAPE) {
					hide_flag = true;
				} else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_ENTER || code == KeyEvent.VK_TAB) {
					for (int i = 0; i < dictionary.size(); i++) {
						Protein str = dictionary.elementAt(i);
						for (String protein : str.getNames()) {
							if (protein.toLowerCase().startsWith(text.toLowerCase())) {
								combo.setSelectedIndex(-1);
								tf.setText(protein);
								RowResultSingleton.getInstance().updateProtein(str);
								hide_flag = true;
								return;
							}
						}
					}
				}
			}
		});

		for (int i = 0; i < proteins.size(); i++) {
			dictionary.addElement(proteins.get(i));
		}
		setModel(new DefaultComboBoxModel(dictionary), "");
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(title));
		p.add(combo, BorderLayout.NORTH);
		add(p);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setPreferredSize(new Dimension(300, 70));
	}

	private boolean hide_flag = false;

	private void setModel(DefaultComboBoxModel mdl, String str) {
		combo.setModel(mdl);
		combo.setSelectedIndex(-1);
		tf.setText(str);
	}

	private static DefaultComboBoxModel getSuggestedModel(List<Protein> list, String text) {
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		boolean isFound = false;
		for (Protein s : list) {
			for (String name : s.getNames()) {
				if (name.toLowerCase().startsWith(text.toLowerCase())) {
					m.addElement(name);
					isFound = true;
					break;
				}
				if (isFound) {
					break;
				}
			}
		}
		return m;
	}

}