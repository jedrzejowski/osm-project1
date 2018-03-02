package dwa.adamy;

import dwa.adamy.db.DataRow;
import dwa.adamy.db.Patient;
import dwa.adamy.ui.DataRowEditor;
import dwa.adamy.ui.PatientListViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMain extends JFrame implements PatientListViewer.Interface, DataRowEditor.Interface {

    private DataRowEditor dataRowEditor;
    private PatientListViewer patientListViewer;
    private JMenuBar menuBar;

    public FrameMain() {

        this.setTitle("Rejestracja wyników badań");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        initMenuBar();
        initCompoments();
    }

    private void initCompoments() {


        dataRowEditor = new DataRowEditor(this);
        add(dataRowEditor, BorderLayout.LINE_START);

        patientListViewer = new PatientListViewer(this);
        add(patientListViewer, BorderLayout.CENTER);

    }

    private void initMenuBar() {

        menuBar = new JMenuBar();

        JMenu aplikacja = new JMenu("Aplikacja");

        JMenuItem zamknij = new JMenuItem("Zamknij");
        zamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        aplikacja.add(zamknij);

        menuBar.add(aplikacja);


        setJMenuBar(menuBar);
    }


    //region PatientListViewer

    @Override
    public void onAddBtn() {
        dataRowEditor.createNewPatient();
    }

    @Override
    public void onRemoveBtn(DataRow dataRow) {
        if (dataRow == null) return;
        patientListViewer.removeDataRow(dataRow);
        dataRowEditor.setEnabled(false);
    }

    @Override
    public void onSelect(DataRow dataRow) {
        if (dataRow == null) return;
        dataRowEditor.setDataRow(dataRow);
    }

    //endregion

    //region DataRowEditor

    @Override
    public void onCreatePatient(DataRow dataRow) {
        patientListViewer.addDataRow(dataRow);
    }

    @Override
    public void onSaveDataRow(DataRow dataRow) {
        patientListViewer.fireTableDataChanged(true);
    }

    //endregion
}
