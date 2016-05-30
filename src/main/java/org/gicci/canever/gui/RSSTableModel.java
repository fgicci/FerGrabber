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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isCellEditable(int row, int col) {
		return false;
    }

}
