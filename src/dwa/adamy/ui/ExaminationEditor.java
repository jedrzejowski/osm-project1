package dwa.adamy.ui;

import dwa.adamy.Log;
import dwa.adamy.db.Examination;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ExaminationEditor extends JBorderedPanel {
    private PropEditor.Date dateField;
    private PropEditor.Text erythrocytesField, leukocytesField, plateletsField;
    private JButton saveBtn, cancelBtn;
    private Examination examinationOrginal, examinationClone = new Examination();

    private Interface callback;

    public ExaminationEditor(Interface callback) {
        super("Badanie");
        this.callback = callback;

        initCompoments();
    }

    private void initCompoments() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        dateField = new PropEditor.Date("Data:", new PropEditor.IOnEdit<Date>() {
            @Override
            public Date get() {
                return examinationClone.getDate();
            }

            @Override
            public boolean set(Date newValue) {
                examinationClone.setDate(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        erythrocytesField = new PropEditor.Text("Erytrocyty", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return examinationClone.getErythrocytes();
            }

            @Override
            public boolean set(String newValue) {
                examinationClone.setErythrocytes(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        leukocytesField = new PropEditor.Text("Leukocyty", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return examinationClone.getLeukocytes();
            }

            @Override
            public boolean set(String newValue) {
                examinationClone.setLeukocytes(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        plateletsField = new PropEditor.Text("Płyteki krwi", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return examinationClone.getPlatelets();
            }

            @Override
            public boolean set(String newValue) {
                examinationClone.setPlatelets(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        add(dateField);
        add(erythrocytesField);
        add(leukocytesField);
        add(plateletsField);

        //region Buttons

        JPanel bottomBox = new JPanel();
        bottomBox.setLayout(new FlowLayout(FlowLayout.LEFT));

        saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(actionEvent -> {
            examinationOrginal = new Examination(examinationClone);
            setSpecialMark(false);
            callback.onSave(examinationOrginal);
        });
        bottomBox.add(saveBtn);

        cancelBtn = new JButton("Anuluj");
        cancelBtn.addActionListener(actionEvent -> {
            setExamination(examinationOrginal);
            setSpecialMark(false);
            callback.onCancel();
        });
        bottomBox.add(cancelBtn);

        add(bottomBox);

        //endregion

        setSpecialMark(false);
    }

    public Examination getExamination() {
        return examinationOrginal;
    }

    public void setExamination(Examination examination) {

        if (examination == null) examination = new Examination();

        examinationOrginal = examination;
        examinationClone = new Examination(examination);

        dateField.refreshData();
        erythrocytesField.refreshData();
        leukocytesField.refreshData();
        plateletsField.refreshData();

        setSpecialMark(false);
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

        for (Component component : getComponents())
            component.setEnabled(b);

        saveBtn.setEnabled(b);
        cancelBtn.setEnabled(b);
    }


    public interface Interface {
        void onSave(Examination examination);

        void onCancel();
    }
}
