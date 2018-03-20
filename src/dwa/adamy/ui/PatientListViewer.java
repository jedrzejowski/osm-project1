package dwa.adamy.ui;

import dwa.adamy.Log;
import dwa.adamy.db.DataRow;
import dwa.adamy.db.Database;

import javax.swing.*;
import java.awt.*;

public class PatientListViewer extends JPanel {

    private JButton addBtn, rmBtn;
    private PatientTable table;
    private final Interface callback;
    private final Database database;

    public PatientListViewer(Database database, Interface callback) {
        super();
        this.database = database;
        this.callback = callback;

        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Lista pacjentów"));

        setLayout(new BorderLayout());

        table = new PatientTable(database);
        table.getSelectionModel().addListSelectionListener(listSelectionEvent -> {

            // To jest poto aby nie było podwujnego eventu
            //https://stackoverflow.com/questions/5865343/why-does-jtable-always-trigger-listselectionlistener-twice
            //https://stackoverflow.com/questions/10860419/what-exactly-does-getvalueisadjusting-do
            if (listSelectionEvent.getValueIsAdjusting()) return;

            callback.onSelect(table.getSelectedDataRow());
        });


        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);


        //region BottomBox

        JPanel bottomBox = new JPanel();
        bottomBox.setLayout(new FlowLayout(FlowLayout.LEFT));

        addBtn = new JButton("Dodaj");
        addBtn.addActionListener(actionEvent -> {
            callback.onAddBtn();
            table.clearSelection();
            setEnabled(false);
        });
        bottomBox.add(addBtn);

        rmBtn = new JButton("Usuń");
        rmBtn.addActionListener(actionEvent -> {
            callback.onRemoveBtn(table.getSelectedDataRow());
            table.clearSelection();
        });
        bottomBox.add(rmBtn);

        if (Log.isDEBUG) {
            JButton addTest = new JButton("Dodaj test");
            addTest.addActionListener(actionEvent -> table.addDataRow(DataRow.newTestowy()));
            bottomBox.add(addTest);
        }

        add(bottomBox, BorderLayout.PAGE_END);

        //endregion

    }

    public void addDataRow(DataRow dataRow){
        table.addDataRow(dataRow);
        setEnabled(true);
    }

    public void fireTableDataChanged(boolean saveSeletion){
        table.fireTableDataChanged(saveSeletion);
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

        table.setEnabled(b);
        addBtn.setEnabled(b);
        rmBtn.setEnabled(b);
    }

    public interface Interface {
        void onAddBtn();

        void onRemoveBtn(DataRow dataRow);

        void onSelect(DataRow dataRow);
    }
}
