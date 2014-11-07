package com.protein.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class FixedTable {
	private String columnNames[];
	private Object rowData[][];
	private final JFrame frame = new JFrame("Fixed Column Table");
	private JScrollPane scrollPane;
	private TableModel mainModel;
	private TableModel fixedColumnModel;

	public void recreateTable(Object rowData[][], String columnNames[]) {
		this.rowData = rowData;
		this.columnNames = columnNames;

		if (frame != null && scrollPane != null) {
			frame.getContentPane().remove(scrollPane);
		}

		createFixedColumnModel();
		createTableMainModel();
		JTable fixedTable = new JTable(fixedColumnModel);
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JTable mainTable = new JTable(mainModel);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		ListSelectionModel model = fixedTable.getSelectionModel();
		mainTable.setSelectionModel(model);

		scrollPane = new JScrollPane(mainTable);
		scrollPane.revalidate();
		Dimension fixedSize = fixedTable.getPreferredSize();
		JViewport viewport = new JViewport();
		viewport.setView(fixedTable);
		viewport.setPreferredSize(fixedSize);
		viewport.setMaximumSize(fixedSize);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
		scrollPane.setRowHeaderView(viewport);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setSize(500, 150);
		frame.setVisible(true);
		frame.validate();
		frame.repaint();
	}

	public void createFixedColumnModel() {
		fixedColumnModel = new AbstractTableModel() {
			public int getColumnCount() {
				return 1;
			}

			@Override
			public String getColumnName(int column) {
				return columnNames[column];
			}

			public int getRowCount() {
				return rowData.length;
			}

			public Object getValueAt(int row, int column) {
				return rowData[row][column];
			}
		};
	}

	public void createTableMainModel() {
		mainModel = new AbstractTableModel() {
			public int getColumnCount() {
				return columnNames.length - 1;
			}

			@Override
			public String getColumnName(int column) {
				return columnNames[column + 1];
			}

			public int getRowCount() {
				return rowData.length;
			}

			public Object getValueAt(int row, int column) {
				return rowData[row][column + 1];
			}
		};
	}

	public static void main(String args[]) {
		Object rowData[][] = new Object[][] { { "Sequence Name 1", "Waterman chart 1" },
				{ "Sequence Name 2", "Waterman chart 2" } };

		String columnNames[] = new String[] { "Sequence Name", "Waterman Chart" };

		FixedTable f = new FixedTable();
		f.recreateTable(rowData, columnNames);

		columnNames = new String[] { "AA Name", "Waterman Chart" };
		f.recreateTable(rowData, columnNames);
	}

}