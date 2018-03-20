package dwa.adamy.ui;

import dwa.adamy.db.DataRow;
import dwa.adamy.db.Database;
import dwa.adamy.db.Patient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class PatientTable extends JTable {
    private final PatientTableModel model;
    private final Database database;

    public PatientTable(Database database) {
        super();
        this.database = database;

        model = new PatientTableModel();

        setModel(model);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addDataRow(DataRow dataRow) {
        database.add(dataRow);
        fireTableDataChanged(false);
    }

    public void fireTableDataChanged(boolean saveSeletion) {

        int rowI = -1;
        if (saveSeletion) rowI = getSelectedRow();

        model.fireTableDataChanged();

        if (saveSeletion) getSelectionModel().addSelectionInterval(rowI, rowI);
    }

    public DataRow getSelectedDataRow() {
        int i = getSelectedRow();

        if (i < 0) return null;

        return database.get(i);
    }

    private class PatientTableModel extends AbstractTableModel {

        final String[] ColumnNames = {
                "Imię i Nazwisko",
                "Płęć",
                "PESEL",
                "Ubezpieczenie",
                "Badanie"
        };

        @Override
        public String getColumnName(int i) {
            return ColumnNames[i];
        }

        @Override
        public int getRowCount() {
            return database.size();
        }

        @Override
        public int getColumnCount() {
            return ColumnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            DataRow dataRow = database.get(row);

            switch (col) {
                case 0:
                    return dataRow.getPatient().getFullName();
                case 1:
                    return Patient.getSexStringMap().get(dataRow.getPatient().getSex());
                case 2:
                    return dataRow.getPatient().getPesel();
                case 3:
                    return Patient.getInsuranceTypeStringMap().get(dataRow.getPatient().getInsuranceType());
                case 4:
                    return dataRow.getExamination().getDate() != null;

                default:
                    return "null";
            }

        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (columnIndex == 4) return Boolean.class;
            return String.class;
        }
    }
}