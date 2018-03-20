package dwa.adamy.ui;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
     *
     * @param <T>
     */
    public interface IOnEdit<T> {
        T get();

        boolean set(T newValue);
    }

    /**
     * Interfejs do wyboru z listy
     *
     * @param <T>
     */
    public interface IOnSelect<T> extends IOnEdit<T> {
        /**
         * Mapa wartości do wyboru
         *
         * @return mapa wartości
         */
        Map<T, String> getMap();
    }

    /**
     * Pobiera poziom ostrzeżenia
     *
     * @return poziom ostrzeżenia
     */
    public WarnLvl getWarnLvl() {
        return warnLvl;
    }

    /**
     * Ustawia poziom ostrzeżenia
     *
     * @param warnLvl nowy poziom ostrzeżenia
     */
    public void setWarnLvl(WarnLvl warnLvl) {
        this.warnLvl = warnLvl;
        updateWarnLvl();
    }

    protected void updateWarnLvl() {
    }

    public enum WarnLvl {
        OK, GOOD, WARN, ERROR
    }


    public static class Text extends PropEditor {
        protected final JFormattedTextField field;
        protected final IOnEdit<String> callback;

        public Text(String title, IOnEdit<String> callback, JFormattedTextField.AbstractFormatter formatter) {
            super(title);
            this.callback = callback;

            field = new JFormattedTextField(formatter);
            field.setPreferredSize(fieldDim);
            field.setMinimumSize(fieldDim);
            field.setMaximumSize(fieldDim);
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent documentEvent) {
                    changedUpdate(documentEvent);
                }

                @Override
                public void removeUpdate(DocumentEvent documentEvent) {
                    changedUpdate(documentEvent);
                }

                @Override
                public void changedUpdate(DocumentEvent documentEvent) {
                    callback.set(field.getText());
                }
            });

            //field.addKeyListener();

            add(field);

            refreshData();
        }

        public Text(String title, IOnEdit<String> callback) {
            this(title, callback, null);
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

        private int maxLength = -1;

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }
    }

    public static class DigitOnly extends Text {

        public DigitOnly(String title, IOnEdit<String> callback) {
            super(title, callback);

            field.addKeyListener(new KeyAdapter() {

                @Override
                public void keyTyped(KeyEvent e) {
                    if (!e.isActionKey() && !Character.isDigit(e.getKeyChar()))
                        e.consume();
                }
            });
        }
    }

    public static class Double extends Text {

        public Double(String title, IOnEdit<java.lang.Double> callback) {
            super(title, new IOnEdit<String>() {
                @Override
                public String get() {
                    java.lang.Double d = callback.get();
                    return d == null ? "" : d.toString();
                }

                @Override
                public boolean set(String newValue) {
                    if (newValue.equals(""))
                        return callback.set(0d);

                    java.lang.Double f = java.lang.Double.parseDouble(newValue);
                    return callback.set(f);
                }
            });

            field.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent event) {

                    if (event.isActionKey()) {
                        double inc = 1;
                        if (event.isShiftDown()) inc = 0.1d;

                        switch (event.getKeyCode()) {
                            case 38: // up arrow
                                field.setText((callback.get() + inc) + "");
                                break;

                            case 40: // down arrow
                                field.setText((callback.get() - inc) + "");
                                break;
                        }
                    }

                    super.keyPressed(event);
                }

                @Override
                public void keyTyped(KeyEvent event) {

                    char c = event.getKeyChar();

                    if (!Character.isDigit(c)) {

                        if (c == ',') event.setKeyChar(c = '.');

                        if (c != '-' && c != '.') {
                            event.consume();
                            return;
                        }
                    }

                    try {
                        String val = field.getText(), first = "", second = "";
                        int length = val.length();

                        if (length > 0) {
                            int start = field.getSelectionStart();
                            int end = field.getSelectionEnd();

                            first = start == 0 ? "" : val.substring(0, start - 1);
                            second = end == length ? "" : val.substring(end, length - 1);
                        }

                        double d = java.lang.Double.parseDouble(first + c + second);
                    } catch (Exception err) {
                        event.consume();
                    }

                    super.keyTyped(event);
                }
            });
        }
    }

    public static class Select<T> extends PropEditor {
        private final JComboBox<Item> comboBox;
        private final IOnSelect<T> callback;
        private Map<T, Item> itemArray;

        @SuppressWarnings("WeakerAccess")
        public Select(String title, IOnSelect<T> callback) {
            super(title);
            this.callback = callback;

            comboBox = new JComboBox<>();
            comboBox.setPreferredSize(fieldDim);
            comboBox.setMinimumSize(fieldDim);
            comboBox.setMaximumSize(fieldDim);
            comboBox.addActionListener(actionEvent -> {
                @SuppressWarnings("unchecked")
                Item item = (Item) comboBox.getSelectedItem();
                if (item != null) callback.set(item.getValue());
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

        protected class Item {
            private final String text;
            private final T value;

            Item(T value, String text) {
                this.text = text;
                this.value = value;
            }

            @Override
            public String toString() {
                return text;
            }

            String getText() {
                return text;
            }

            T getValue() {
                return value;
            }
        }
    }

    public static class Radio<T> extends PropEditor {
        private final ButtonGroup group;
        private final IOnSelect<T> callback;
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
        final JDateChooser calendar;
        final IOnEdit<java.util.Date> callback;

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
