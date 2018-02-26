package ui;

import db.Examination;

import javax.swing.*;

public class DataRowEditor extends JPanel {

    private PatientEditor patientEditor;
    private ExaminationEditor examinationEditor;

    public DataRowEditor(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        patientEditor = new PatientEditor();
        examinationEditor = new ExaminationEditor();

        add(patientEditor);
        add(examinationEditor);
    }
}
