package dwa.adamy.db;

public class DataRow {
    private Patient patient;
    private Examination examination;

    public DataRow() {
        this.patient = new Patient();
        this.examination = new Examination();
    }

    public DataRow(Patient patient, Examination examination) {
        this.patient = patient;
        this.examination = examination;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public static DataRow newTestowy() {
        DataRow row = new DataRow();
        row.setPatient(Patient.newTestowy());
        return row;
    }

    @Override
    public String toString() {
        return String.format("DataRow {\n\tpatient: %s\n\texamination: %s\n}", getPatient(), getExamination());
    }
}
