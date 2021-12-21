package elements;

public class Wallet {
	private double dollars;
	private double coins;
	private double blockedDollars;
	private double blockedCoins;
	/**
	 *  Wallet constructor with 2 parameters
	 * @param dollars dollars in wallet
	 * @param coins coins in wallet
	 */
	public Wallet(double dollars, double coins) {
		this.dollars=dollars;
		this.coins=coins;
	}
	/**
	 * 
	 * @return dollars in wallet
	 */
	public double getDollars() {
		return dollars;
	}
	/**
	 * 
	 * @return coins in wallet
	 */
	public double getCoins() {
		return coins;
	}
	/**
	 * deposits amount to the wallet
	 * @param amount amount  of dollars deposited
	 */
	public void deposit(double amount) {
		this.dollars+=amount;
	}
	/**
	 * withdraws amount from the wallet
	 * @param amount amount of dollars withdrawn
	 */
	public void withdraw(double amount) {
		this.dollars-=amount;
	}
	/**
	 * blocks dollars for buying
	 * @param amount amount of order
	 * @param price price of order
	 */
	public void buy(double amount,double price) {
		this.dollars-=amount*price;
		this.blockedDollars+=amount*price;
		
	}
	/**
	 * blocks coins for selling
	 * @param amount amount of coins
	 */
	public void sell(double amount) {
		this.coins-=amount;
		this.blockedCoins+=amount;
		
	}
	/**
	 * deposits coin to wallet
	 * @param amount amount of coins deposited
	 */
	public void depositcoins(double amount) {
		this.coins+=amount;
	}
	/**
	 * unblocks dollars
	 * @param amount amount spent
	 * @param amountdollar amount of dollars
	 */
	public void removeBlockedDollars(double amount,double amountdollar) {
		this.blockedDollars-=amount;
		this.blockedDollars-=amountdollar;
		this.dollars+=amountdollar;
	}
	/**
	 * unblocks coin
	 * @param amount amount of coins selled
	 */
	public void removeBlockedcoin(double amount) {
		this.blockedCoins-=amount;
	}
	
	/**
	 * toString method for walllet
	 */
	public String toString() {
		return String.format("%.5f",  (this.dollars+this.blockedDollars))+"$ "+ String.format("%.5f", (this.blockedCoins+this.coins))+"PQ";
		
	}
	
}
