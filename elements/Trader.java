package elements;



public class Trader {
	private int id;
	private Wallet wallet;
	/**
	 * constructor of Trader
	 * @param dollars dollars that trader has 
	 * @param coins coins that trader has
	 */
	public Trader(double dollars, double coins) {
		this.id=numberOfUsers;
		numberOfUsers++;
		this.wallet=new Wallet(dollars,coins);
	}
	/**
	 * 
	 * @param amount amount of order
	 * @param price price of order
	 * @param market market which orders exist
	 * @return 1 if it can sell, 0 if it can not
	 */
	public int sell(double amount, double price,
	Market market) {
		if(wallet.getCoins()<amount) {
			return 0;
		}else {
			wallet.sell(amount);
			return 1;
	}
	}
	/**
	 * 
		 * @param amount amount of order
	 * @param price price of order
	 * @param market market which orders exist
	 * @return 1 if it can buy,0 if it can not
	 */
	public int buy(double amount, double price, Market market) {
		if(amount*price>wallet.getDollars()) {
			return 0;
		}else {
			wallet.buy(amount, price);
			return 1;
			}
	}
	
	public static int numberOfUsers = 0;
	
	/**
	 * 
	 * @return id of trader
	 */
	public int getID() {
		return id;
	}
	/**
	 * 
	 * @return Wallet of trader
	 */
	public Wallet getWallet() {
		return wallet;
	}
}
