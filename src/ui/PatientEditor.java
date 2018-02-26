package ui;

import db.Patient;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PatientEditor extends JPanel {

    PropEditor.Text name1Field, name2Field, peselField;
    PropEditor.Radio sexField;
    PropEditor.Select insuranceField;

    public PatientEditor() {
        initCompoments();
    }

    private void initCompoments() {
        setBorder(BorderFactory.createTitledBorder("Dane pacjenta"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        name1Field = new PropEditor.Text("Imię:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return null;
            }

            @Override
            public boolean set(String newValue) {
                return false;
            }
        });

        name2Field = new PropEditor.Text("Nazwisko:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return null;
            }

            @Override
            public boolean set(String newValue) {
                return false;
            }
        });

        peselField = new PropEditor.Text("Pesel:", new PropEditor.IOnEdit<String>() {
            @Override
            public String get() {
                return null;
            }

            @Override
            public boolean set(String newValue) {
                return false;
            }
        });

        sexField = new PropEditor.Radio<>("Płeć", new PropEditor.IOnSelect<Patient.Sex>(){

            @Override
            public Map<Patient.Sex, String> getMap() {
                Map<Patient.Sex, String> map = new HashMap<>();

                map.put(Patient.Sex.MALE, "Mężczyzna");
                map.put(Patient.Sex.FEMALE, "Kobieta");

                return map;
            }

            @Override
            public Patient.Sex get() {
                return null;
            }

            @Override
            public boolean set(Patient.Sex newValue) {


                return false;
            }
        });

        insuranceField = new PropEditor.Select<>("Ubezpieczenie", new PropEditor.IOnSelect<Patient.InsuranceType>() {
            @Override
            public Map<Patient.InsuranceType, String> getMap() {
                Map<Patient.InsuranceType, String> map = new HashMap<>();

                map.put(Patient.InsuranceType.NONE, "Brak");
                map.put(Patient.InsuranceType.NFZ, "NFZ");
                map.put(Patient.InsuranceType.PRIVATE, "Prywatne");

                return map;
            }

            @Override
            public Patient.InsuranceType get() {
                return null;
            }

            @Override
            public boolean set(Patient.InsuranceType newValue) {
                return false;
            }
        });

        add(name1Field);
        add(name2Field);
        add(peselField);
        add(sexField);
        add(insuranceField);
    }
}
