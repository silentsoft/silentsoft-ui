package org.silentsoft.ui.component.table;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 3873366827880854918L;

	public CustomTableModel (Object [] [] data, Object [] columns) { 
        super (data, columns); 
    }
	
	public boolean isCellEditable (int row, int column) { 
        return false; 
    }
}