package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientListViewer extends JPanel {

    JButton addBtn, rmBtn;
    PatientTable table;

    public PatientListViewer(){
        super();

        initComponents();
    }

    private void initComponents(){
        setBorder(BorderFactory.createTitledBorder("Lista pacjentów"));

        table = new PatientTable();
        add(table, BorderLayout.CENTER);

        JPanel bottomBox = new JPanel();

        addBtn = new JButton("Dodaj");
        addBtn.addActionListener(actionEvent -> {

        });
        bottomBox.add(addBtn);

        rmBtn = new JButton("Usuń");
        rmBtn.addActionListener(actionEvent -> {

        });
        bottomBox.add(rmBtn);

        add(bottomBox, BorderLayout.PAGE_END);

    }
}
