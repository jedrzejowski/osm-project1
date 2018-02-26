package ui;

import javax.swing.*;

public class ExaminationEditor extends JPanel {
    PropEditor.Text dateField;

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
    }
}
