package dwa.adamy.db;

import java.util.Date;

/**
 * Klasa obiektów reprezentujących dane badania krwi
 *
 * Dlaczego wartości parametrów są string'ami a nie int'ami?
 * Otóż dokumentacja medyczna ma być tworzona prze lekarzy, lub im podobnych. Aplikacja nie powinna ograniczać
 * możliwości tworzenia i edycji danych medycznych nie będących danymi osobowymi. To lekarz decyduje co wpisać w rubrykę
 * i to lekarz będzie te dane interpretował, a nie aplikacja
 */
public class Examination {

    private Date date;
    private String erythrocytes, leukocytes, platelets;

    public Examination(){
        date = null;
        erythrocytes = "";
        leukocytes = "";
        platelets = "";
    }

    /**
     * Tworzy głęboką kopie badania
     * @param examination badanie do sklonowania
     */
    public Examination(Examination examination){
        date = examination.date;
        erythrocytes = examination.erythrocytes;
        leukocytes = examination.leukocytes;
        platelets = examination.platelets;
    }

    /**
     * Pobiera date badania
     * @return data badania
     */
    public Date getDate() {
        return date;
    }

    /**
     * Ustawia date badania
     * @param date nowa data bdania
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Pobiera wartość liczby erytrocytów
     * @return liczna erytrocytów
     */
    public String getErythrocytes() {
        return erythrocytes;
    }

    /**
     * Ustawia wartość liczby erytrocytów
     * @param erythrocytes nowa liczna erytrocytów
     */
    public void setErythrocytes(String erythrocytes) {
        this.erythrocytes = erythrocytes;
    }

    /**
     * Pobiera wartość leukocytów
     * @return wartość leukocytów
     */
    public String getLeukocytes() {
        return leukocytes;
    }

    /**
     * Ustawia wartość leukocytów
     * @param leukocytes nowa wartość leukocytów
     */
    public void setLeukocytes(String leukocytes) {
        this.leukocytes = leukocytes;
    }

    /**
     * Pobiera liczbę płytek krwi
     * @return liczba płytek krwi
     */
    public String getPlatelets() {
        return platelets;
    }

    /**
     * Ustawia liczbę płytek krwi
     * @param platelets nowa liczba płytek krwi
     */
    public void setPlatelets(String platelets) {
        this.platelets = platelets;
    }

    @Override
    public String toString() {
        return String.format("Examination{%s}", getDate());
    }
}
