package dwa.adamy.db;

/**
 * Klasa obiektów reprezentująca obiekt bazy danych, zawiera obiekt pacjenta i obiekt badania
 * @see Patient
 * @see Examination
 */
public class DataRow {
    private Patient patient;
    private Examination examination;

    public DataRow() {
        this.patient = new Patient();
        this.examination = new Examination();
    }

    /**
     * Pobiera pacjenta
     * @return pacjent
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Ustawia pacjenta
     * @param patient nowy pacjent
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Pobiera badanie
     * @return badanie
     */
    public Examination getExamination() {
        return examination;
    }

    /**
     * Ustawia badanie
     * @param examination nowe badanie
     */
    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    /**
     * Tworzy wiersz testowy do debugowania aplikacji w czasie rzeczywistym, każdy następny wygenerowany wiersz jest
     * inny niż poprzedni
     * @return wiersz testowy
     */
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
