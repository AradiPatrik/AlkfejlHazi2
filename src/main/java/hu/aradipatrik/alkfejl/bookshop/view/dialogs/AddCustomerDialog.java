package hu.aradipatrik.alkfejl.bookshop.view.dialogs;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.view.BookShopGUI;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * Az osztály az új ügyfél felvételénél megjelenõ dialógus.
 */
public class AddCustomerDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2072911996817368496L;

    private BookShopGUI gui;

    // A dialógus azon vezérlõit melyekre szükség lesz az eseménykezelés során
    // osztályváltozóként definiáljuk
    private JTextField nameTextfield = new JTextField(10);
    private JSpinner ageSpinner = new JSpinner();
    private JRadioButton femaleRadio = new JRadioButton(Labels.female);
    private JRadioButton maleRadio = new JRadioButton(Labels.male);
    private JCheckBox granteeCheck = new JCheckBox();
    private JComboBox<String> qualificationCombo = new JComboBox<String>(Labels.qualifications);
    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    public AddCustomerDialog(BookShopGUI gui, boolean modal) {
        super(gui.getWindow(), modal);
        this.gui = gui;

        // A dialógus címének beállítása
        this.setTitle(Labels.add_customer);

        // A beállításokat tartalmazó panel gyártása
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel dialogPanel = createDialogPanel(settingPanel, buttonPanel);

        // A dialogPanelt rárakjuk a dialógusra
        getContentPane().add(dialogPanel);

        // A dialógus megfelelõ méretének beállítása (a tartalmazott elemek alapján)
        pack();

        // A dialógust a BookShopGUI-hoz képest rajzolja ki
        setLocationRelativeTo(gui.getWindow());

        // Dialogus megjelenítése
        setVisible(true);
    }

    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        // A panel elrendezése mátrix, 5 sor és 2 oszlop, a cellák egyforma méretûek
        settingPanel.setLayout(new GridLayout(5,2));

        // Az elsõ sorban egy név címke és egy szövegmezõ lesz
        settingPanel.add(new JLabel(Labels.name));
        settingPanel.add(this.nameTextfield);

        // A második sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.age));
        settingPanel.add(this.ageSpinner);

        // A harmadik sorban egy nem címke és két radiobutton lesz, amit egy
        // újabb panelen helyezünk el (gender_panel)
        // A gender_panelre jobbra zártan egymás után pakolhatunk vezérlõket
        settingPanel.add(new JLabel(Labels.gender));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        genderPanel.add(femaleRadio);
        genderPanel.add(maleRadio);

        // Alapértelmezésben a female van bekapcsolva
        femaleRadio.setSelected(true);

        // A female-male-bõl csak egy választható ki ezért egy ButtonGroupba rakjuk õket
        ButtonGroup group = new ButtonGroup();
        group.add(femaleRadio);
        group.add(maleRadio);

        // mivel a radio buttonnak alapértelmézesben nagyobb a magassága mint a
        // textfieldnek ezért átéllítjuk az y koordinátékat akkorára, mint a
        // textfieldek esetében
        // ellenkezõ esetben a textfield-ek is aránytalanul magasabbak lesznek
        // mivel GridLayout-unk van, ahol minden cella egyforma méretû
        maleRadio.setPreferredSize(new Dimension(
                maleRadio.getPreferredSize().width,
                nameTextfield.getPreferredSize().height));
        femaleRadio.setPreferredSize(new Dimension(
                femaleRadio.getPreferredSize().width,
                nameTextfield.getPreferredSize().height));
        settingPanel.add(genderPanel);

        // A 4. sorban lesz a kedvezményezettség
        settingPanel.add(new JLabel(Labels.grantee));
        settingPanel.add(granteeCheck);

        // A 5. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.qualification));
        settingPanel.add(qualificationCombo);

        return settingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // A panel elrendezése folytonos, középre igazítva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozzáadjuk az ok gombot, és figyelünk rá
        buttonPanel.add(okButton);
        okButton.addActionListener(this);

        // Hozzáadjuk a cancel gombot, és figyelünk rá
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(this);

        return buttonPanel;
    }

    private JPanel createDialogPanel(JPanel settingPanel, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // Középen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (okButton == e.getSource()) {
            // Ha az OK gombot nyomták meg, akkor megpróbáljuk felvenni az
            // ügyfelet

            if (nameTextfield.getText().isEmpty()) {
                // Ha nem adtak meg nevet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // létrehozzuk a customert
            Customer customer = new Customer();

            customer.setName(nameTextfield.getText());
            customer.setAge((Integer)ageSpinner.getValue());
            customer.setFemale(femaleRadio.isSelected());
            customer.setGrantee(granteeCheck.isSelected());
            customer.setQualification(qualificationCombo.getSelectedItem().toString());

            if (!gui.getController().addCustomer(customer)) {
                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.customer_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ha az addCustomer true-t ad vissza akkor bezárjuk a dialógust
                setVisible(false);
            }
        } else if (cancelButton == e.getSource()) {
            // cancel esetén egyszerûen bezárjuk az ablakot
            setVisible(false);
        }
    }

}
