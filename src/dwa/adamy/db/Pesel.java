package dwa.adamy.db;


import java.util.Date;

/**
 * Klasa reprezentująca numer PESEL
 */
public class Pesel {

    private static final int[] weight = {9, 7, 3, 1, 9, 7, 3, 1, 9, 7};

    private final String pesel;
    private final boolean valid;
    private Date born;

    public Pesel(String pesel) {
        this.pesel = pesel;

        if (pesel.length() != 11) {
            valid = false;
            return;
        }

        int sum = 0;
        for (int i = 0; i < weight.length; i++)
            sum += (Integer.parseInt(pesel.substring(i, i + 1), 10) * weight[i]);
        sum = sum % 10;
        valid = (sum == Integer.parseInt(pesel.substring(10, 11), 10));

        if (!valid) return;


        int year = Integer.parseInt(pesel.substring(0, 2), 10);
        int month = Integer.parseInt(pesel.substring(2, 4), 10);
        int day = Integer.parseInt(pesel.substring(4, 6), 10);

        if (month > 80) {
            year += 1800;
            month -= 80;
        } else if (month > 60) {
            year += 2200;
            month -= 60;
        } else if (month > 40) {
            year += 2100;
            month -= 40;
        } else if (month > 20) {
            year += 2000;
            month -= 20;
        } else
            year += 1900;

        born = new Date(year, month, day);
    }

    public boolean isValid() {
        return valid;
    }

    public Date getBorn() {
        return born;
    }

    public Patient.Sex getSex() {
        if (Integer.parseInt(pesel.substring(9, 10), 10) % 2 == 1)
            return Patient.Sex.MALE;
        return Patient.Sex.FEMALE;
    }

    @Override
    public String toString() {
        return pesel;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Pesel.class && this.pesel.equals(((Pesel) obj).pesel);
    }
}
