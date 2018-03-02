package dwa.adamy.ui;

import dwa.adamy.db.DataRow;
import dwa.adamy.db.Patient;

import javax.swing.*;
import java.awt.*;

public class DataRowEditor extends JPanel {

    private PatientEditor patientEditor;
    private ExaminationEditor examinationEditor;
    private Interface callback;

    private State state;

    private DataRow dataRow;

    public DataRowEditor(Interface callback) {
        super();
        this.callback = callback;
        state = State.WAIT;

        initCompoments();
        setEnabled(false);
    }

    private void initCompoments() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        patientEditor = new PatientEditor(new PatientEditor.Interface() {
            @Override
            public void onSave(Patient patient) {
                dataRow.setPatient(patient);

                switch (state) {
                    case CREATING:
                        callback.onCreatePatient(dataRow);
                        setEnabled(false);
                        break;
                    case EDITING:
                        callback.onSaveDataRow(dataRow);
                        break;
                }
            }

            @Override
            public void onCancel() {

                switch (state) {
                    case CREATING:
                        setEnabled(false);
                        break;
                }

            }
        });
        add(patientEditor);

        examinationEditor = new ExaminationEditor();
        add(examinationEditor);
    }

    public void createNewPatient() {
        state = State.CREATING;
        dataRow = new DataRow();

        patientEditor.setEnabled(true);
        patientEditor.setPatient(dataRow.getPatient());

        examinationEditor.setEnabled(false);
    }

    public DataRow getDataRow() {
        return dataRow;
    }

    public void setDataRow(DataRow dataRow) {
        state = State.EDITING;
        this.dataRow = dataRow;

        patientEditor.setEnabled(true);
        patientEditor.setPatient(dataRow.getPatient());

        examinationEditor.setEnabled(true);
        examinationEditor.setExamination(dataRow.getExamination());
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

        for (Component component : getComponents())
            component.setEnabled(b);

        if (!b) {
            state = State.WAIT;
            patientEditor.setEnabled(false);
            patientEditor.setPatient(null);
            examinationEditor.setEnabled(false);
            examinationEditor.setExamination(null);
        }
    }

    public interface Interface {
        void onCreatePatient(DataRow dataRow);

        void onSaveDataRow(DataRow dataRow);
    }

    private enum State {
        WAIT, CREATING, EDITING
    }
}
