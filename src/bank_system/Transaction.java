package bank_system;

import java.util.Date;

public class Transaction {
    private long id;
    private long timeStamp;

    private long userId;
    private TransactionType type;
    private Integer amount;

    Long balance;
    Boolean result;

    Transaction(long timeStamp, long userId, TransactionType type, Integer amount) {
        this.id = (timeStamp >> 32) ^ userId;
        this.timeStamp = timeStamp;

        this.userId = userId;
        this.type = type;
        this.amount = amount;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    long getTimeStamp() {
        return timeStamp;
    }

    long getUserId() {
        return userId;
    }

    TransactionType getType() {
        return type;
    }

    Integer getAmount() {
        return amount;
    }
}
