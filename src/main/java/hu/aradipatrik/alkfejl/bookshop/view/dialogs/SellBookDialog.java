package hu.aradipatrik.alkfejl.bookshop.view.dialogs;

import hu.aradipatrik.alkfejl.bookshop.model.bean.Book;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Customer;
import hu.aradipatrik.alkfejl.bookshop.model.bean.Purchase;
import hu.aradipatrik.alkfejl.bookshop.view.BookShopGUI;
import hu.aradipatrik.alkfejl.bookshop.view.Labels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * Az osztály az új vásárlás rögzítésénél megjelenõ dialógus.
 */
public class SellBookDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 2072911996817368496L;

    /**
     * Érték, mely {@link Book} választásának hiányát reprezentálja.
     */
    private static final String BOOK_NONE_SELECTED = "";
    private static final String CUSTOMER_NONE_SELECTED = "";

    private BookShopGUI gui;

    private JButton okButton = new JButton(Labels.ok);
    private JButton cancelButton = new JButton(Labels.cancel);

    private Map<String, Book> bookMap = new HashMap<String, Book>();
    private Book selectedBook;

    private Map<String, Customer> customerMap = new HashMap<String, Customer>();
    private Customer selectedCustomer;

    public SellBookDialog(BookShopGUI gui, boolean modal) {
        super(gui.getWindow(), modal);

        this.gui = gui;

        prepareDataStructure();

        // A dialógus címének beállítása
        this.setTitle(Labels.sell_book);

        // A beállításokat tartalmazó panel gyártása
        Component settingPanel = createSettingPanel();

        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel dialogPanel = createDialogPanel(settingPanel, buttonPanel);

        // A dialogPanelt rárakjuk a dialógusra
        getContentPane().add(dialogPanel);

        setPreferredSize(new Dimension(600, 300));

        // A dialógus megfelelõ méretének beállítása (a tartalmazott elemek alapján)
        pack();

        // A dialógust a BookShopGUI-hoz képest rajzolja ki
        setLocationRelativeTo(gui.getWindow());

        // Dialogus megjelenítése
        setVisible(true);
    }

    /**
     * A controlleren keresztül megkapott adatok struktúráját (lista) a view
     * szükségleteinek megfelelõen átalakítja.
     */
    private void prepareDataStructure() {
        List<Book> books = gui.getController().getBooks();

        for (Book book : books) {
            bookMap.put(book.getTitle(), book);
        }

        List<Customer> customers = gui.getController().getCustomers();

        for (Customer customer : customers) {
            customerMap.put(customer.getName(), customer);
        }
    }

    private Component createSettingPanel() {
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // a resizeWeigth property adja meg, hogy melyik panel mennyit kapjuk
        // a helybõl átméretezés esetén, 0: egyik, 1: másik, 0.5: egyenlõen
        splitPanel.setResizeWeight(0.5);
        splitPanel.add(getLeftSettingsPanel());
        splitPanel.add(getRightSettingsPanel());

        return splitPanel;
    }

    private Container getLeftSettingsPanel(){
        // a könyvválasztó combo összerakása
        JComboBox<String> bookCombo = new JComboBox<String>();

        bookCombo.setPreferredSize(new Dimension(100, 20));

        // a combo elemeinek beállítása
        bookCombo.addItem(BOOK_NONE_SELECTED);
        for (String bookTitle : bookMap.keySet()) {
            bookCombo.addItem(bookTitle);
        }

        JPanel bookComboPanel = new JPanel();
        bookComboPanel.add(bookCombo);

        // a könyv adatait mutató panel
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(0, 2));

        /* FIGYELEM!
         * A változók final-ök kell legyenek ahhoz, hogy el tudjuk érni az
         * ItemListenerbõl.
         */

        dataPanel.add(new JLabel(Labels.author));
        final JLabel authorLabel = new JLabel();
        dataPanel.add(authorLabel);

        dataPanel.add(new JLabel(Labels.title));
        final JLabel titleLabel = new JLabel();
        dataPanel.add(titleLabel);

        dataPanel.add(new JLabel(Labels.year));
        final JLabel yearLabel = new JLabel();
        dataPanel.add(yearLabel);

        dataPanel.add(new JLabel(Labels.category));
        final JLabel categoryLabel = new JLabel();
        dataPanel.add(categoryLabel);

        dataPanel.add(new JLabel(Labels.price));
        final JLabel priceLabel = new JLabel();
        dataPanel.add(priceLabel);

        dataPanel.add(new JLabel(Labels.piece));
        final JLabel pieceLabel = new JLabel();
        dataPanel.add(pieceLabel);

        dataPanel.add(new JLabel(Labels.ancient));
        final JLabel ancientLabel = new JLabel();
        dataPanel.add(ancientLabel);

        bookCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // egy elem kiválasztásakor 2 event keletkezik, egyik a régi
                // elem kijelöltségének megszünése, másik a új elem kijelölése
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedBookTitle = (String)e.getItem();

                    if (selectedBookTitle.equals(BOOK_NONE_SELECTED)) {
                        authorLabel.setText("");
                        titleLabel.setText("");
                        yearLabel.setText("");
                        categoryLabel.setText("");
                        priceLabel.setText("");
                        pieceLabel.setText("");
                        ancientLabel.setText("");
                    } else {
                        selectedBook = bookMap.get(selectedBookTitle);

                        authorLabel.setText(selectedBook.getAuthor());
                        titleLabel.setText(selectedBook.getTitle());
                        yearLabel.setText(Integer.toString(selectedBook.getYear()));
                        categoryLabel.setText(selectedBook.getCategory());
                        priceLabel.setText(Integer.toString(selectedBook.getPrice()));
                        pieceLabel.setText(Integer.toString(selectedBook.getPiece()));
                        ancientLabel.setText(Boolean.toString(selectedBook.isAncient()));
                    }
                }
            }
        });

        // a bal oldali settings panel összeállítása (combo alatti részletek)
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(bookComboPanel, BorderLayout.NORTH);
        panel.add(dataPanel, BorderLayout.CENTER);

        return panel;
    }

    private Container getRightSettingsPanel(){
        // a vásárlóválasztó combo összerakása
        JComboBox<String> customerCombo = new JComboBox<String>();

        customerCombo.setPreferredSize(new Dimension(100, 20));

        // a combo elemeinek beállítása
        customerCombo.addItem(CUSTOMER_NONE_SELECTED);
        for (String customerName : customerMap.keySet()) {
            customerCombo.addItem(customerName);
        }

        JPanel customerComboPanel = new JPanel();
        customerComboPanel.add(customerCombo);

        // a vásárló adatait mutató panel
        JPanel dataPanel = new JPanel();

        dataPanel.setLayout(new GridLayout(0, 2));

        dataPanel.add(new JLabel(Labels.name));
        final JLabel nameLabel = new JLabel();
        dataPanel.add(nameLabel);

        dataPanel.add(new JLabel(Labels.age));
        final JLabel ageLabel = new JLabel();
        dataPanel.add(ageLabel);

        dataPanel.add(new JLabel(Labels.female));
        final JLabel genderLabel = new JLabel();
        dataPanel.add(genderLabel);

        dataPanel.add(new JLabel(Labels.qualification));
        final JLabel qualificationLabel = new JLabel();
        dataPanel.add(qualificationLabel);

        customerCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedCustomerName = (String)e.getItem();

                    if (selectedCustomerName.equals(CUSTOMER_NONE_SELECTED)) {
                        qualificationLabel.setText("");
                        nameLabel.setText("");
                        genderLabel.setText("");
                        ageLabel.setText("");
                    } else {
                        selectedCustomer = customerMap.get(selectedCustomerName);

                        qualificationLabel.setText(selectedCustomer.getQualification());
                        nameLabel.setText(selectedCustomer.getName());
                        genderLabel.setText(Boolean.toString(selectedCustomer.isFemale()));
                        ageLabel.setText(Integer.toString(selectedCustomer.getAge()));
                    }
                }
            }
        });

        // a jobb oldali settings panel összeállítása (combo alatti részletek)
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.add(customerComboPanel, BorderLayout.NORTH);
        panel.add(dataPanel, BorderLayout.CENTER);

        return panel;
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

    private JPanel createDialogPanel(Component settingPanel, JPanel buttonPanel) {
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

            if (selectedBook == null || selectedCustomer == null) {
                // Ha nem adtak meg címet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.purchase_choose_book_and_customer,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Purchase purchase = new Purchase();

            purchase.setBook(selectedBook);
            purchase.setCustomer(selectedCustomer);

            if (!gui.getController().addPurchase(purchase)) {
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.purchase_failed,
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
