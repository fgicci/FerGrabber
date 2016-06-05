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
	
	public int getColumnIndex(String name) {
		for (int index = 0; index < this.columnNames.length; index++) {
			if (columnNames[index].equals(name)) return index;
		}
        return 0;
    }

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
    }

}
