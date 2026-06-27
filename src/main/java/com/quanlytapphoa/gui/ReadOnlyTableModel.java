package com.quanlytapphoa.gui;

import javax.swing.table.DefaultTableModel;

final class ReadOnlyTableModel extends DefaultTableModel {
    ReadOnlyTableModel(String... columns) {
        super(columns, 0);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
