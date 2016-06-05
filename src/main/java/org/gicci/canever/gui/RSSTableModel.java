package org.gicci.canever.gui;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class RSSTableModel extends AbstractTableModel {

	private String[] columnNames;
	private String[][] data;
	
	public RSSTableModel(String[] columnNames, String[][] data) {
		this.columnNames = columnNames;
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	public String getColumnName(int col) {
        return columnNames[col];
    }

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
    }

}
