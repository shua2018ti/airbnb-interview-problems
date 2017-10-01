package bank_system;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 设计一个银行帐户系统，实现：
 * 存钱（帐户id，存钱数目，日期）
 * 取钱（帐户id，存钱数目，日期）
 * 查账（帐户id，起始日期，结束日期）： 只需要返回两个数值，一个是起始日期的balance，一个是结束日期的balance。
 * 描述就是这么多，剩下的自己发挥。钱的类型用integer，日期什么的自定义，我直接拿了integer
 */

public class BankSystem {


    private HashMap<Long, Account> accounts;
    private HashMap<Account, LinkedList<Transaction>> histories;

    public BankSystem() {
        this.accounts = new HashMap<>();
        this.histories = new HashMap<>();
    }

    public boolean deposit(long userId, int amount, long timestamp) {
        /* Check if parameter invalid */
        if (amount < 0 || timestamp < 0) return false;

        /* Create new or fetch old account */
        Account account = null;
        if (accounts.containsKey(userId)) {
            account = accounts.get(userId);
        } else {
            account = new Account(userId, timestamp);
            accounts.put(userId, account);
        }

        /* Instantiate a new transaction */
        Transaction transaction = new Transaction(timestamp, userId, TransactionType.DEPOSIT, amount);

        /* Perform deposit and keep a record */
        boolean result = account.deposit(transaction);
        LinkedList<Transaction> history = null;
        if (histories.containsKey(account)) {
            history = histories.get(account);
        } else {
            history = new LinkedList<>();
            histories.put(account, history);
        }
        history.addLast(transaction); // In fact here should be posting a transaction to database

        return result;
    }


    public boolean withdraw(long userId, int amount, long timestamp) {
        /* Find old account or return failure */
        if (accounts.containsKey(userId) == false)
            return false;
        Account account = accounts.get(userId);

        /* Instantiate a new transaction */
        Transaction transaction = new Transaction(timestamp, userId, TransactionType.WITHDRAW, amount);

        /* Perform withdraw and keep a record */
        boolean result = account.withdraw(transaction);
        LinkedList<Transaction> history = null;
        if (histories.containsKey(account)) {
            history = histories.get(account);
        } else {
            history = new LinkedList<>();
            histories.put(account, history);
        }
        history.addLast(transaction);

        return result;
    }

    public long[] getStatement(long userId, long startTime, long endTime) {
        /* Find the old account and its history */
        if (accounts.containsKey(userId) == false)
            return null; /* In fact, here should throw an exception */
        Account account = accounts.get(userId);
        if (histories.containsKey(account) == false)
            return null; /* See above */
        LinkedList<Transaction> history = histories.get(account);

        /* Find the oldest balance before startTime and oldest balance after before endTime */
        long[] statement = new long[2];

        Iterator<Transaction> iter = history.listIterator();
        Transaction cur = iter.next();
        while (cur != null) {
            long time = cur.getTimeStamp();

            if (time <= startTime) {
                statement[0] = cur.balance;
                statement[1] = cur.balance;

            } else if (time > startTime && time <= endTime) {
                statement[1] = cur.balance;

            } else {
                break;
            }

            if (iter.hasNext())
                cur = iter.next();
            else
                break;
        }


        /* Check if statement found */
        long[] finals = new long[2];
        finals[0] = statement[0];
        finals[1] = statement[1];
        return finals;
    }
}
