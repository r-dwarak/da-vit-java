import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class BankApp extends JFrame {
    private final Account account;
    private final JLabel balanceLabel;
    private final JTextField amountField;
    private final JTextArea messageArea;
    private final NumberFormat currencyFmt = NumberFormat.getCurrencyInstance();

    public BankApp(Account account) {
        super("Local Bank — Account Manager");
        this.account = account;

        balanceLabel = new JLabel(formatBalance(), SwingConstants.RIGHT);
        balanceLabel.setFont(balanceLabel.getFont().deriveFont(Font.BOLD, 16f));

        amountField = new JTextField(10);
        amountField.setToolTipText("Enter amount, e.g. 250.00");

        JButton viewBtn = new JButton(new AbstractAction("View Balance") {
            @Override public void actionPerformed(ActionEvent e) { updateBalance("Balance refreshed."); }
        });

        JButton depositBtn = new JButton(new AbstractAction("Deposit") {
            @Override public void actionPerformed(ActionEvent e) { onDeposit(); }
        });

        JButton withdrawBtn = new JButton(new AbstractAction("Withdraw") {
            @Override public void actionPerformed(ActionEvent e) { onWithdraw(); }
        });

        messageArea = new JTextArea(4, 30);
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JPanel top = new JPanel(new BorderLayout(10, 10));
        top.add(new JLabel("Current Balance:"), BorderLayout.WEST);
        top.add(balanceLabel, BorderLayout.CENTER);

        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5,5,5,5);
        gc.gridx = 0; gc.gridy = 0; gc.anchor = GridBagConstraints.EAST;
        center.add(new JLabel("Amount:"), gc);
        gc.gridx = 1; gc.anchor = GridBagConstraints.WEST;
        center.add(amountField, gc);

        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 2;
        JPanel buttons = new JPanel();
        buttons.add(viewBtn);
        buttons.add(depositBtn);
        buttons.add(withdrawBtn);
        center.add(buttons, gc);

        JPanel bottom = new JPanel(new BorderLayout(5,5));
        bottom.add(new JLabel("Transaction Messages:"), BorderLayout.NORTH);
        bottom.add(new JScrollPane(messageArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

        setLayout(new BorderLayout(10,10));
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void onDeposit() {
        try {
            BigDecimal amt = parseAmount();
            account.deposit(amt);
            updateBalance("Deposited " + currencyFmt.format(amt) + " successfully.");
            amountField.setText("");
        } catch (IllegalArgumentException ex) {
            showError("Invalid deposit: " + ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected error during deposit: " + ex.getMessage());
        }
    }

    private void onWithdraw() {
        try {
            BigDecimal amt = parseAmount();
            account.withdraw(amt);
            updateBalance("Withdrew " + currencyFmt.format(amt) + " successfully.");
            amountField.setText("");
        } catch (IllegalArgumentException ex) {
            showError("Invalid withdrawal: " + ex.getMessage());
        } catch (InsufficientFundsException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected error during withdrawal: " + ex.getMessage());
        }
    }

    private BigDecimal parseAmount() {
        String text = amountField.getText().trim();
        if (text.isEmpty()) throw new IllegalArgumentException("Amount is required.");
        try {
            BigDecimal amt = new BigDecimal(text).setScale(2, BigDecimal.ROUND_HALF_UP);
            if (amt.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be > 0.");
            return amt;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Enter a valid number (e.g., 150.00).");
        }
    }

    private void updateBalance(String msg) {
        balanceLabel.setText(formatBalance());
        appendMessage("# " + msg + " New balance: " + formatBalance() + ".");
    }

    private void showError(String msg) {
        appendMessage("#" + msg + " Current balance: " + formatBalance() + ".");
    }

    private void appendMessage(String msg) {
        messageArea.append(msg + System.lineSeparator());
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    private String formatBalance() {
        return currencyFmt.format(account.getBalance());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankApp(new Account()).setVisible(true));
    }
}
