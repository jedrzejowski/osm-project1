import ui.DataRowEditor;
import ui.PatientEditor;
import ui.PatientListViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameMain extends JFrame {

    private DataRowEditor dataRowEditor;
    private PatientListViewer patientListViewer;
    private JMenuBar menuBar;

    public FrameMain(){

        this.setTitle("Rejestracja wyników badań");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);



        initMenuBar();
        initCompoments();
    }

    private void initCompoments(){


        dataRowEditor = new DataRowEditor();
        add(dataRowEditor, BorderLayout.LINE_START);

        patientListViewer = new PatientListViewer();
        add(patientListViewer, BorderLayout.CENTER);

    }

    private void initMenuBar(){

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
}
