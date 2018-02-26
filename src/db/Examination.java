package db;

import java.util.Date;

public class Examination {

    private Date date;

    public Examination(){
        date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
