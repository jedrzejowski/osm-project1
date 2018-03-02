package dwa.adamy.ui;

import dwa.adamy.db.DataRow;
import dwa.adamy.db.Patient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PatientTable extends JTable {
    private PatientTableModel model;
    private ArrayList<DataRow> list;
    private ListSelectionModel cellSelectionModel;

    public PatientTable() {
        super();

        list = new ArrayList<>();

        model = new PatientTableModel();
        model.setList(list);
        setModel(model);

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void addDataRow(DataRow dataRow) {
        list.add(dataRow);
        fireTableDataChanged(false);
    }

    public void removeDataRow(DataRow dataRow) {
        list.remove(dataRow);
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

        return list.get(i);
    }


    private static class PatientTableModel extends AbstractTableModel {

        final static String[] ColumnNames = {
                "Imię i Nazwisko",
                "Płęć",
                "PESEL",
                "Ubezpieczenie",
                "Badanie"
        };

        List<DataRow> list;

        public List<DataRow> getList() {
            return list;
        }

        private void setList(List<DataRow> list) {
            this.list = list;
        }

        @Override
        public String getColumnName(int i) {
            return ColumnNames[i];
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return ColumnNames.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            DataRow dataRow = list.get(row);

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
                    return "Badanie";

                default:
                    return "null";
            }

        }
    }
}