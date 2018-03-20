package dwa.adamy;

import dwa.adamy.db.Database;

public class Main {

    /**
     * Główna pętla programu uruchamiająca okno
     *
     * @param args argumenty programu
     */
    public static void main(String[] args) {

        Database db = new Database();

        FrameMain app = new FrameMain(db);
        app.setVisible(true);

//        System.out.println(Double.parseFloat("0f"));
//        System.out.println(Character.isDigit(','));

    }

}
