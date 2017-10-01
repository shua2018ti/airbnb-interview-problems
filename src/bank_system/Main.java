package bank_system;

public class Main {
    public static void main(String[] args) {
        BankSystem bs = new BankSystem();
        System.out.println(bs.withdraw(0, 100, 0));  // false

        bs.deposit(0, 100, 1);
        bs.deposit(1, 250, 2);
        bs.withdraw(0, 30, 3);

        long[] r1 = bs.getStatement(0, 0, 2);  // [0, 100]

        bs.deposit(1, 5, 7);

        long[] r2 = bs.getStatement(1, 3, 9);  // [250, 255]

        return;
    }
}
