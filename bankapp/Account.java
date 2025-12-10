import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    private BigDecimal balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public synchronized BigDecimal getBalance() {
        return balance;
    }

    public synchronized void deposit(BigDecimal amount) {
        requirePositive(amount);
        balance = balance.add(amount);
    }

    public synchronized void withdraw(BigDecimal amount) throws InsufficientFundsException {
        requirePositive(amount);
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Withdrawal denied: insufficient funds.");
        }
        balance = balance.subtract(amount);
    }

    private void requirePositive(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be a positive number.");
        }
    }
}
