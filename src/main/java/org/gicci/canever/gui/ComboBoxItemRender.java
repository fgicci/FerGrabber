package org.gicci.canever.gui;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

@SuppressWarnings("serial")
public class ComboBoxItemRender extends BasicComboBoxRenderer {

	public Component getListCellRendererComponent(@SuppressWarnings("rawtypes") JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
            ComboBoxItem item = (ComboBoxItem) value;
            setText(item.toString().toUpperCase());
        }

        if (index == -1) {
        	ComboBoxItem item = (ComboBoxItem) value;
            setText(item.toString().toUpperCase());
        }

        return this;
    }
}
