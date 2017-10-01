package bank_system;

public class Account {
    private long id;
    private long timeStamp;

    private long userId;
    private long balance;

    Account(long userId, long timeStamp) {
        this.id = userId ^ (timeStamp >> 16);
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.balance = 0;
    }

    boolean deposit(Transaction transaction) {
        balance += transaction.getAmount();
        /* In fact, here should be doing some IO with database or files */

        transaction.balance = balance;
        transaction.result = true;
        return true;
    }

    public boolean withdraw(Transaction transaction) {
        /* Check if account balance is enough for withdrawing */
        if (balance < transaction.getAmount()) {
            return false;
        } else {
            balance -= transaction.getAmount();

            transaction.balance = balance;
            transaction.result = true;
            return true;
        }
    }
}
