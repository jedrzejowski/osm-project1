package ui;

import db.Examination;

import javax.swing.*;
import java.awt.*;

public class DataRowEditor extends JPanel {

    private PatientEditor patientEditor;
    private ExaminationEditor examinationEditor;

    public DataRowEditor(){
        super();

        initCompoments();
    }

    private void initCompoments() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        patientEditor = new PatientEditor();
        add(patientEditor);


        examinationEditor = new ExaminationEditor();
        add(examinationEditor);
    }
}
