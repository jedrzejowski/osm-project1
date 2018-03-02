package dwa.adamy;

import dwa.adamy.db.DataRow;
import dwa.adamy.db.Patient;
import dwa.adamy.ui.DataRowEditor;
import dwa.adamy.ui.PatientListViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klasa zawierająca logikę oddziaływania pomiędzy pomiędzy dwoma panelami
 * @see dwa.adamy.ui.DataRowEditor panel edycji pojedyńczego wiersza
 * @see dwa.adamy.ui.PatientListViewer panel trzymający wszsytkie wiersze
 */
public class FrameMain extends JFrame implements PatientListViewer.Interface, DataRowEditor.Interface {

    private DataRowEditor dataRowEditor;
    private PatientListViewer patientListViewer;

    public FrameMain() {

        setTitle("Rejestracja wyników badań");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        initMenuBar();
        initCompoments();
    }

    /**
     * Inicjuje komponenty, czyli prawy panel listy i lewy panel edycji
     */
    private void initCompoments() {
        dataRowEditor = new DataRowEditor(this);
        add(dataRowEditor, BorderLayout.LINE_START);

        patientListViewer = new PatientListViewer(this);
        add(patientListViewer, BorderLayout.CENTER);
    }

    /**
     * Inicjuje górny pasek menu, zgodnie z wytycznymi projektu
     */
    private void initMenuBar() {

        JMenuBar menuBar = new JMenuBar();

        JMenu aplikacja = new JMenu("Aplikacja");

        JMenuItem zamknij = new JMenuItem("Zamknij");
        zamknij.addActionListener(actionEvent -> dispose());
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
    public boolean onCreatePatient(DataRow newDataRow) {

        for (DataRow dataRow : patientListViewer.getList()) {
            if (dataRow.getPatient().getPesel().equals(newDataRow.getPatient().getPesel())){

                JOptionPane.showMessageDialog(this,
                        "Istnieje już pacjent o takim peselu",
                        "Uwaga !!!",
                        JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        patientListViewer.addDataRow(newDataRow);
        return true;
    }

    @Override
    public void onSaveDataRow(DataRow dataRow) {
        patientListViewer.fireTableDataChanged(true);
    }

    @Override
    public void onCancelDataRow() {
        patientListViewer.setEnabled(true);
    }

    //endregion
}
