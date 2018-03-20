package dwa.adamy.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class JBorderedPanel extends JPanel {
    private String title;
    private final TitledBorder border;
    private boolean specialMark = false;

    public JBorderedPanel(String title) {
        this.title = title;

        border = BorderFactory.createTitledBorder(title);
        setBorder(border);
    }

    public void setSpecialMark(boolean b) {

        if (specialMark != b) {
            specialMark = b;
            updateBorder();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        updateBorder();
    }

    private void updateBorder() {
        border.setTitle(title + (specialMark ? "*" : ""));
        this.repaint();
    }
}
