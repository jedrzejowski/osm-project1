package dwa.adamy.ui;

import dwa.adamy.db.Examination;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ExaminationEditor extends JBorderedPanel {
    private PropEditor.Date dateField;
    private PropEditor.Text erythrocytesField, leukocytesField, plateletsField;
    private JButton saveBtn, cancelBtn;
    private Examination examinationOrginal, examinationClone = new Examination();

    private final Interface callback;

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

        erythrocytesField = new PropEditor.Double("Erytrocyty", new PropEditor.IOnEdit<Double>() {
            @Override
            public Double get() {
                return examinationClone.getErythrocytes();
            }

            @Override
            public boolean set(Double newValue) {
                examinationClone.setErythrocytes(newValue);

                WarnLvlParse(erythrocytesField, newValue, 4.5, 6, 3, 7.5);

                setSpecialMark(true);
                return true;
            }
        });

        leukocytesField = new PropEditor.Double("Leukocyty", new PropEditor.IOnEdit<Double>() {
            @Override
            public Double get() {
                return examinationClone.getLeukocytes();
            }

            @Override
            public boolean set(Double newValue) {
                examinationClone.setLeukocytes(newValue);

                WarnLvlParse(leukocytesField, newValue, 4, 10, 0, 14);

                setSpecialMark(true);
                return true;
            }
        });

        plateletsField = new PropEditor.Double("Płyteki krwi", new PropEditor.IOnEdit<Double>() {
            @Override
            public Double get() {
                return examinationClone.getPlatelets();
            }

            @Override
            public boolean set(Double newValue) {
                examinationClone.setPlatelets(newValue);

                WarnLvlParse(plateletsField, newValue, 130, 300, 0, 400);

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

    private static void WarnLvlParse(PropEditor editor, Double val, double good1, double good2, double warn1, double warn2) {
        if (editor == null) return;

        if (good1 < val && val < good2) {
            editor.setWarnLvl(PropEditor.WarnLvl.GOOD);
        } else if (warn1 < val && val < warn2) {
            editor.setWarnLvl(PropEditor.WarnLvl.WARN);
        } else {
            editor.setWarnLvl(PropEditor.WarnLvl.ERROR);
        }
    }

    public interface Interface {
        void onSave(Examination examination);

        void onCancel();
    }
}
