package ui;

import db.Patient;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.List;

public class PatientTable extends JTable {
    PatientTableModel model;
    private ArrayList<Patient> list;

    public PatientTable() {
        super();

        list = new ArrayList<>();

        model = new PatientTableModel();
        model.setList(list);
        setModel(model);
    }


    private static class PatientTableModel extends AbstractTableModel {

        List<Patient> list;

        public List<Patient> getList() {
            return list;
        }

        public void setList(List<Patient> list) {
            this.list = list;
        }

        @Override
        public String getColumnName(int i) {

            switch (i) {
                case 0:
                    return "Imię i Nazwisko";
                case 1:
                    return "Płęć";
                case 2:
                    return "PESEL";
                case 3:
                    return "Ubezpieczenie";
                case 4:
                    return "Badanie";
                default:
                    return "null";
            }
        }

        @Override
        public int getRowCount() {
            return list.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public Object getValueAt(int row, int col) {
            Patient patient = list.get(row);

            switch (col) {
                case 0:
                    return patient.getName1() + " " + patient.getName2();
                case 1:
                    switch (patient.getSex()) {
                        case FEMALE:
                            return "Kobieta";

                        case MALE:
                            return "Mężczyzna";
                    }

                case 2:
                    return patient.getPesel();
                case 3:
                    return "Ubezpieczenie";
                case 4:
                    return "Badanie";

            }

            return "null";
        }
    }
}