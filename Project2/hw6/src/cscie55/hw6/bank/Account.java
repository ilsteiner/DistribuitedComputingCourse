package cscie55.hw6.bank;

public interface Account
{
    /**
     * Returns the identifier of this Account. Identifiers should be unique within a Bank.
     * @return the identifier of this Account.
     */
    int id();

    /**
     * Returns the balance of this Account.
     * @return the balance of this Account.
     */
    long balance();

    /**
     * Increase the balance by the given amount.
     * @param amount The amount being deposited.
     */
    void deposit(long amount);

    /**
     * Decrease the balance by the given amount.
     * @param amount The amount being withdrawn.
     * @throws InsufficientFundsException if amount exceeds balance.
     */
    void withdraw(long amount) throws InsufficientFundsException;
}
