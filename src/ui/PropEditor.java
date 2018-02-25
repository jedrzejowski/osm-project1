package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public abstract class PropEditor extends JPanel {

    private final static Dimension labelDim = new Dimension(100, 25);
    private final static Dimension fieldDim = new Dimension(180, 20);

    PropEditor(String title) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel label = new JLabel(title);
        label.setPreferredSize(labelDim);
        label.setMinimumSize(labelDim);
        label.setMaximumSize(labelDim);
        add(label);

        add(Box.createHorizontalGlue());
    }

    public interface IOnEdit<T> {
        T get();

        boolean set(T newValue);
    }

    public interface IOnSelect<T> extends IOnEdit<T> {
        Map<T, String> getMap();
    }


    static public class Text extends PropEditor {
        public Text(String title, IOnEdit<String> callback) {
            super(title);

            JTextField field = new JTextField();
            field.setPreferredSize(fieldDim);
            field.setMinimumSize(fieldDim);
            field.setMaximumSize(fieldDim);
            add(field);

        }
    }

    static public class Select<T> extends PropEditor {
        private JComboBox comboBox;
        private IOnSelect<T> callback;

        public Select(String title, IOnSelect<T> callback) {
            super(title);
            this.callback = callback;

            comboBox = new JComboBox();
            comboBox.setPreferredSize(fieldDim);
            comboBox.setMinimumSize(fieldDim);
            comboBox.setMaximumSize(fieldDim);

            refreshButtonsDef();

            add(comboBox);
        }

        void refreshButtonsDef() {
            Map<T, String> array = callback.getMap();

            for (Map.Entry<T, String> entry : array.entrySet()) {

                comboBox.addItem(new Item(entry.getKey(), entry.getValue()));
            }
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

    static public class Radio<T> extends PropEditor {
        private ButtonGroup group;
        private IOnSelect<T> callback;

        public Radio(String title, IOnSelect<T> callback) {
            super(title);
            this.callback = callback;

            group = new ButtonGroup();

            refreshButtonsDef();
        }

        void refreshButtonsDef() {
            Map<T, String> array = callback.getMap();

            for (Map.Entry<T, String> entry : array.entrySet()) {

                JRadioButton btn = new JRadioButton();
                btn.setText(entry.getValue());

                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        callback.set(entry.getKey());
                    }
                });

                group.add(btn);

                add(btn);
            }
        }
    }
}
