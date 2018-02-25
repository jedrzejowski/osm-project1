import ui.PatientEditor;

import javax.swing.*;

public class FrameMain extends JFrame {

    private PatientEditor patientEditor;

    public FrameMain(){

        this.setTitle("Rejestracja wyników badań");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);


        patientEditor = new PatientEditor();
        add(patientEditor);
    }

}
