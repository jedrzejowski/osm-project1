package dwa.adamy.db;

import java.util.Vector;

public class Database extends Vector<DataRow> {

    public Database() {
    }

    @Override
    public synchronized boolean add(DataRow newDataRow){

        for (DataRow dataRow : this) {
            if (dataRow.getPatient().getPesel().equals(newDataRow.getPatient().getPesel()))
                return false;
        }

        return super.add(newDataRow);
    }
}
