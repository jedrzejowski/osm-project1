package dwa.adamy.ui;

import dwa.adamy.db.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PatientEditor extends JBorderedPanel {

    private PropEditor.Text name1Field, name2Field, peselField;
    private PropEditor.Radio sexField;
    private PropEditor.Select insuranceField;
    private Interface callback;
    private JButton saveBtn, cancelBtn;

    private Patient patientOrginal, patientClone = new Patient();

    public PatientEditor(Interface callback) {
        super("Dane pacjenta");
        this.callback = callback;

        initCompoments();
        this.setEnabled(false);
    }

    private void initCompoments() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //region PropEditors

        name1Field = new PropEditor.Text("Imię:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return patientClone.getName1();
            }

            @Override
            public boolean set(String newValue) {
                patientClone.setName1(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        name2Field = new PropEditor.Text("Nazwisko:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return patientClone.getName2();
            }

            @Override
            public boolean set(String newValue) {
                patientClone.setName2(newValue);
                return false;
            }
        });

        peselField = new PropEditor.Text("Pesel:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return patientClone.getPesel();
            }

            @Override
            public boolean set(String newValue) {
                patientClone.setPesel(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        sexField = new PropEditor.Radio<>("Płeć", new PropEditor.IOnSelect<Patient.Sex>() {

            @Override
            public Map<Patient.Sex, String> getMap() {
                return Patient.getSexStringMap();
            }

            @Override
            public Patient.Sex get() {
                return patientClone.getSex();
            }

            @Override
            public boolean set(Patient.Sex newValue) {
                patientClone.setSex(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        insuranceField = new PropEditor.Select<>("Ubezpieczenie", new PropEditor.IOnSelect<Patient.InsuranceType>() {
            @Override
            public Map<Patient.InsuranceType, String> getMap() {
                return Patient.getInsuranceTypeStringMap();
            }

            @Override
            public Patient.InsuranceType get() {
                return patientClone.getInsuranceType();
            }

            @Override
            public boolean set(Patient.InsuranceType newValue) {
                patientClone.setInsuranceType(newValue);
                setSpecialMark(true);
                return true;
            }
        });

        add(name1Field);
        add(name2Field);
        add(peselField);
        add(sexField);
        add(insuranceField);

        //endregion

        //region Buttons

        JPanel bottomBox = new JPanel();
        bottomBox.setLayout(new FlowLayout(FlowLayout.LEFT));

        saveBtn = new JButton("Zapisz");
        saveBtn.addActionListener(actionEvent -> {
            patientOrginal = new Patient(patientClone);
            setSpecialMark(false);
            callback.onSave(patientOrginal);
        });
        bottomBox.add(saveBtn);

        cancelBtn = new JButton("Anuluj");
        cancelBtn.addActionListener(actionEvent -> {
            setPatient(patientOrginal);
            setSpecialMark(false);
            callback.onCancel();
        });
        bottomBox.add(cancelBtn);

        add(bottomBox);

        //endregion

        setSpecialMark(false);
    }

    public Patient getPatient() {
        return patientOrginal;
    }

    public void setPatient(Patient patient) {
        if (patient == null) patient = new Patient();

        patientOrginal = patient;
        patientClone = new Patient(patient);

        name1Field.refreshData();
        name2Field.refreshData();
        peselField.refreshData();
        sexField.refreshData();
        insuranceField.refreshData();

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
        void onSave(Patient patient);

        void onCancel();
    }
}
