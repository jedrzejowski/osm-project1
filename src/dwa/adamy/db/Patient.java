package dwa.adamy.db;

import java.util.*;

public class Patient {

    private String name1 = "";
    private String name2 = "";
    private String pesel = "";
    private Sex sex = Sex.FEMALE;
    private InsuranceType insuranceType = InsuranceType.NONE;

    public Patient() {
    }

    public Patient(Patient orginal) {
        name1 = orginal.name1;
        name2 = orginal.name2;
        pesel = orginal.pesel;
        sex = orginal.sex;
        insuranceType = orginal.insuranceType;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getFullName() {
        return name1 + " " + name2;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    @Override
    public String toString() {
        return String.format("Patient{%s %s, %s}", getName1(), getName2(), getPesel());
    }

    private static int DebugTestcounter = 0;

    public static Patient newTestowy() {
        DebugTestcounter++;

        Patient testowy = new Patient();
        testowy.setName1("Jan" + DebugTestcounter);
        testowy.setName2("Testowy");
        testowy.setSex(Sex.MALE);
        testowy.setPesel("22222222222");
        return testowy;
    }

    public enum Sex {
        MALE, FEMALE
    }

    static Map<Sex, String> sexMap = null;

    public static Map<Sex, String> getSexStringMap() {
        if (sexMap != null) return sexMap;

        sexMap = new HashMap<>();

        sexMap.put(Sex.MALE, "Mężczyzna");
        sexMap.put(Sex.FEMALE, "Kobieta");

        return sexMap;
    }

    public enum InsuranceType {
        NFZ, PRIVATE, NONE
    }

    static Map<InsuranceType, String> insuranceTypeMap = null;

    public static Map<InsuranceType, String> getInsuranceTypeStringMap() {
        if (insuranceTypeMap != null) return insuranceTypeMap;

        insuranceTypeMap = new HashMap<>();

        insuranceTypeMap.put(InsuranceType.NONE, "Brak");
        insuranceTypeMap.put(InsuranceType.NFZ, "NFZ");
        insuranceTypeMap.put(InsuranceType.PRIVATE, "Prywatne");

        return insuranceTypeMap;
    }

}

