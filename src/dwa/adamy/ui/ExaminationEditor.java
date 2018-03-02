package dwa.adamy.ui;

import dwa.adamy.db.Examination;
import dwa.adamy.db.Patient;

import javax.swing.*;
import java.awt.*;

public class ExaminationEditor extends JPanel {
    PropEditor.Text dateField;
    Examination examination;
    JButton saveBtn, cancelBtn;

    public ExaminationEditor(){
        super();
        setBorder(BorderFactory.createTitledBorder("Badanie"));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initCompoments();
    }

    private void initCompoments(){

        dateField = new PropEditor.Text("Data:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return null;
            }

            @Override
            public boolean set(String newValue) {
                return false;
            }
        });

        add(dateField);

        //region Buttons

        JPanel bottomBox = new JPanel();
        bottomBox.setLayout(new FlowLayout(FlowLayout.LEFT));

        saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(actionEvent -> {

        });
        bottomBox.add(saveBtn);

        cancelBtn = new JButton("Anuluj");
        cancelBtn.addActionListener(actionEvent -> {

        });
        bottomBox.add(cancelBtn);

        add(bottomBox);

        //endregion
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

        for (Component component : getComponents())
            component.setEnabled(b);

        saveBtn.setEnabled(b);
        cancelBtn.setEnabled(b);
    }
}
