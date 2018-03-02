package dwa.adamy.ui;

import com.toedter.calendar.JDateChooser;
import dwa.adamy.Log;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstrakcyjna klasa która tworzy edytory do wartośći
 */
public abstract class PropEditor extends JPanel {

    private final static Dimension labelDim = new Dimension(100, 25);
    private final static Dimension fieldDim = new Dimension(180, 20);

    private WarnLvl warnLvl;

    private PropEditor(String title) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel label = new JLabel(title);
        label.setPreferredSize(labelDim);
        label.setMinimumSize(labelDim);
        label.setMaximumSize(labelDim);
        add(label);

        add(Box.createHorizontalGlue());
    }

    public abstract void refreshData();

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

        for (Component component : getComponents())
            component.setEnabled(b);
    }

    /**
     * Interfejs do
     * @param <T>
     */
    public interface IOnEdit<T> {
        T get();

        boolean set(T newValue);
    }

    /**
     * Interfejs do wyboru z listy
     * @param <T>
     */
    public interface IOnSelect<T> extends IOnEdit<T> {
        /**
         * Mapa wartości do wyboru
         * @return mapa wartości
         */
        Map<T, String> getMap();
    }

    /**
     * Pobiera poziom ostrzeżenia
     * @return poziom ostrzeżenia
     */
    public WarnLvl getWarnLvl() {
        return warnLvl;
    }

    /**
     * Ustawia poziom ostrzeżenia
     * @param warnLvl nowy poziom ostrzeżenia
     */
    public void setWarnLvl(WarnLvl warnLvl) {
        this.warnLvl = warnLvl;
        updateWarnLvl();
    }

    protected void updateWarnLvl() { }

    public enum WarnLvl {
        OK, GOOD, WARN, ERROR
    }


    public static class Text extends PropEditor {
        JTextField field;
        IOnEdit<String> callback;

        public Text(String title, IOnEdit<String> callback) {
            super(title);
            this.callback = callback;

            field = new JTextField();
            field.setPreferredSize(fieldDim);
            field.setMinimumSize(fieldDim);
            field.setMaximumSize(fieldDim);
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent documentEvent) {
                    callback.set(field.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent documentEvent) {
                    callback.set(field.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent documentEvent) {
                    callback.set(field.getText());
                }
            });
            add(field);

            refreshData();
        }

        @Override
        public void refreshData() {
            field.setText(callback.get());
        }

        @Override
        protected void updateWarnLvl() {
            Color color = UIManager.getColor("TextField.background");

            switch (getWarnLvl()) {
                case ERROR:
                    color = COLOR_ERROR;
                    break;

                case WARN:
                    color = COLOR_WARN;
                    break;

                case GOOD:
                    color = COLOR_GOOD;
                    break;
            }

            field.setBackground(color);
        }
    }

    public static class Select<T> extends PropEditor {
        private JComboBox comboBox;
        private IOnSelect<T> callback;
        private Map<T, Item> itemArray;

        public Select(String title, IOnSelect<T> callback) {
            super(title);
            this.callback = callback;

            comboBox = new JComboBox();
            comboBox.setPreferredSize(fieldDim);
            comboBox.setMinimumSize(fieldDim);
            comboBox.setMaximumSize(fieldDim);
            comboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    Item item = (Item) comboBox.getSelectedItem();
                    callback.set(item.getValue());
                }
            });

            refreshButtonsDef();

            add(comboBox);

            refreshData();
        }

        public void refreshButtonsDef() {
            Map<T, String> array = callback.getMap();
            itemArray = new HashMap<>();

            for (Map.Entry<T, String> entry : array.entrySet()) {

                Item item = new Item(entry.getKey(), entry.getValue());
                itemArray.put(entry.getKey(), item);
                comboBox.addItem(item);
            }
        }

        @Override
        public void refreshData() {
            comboBox.setSelectedItem(itemArray.get(callback.get()));
        }

        class Item {
            private String text;
            private T value;

            Item(T value, String text) {
                this.text = text;
                this.value = value;
            }

            @Override
            public String toString() {
                return text;
            }

            public String getText() {
                return text;
            }

            public T getValue() {
                return value;
            }
        }
    }

    public static class Radio<T> extends PropEditor {
        private ButtonGroup group;
        private IOnSelect<T> callback;
        private Map<T, JRadioButton> radioButtons;

        public Radio(String title, IOnSelect<T> callback) {
            super(title);
            this.callback = callback;

            group = new ButtonGroup();

            refreshButtonsDef();
        }

        public void refreshButtonsDef() {
            Map<T, String> array = callback.getMap();
            radioButtons = new HashMap<>();

            for (Map.Entry<T, String> entry : array.entrySet()) {

                JRadioButton btn = new JRadioButton();
                btn.setText(entry.getValue());

                btn.addActionListener(actionEvent -> callback.set(entry.getKey()));

                group.add(btn);
                radioButtons.put(entry.getKey(), btn);

                add(btn);
            }
        }

        @Override
        public void refreshData() {
            radioButtons.get(callback.get()).setSelected(true);
        }
    }

    public static class Date extends PropEditor {
        JDateChooser calendar;
        IOnEdit<java.util.Date> callback;

        public Date(String title, IOnEdit<java.util.Date> callback) {
            super(title);
            this.callback = callback;

            calendar = new JDateChooser();
            calendar.setPreferredSize(fieldDim);
            calendar.setMinimumSize(fieldDim);
            calendar.setMaximumSize(fieldDim);
            calendar.addPropertyChangeListener("date", propertyChangeEvent -> callback.set(calendar.getDate()));
            add(calendar);

            refreshData();
        }

        @Override
        public void refreshData() {
            calendar.setDate(callback.get());
        }
    }

    public static final Color COLOR_WARN = new Color(255, 234, 168);
    public static final Color COLOR_ERROR = new Color(254, 204, 205);
    public static final Color COLOR_GOOD = new Color(188, 245, 188);
}
