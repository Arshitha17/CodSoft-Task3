import java.awt.*;
import java.awt.event.*;

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= account.getBalance()) {
            account.decreaseBalance(amount);
            System.out.println("Withdrawal successful. Remaining balance: " + account.getBalance());
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            account.increaseBalance(amount);
            System.out.println("Deposit successful. New balance: " + account.getBalance());
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: " + account.getBalance());
    }

    public BankAccount getAccount() {
        return account;
    }
}

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void increaseBalance(double amount) {
        balance += amount;
    }

    public void decreaseBalance(double amount) {
        balance -= amount;
    }
}

class ATMGUI extends Frame implements ActionListener {
    private TextField amountField;
    private TextArea outputArea;
    private ATM atm;

    private double withdrawnAmount;
    private double depositedAmount;

    public ATMGUI(ATM atm) {
        this.atm = atm;

        Label amountLabel = new Label("Amount:");
        amountField = new TextField(10);
        Button withdrawButton = new Button("Withdraw");
        Button depositButton = new Button("Deposit");
        Button checkBalanceButton = new Button("Check Balance");

        withdrawButton.addActionListener(this);
        depositButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);

        outputArea = new TextArea(10, 30);
        outputArea.setEditable(false);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(amountLabel, gbc);

        gbc.gridx = 1;
        add(amountField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(withdrawButton, gbc);

        gbc.gridy = 2;
        add(depositButton, gbc);

        gbc.gridy = 3;
        add(checkBalanceButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(outputArea, gbc);

        setTitle("ATM Machine");
        setSize(500, 400);
        setVisible(true);
        setBackground(Color.LIGHT_GRAY);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Check Balance")) {
            atm.checkBalance();
            updateOutputArea();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid amount.");
            return;
        }

        if (command.equals("Withdraw")) {
            atm.withdraw(amount);
            withdrawnAmount += amount;
        } else if (command.equals("Deposit")) {
            atm.deposit(amount);
            depositedAmount += amount;
        }

        amountField.setText("");
        updateOutputArea();
    }

    private void updateOutputArea() {
        outputArea.setText("");
        outputArea.append("Current balance: " + atm.getAccount().getBalance() + "\n");
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000); // Initial balance
        ATM atm = new ATM(account);
        ATMGUI atmGUI = new ATMGUI(atm);
    }
}
