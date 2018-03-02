package dwa.adamy.db;

import java.util.*;

/**
 * Klasa obiektów reprezentująca pacjenta
 */
public class Patient {

    private String name1 = "";
    private String name2 = "";
    private String pesel = "";
    private Sex sex = Sex.FEMALE;
    private InsuranceType insuranceType = InsuranceType.NONE;

    public Patient() {
    }

    /**
     * Tworzy głęboką kopie pacjenta
     * @param orginal pacjent do skopiowania
     */
    public Patient(Patient orginal) {
        name1 = orginal.name1;
        name2 = orginal.name2;
        pesel = orginal.pesel;
        sex = orginal.sex;
        insuranceType = orginal.insuranceType;
    }

    /**
     * Pobiera imię pacjenta
     * @return imię pacjenta
     */
    public String getName1() {
        return name1;
    }

    /**
     * Ustawia imię pacjenta
     * @param name1 imię pacjenta
     */
    public void setName1(String name1) {
        this.name1 = name1;
    }

    /**
     * Pobiera nazwisko pacjenta
     * @return nazwisko pacjenta
     */
    public String getName2() {
        return name2;
    }

    /**
     * Ustawia nazwisko pacjenta
     * @param name2 nazwisko pacjenta
     */
    public void setName2(String name2) {
        this.name2 = name2;
    }

    /**
     * Pobiera imię i nazwisko pacjenta
     * @return imię i nazwisko pacjenta ze spacją
     */
    public String getFullName() {
        return name1 + " " + name2;
    }

    /**
     * Pobiera pesel pacjenta
     * @return pesel pacjenta
     */
    public String getPesel() {
        return pesel;
    }

    /**
     * Ustawia pesel pacjenta
     * @param pesel pesel pacjenta
     */
    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    /**
     * Pobiera płeć pacjenta
     * @return płeć pacjenta
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Ustawia płeć pacjenta
     * @param sex nowa płeć pacjenta
     */
    public void setSex(Sex sex) {
        this.sex = sex;
    }

    /**
     * Pobiera typ ubezpieczenia
     * @return typ ubezpieczenia
     */
    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    /**
     * Ustawia typ ubezpieczenia
     * @param insuranceType typ ubezpieczenia
     */
    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    @Override
    public String toString() {
        return String.format("Patient{%s %s, %s}", getName1(), getName2(), getPesel());
    }

    private static int DebugTestcounter = 0;

    /**
     * Tworzy pacjenta testowego
     * @return pacjent testowy
     */
    public static Patient newTestowy() {
        DebugTestcounter++;

        Patient testowy = new Patient();
        testowy.setName1("Jan" + DebugTestcounter);
        testowy.setName2("Testowy");
        testowy.setSex(Sex.MALE);
        testowy.setPesel("22222222222");
        return testowy;
    }

    /**
     * Typ wyliczeniowy reprezentujący płeć
     */
    public enum Sex {
        MALE, FEMALE
    }

    private static Map<Sex, String> sexMap = null;

    /**
     * Pobiera mapę płci z nazwami
     * @return mapa płci z nazwami
     */
    public static Map<Sex, String> getSexStringMap() {
        if (sexMap != null) return sexMap;

        sexMap = new HashMap<>();

        sexMap.put(Sex.MALE, "Mężczyzna");
        sexMap.put(Sex.FEMALE, "Kobieta");

        return sexMap;
    }

    /**
     * Typ wyliczeniowy reprezentujący ubezpiecznie
     */
    public enum InsuranceType {
        NFZ, PRIVATE, NONE
    }

    private static Map<InsuranceType, String> insuranceTypeMap = null;

    /**
     * Pobiera mapę ubezpieczeń z nazwami
     * @return mapa ubezpieczeń z nazwami
     */
    public static Map<InsuranceType, String> getInsuranceTypeStringMap() {
        if (insuranceTypeMap != null) return insuranceTypeMap;

        insuranceTypeMap = new HashMap<>();

        insuranceTypeMap.put(InsuranceType.NONE, "Brak");
        insuranceTypeMap.put(InsuranceType.NFZ, "NFZ");
        insuranceTypeMap.put(InsuranceType.PRIVATE, "Prywatne");

        return insuranceTypeMap;
    }

}

