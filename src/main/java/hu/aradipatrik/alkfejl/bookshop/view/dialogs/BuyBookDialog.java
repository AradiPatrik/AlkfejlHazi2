package hu.aradipatrik.alkfejl.bookshop.view.dialogs;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.view.BookShopGUI;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * Az osztály az új könyv felvételénél megjelenõ dialógus.
 */
public class BuyBookDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2072911996817368496L;

    private BookShopGUI gui;

    // A dialógus azon vezérlõit melyekre szükség lesz az eseménykezelés során
    // osztályváltozóként definiáljuk
    private JTextField authorTextfield = new JTextField(10);
    private JTextField titleTextfield = new JTextField(10);
    private JSpinner yearSpinner = new JSpinner();
    private JSpinner priceSpinner = new JSpinner();
    private JSpinner pieceSpinner = new JSpinner();
    private JComboBox<String> categoryCombo = new JComboBox<String>(Labels.categories);
    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    public BuyBookDialog(BookShopGUI gui, boolean modal) {
        super(gui.getWindow(), modal);
        this.gui = gui;

        // A dialógus címének beállítása
        this.setTitle(Labels.buy_book);

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

        // A panel elrendezése mátrix, tetszõleges számú sor és 2 oszlop,
        // a cellák egyforma méretûek
        settingPanel.setLayout(new GridLayout(0, 2));

        // author
        settingPanel.add(new JLabel(Labels.author));
        settingPanel.add(this.authorTextfield);

        // title
        settingPanel.add(new JLabel(Labels.title));
        settingPanel.add(this.titleTextfield);

        // year
        settingPanel.add(new JLabel(Labels.year));
        settingPanel.add(this.yearSpinner);

        // category
        settingPanel.add(new JLabel(Labels.category));
        settingPanel.add(this.categoryCombo);

        // price
        settingPanel.add(new JLabel(Labels.price));
        settingPanel.add(this.priceSpinner);

        // piece
        settingPanel.add(new JLabel(Labels.piece));
        settingPanel.add(this.pieceSpinner);

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
            // Ha az OK gombot nyomták meg, akkor megpróbáljuk felvenni a
            // könyvet

            if (titleTextfield.getText().isEmpty()) {
                // Ha nem adtak meg címet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.book_title_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // létrehozzuk a könyvet
            Book book = new Book();

            book.setAuthor(authorTextfield.getText());
            book.setCategory(categoryCombo.getSelectedItem().toString());
            book.setPiece(Integer.parseInt(pieceSpinner.getValue().toString()));
            book.setPrice((int) priceSpinner.getValue());
            book.setTitle(titleTextfield.getText());
            book.setYear((int) yearSpinner.getValue());

            if (!gui.getController().addBook(book)) {
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.book_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                setVisible(false);
            }
        } else if (cancelButton == e.getSource()) {
            // cancel esetén egyszerûen bezárjuk az ablakot
            setVisible(false);
        }
    }

}
