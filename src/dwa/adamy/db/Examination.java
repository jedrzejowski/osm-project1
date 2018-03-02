package dwa.adamy.db;

import java.util.Date;

public class Examination {

    private Date date;
    private String erythrocytes, leukocytes, platelets;

    public Examination(){
        date = null;
        erythrocytes = "";
        leukocytes = "";
        platelets = "";
    }

    public Examination(Examination examination){
        date = examination.date;
        erythrocytes = examination.erythrocytes;
        leukocytes = examination.leukocytes;
        platelets = examination.platelets;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getErythrocytes() {
        return erythrocytes;
    }

    public void setErythrocytes(String erythrocytes) {
        this.erythrocytes = erythrocytes;
    }

    public String getLeukocytes() {
        return leukocytes;
    }

    public void setLeukocytes(String leukocytes) {
        this.leukocytes = leukocytes;
    }

    public String getPlatelets() {
        return platelets;
    }

    public void setPlatelets(String platelets) {
        this.platelets = platelets;
    }

    @Override
    public String toString() {
        return String.format("Examination{%s}", getDate());
    }
}
