package elements;
/**
 * order class
 * @author onurd
 *
 */
public class Order{
	private double amount;
	private double price;

	private int traderID;
	/**
	 * 
	 * @return trader ID
	 */
	public int getTraderID() {
		return traderID;
	}
	/**
	 * constructor of order
	* @param traderID id of trader
	 * @param amount order amount
	 * @param price price of order
	 */
	public Order(int traderID, double amount, double
	price) {
		this.traderID=traderID;
		this.price=price;
		this.amount=amount;
	}
	/**
	 * compare to  method to order orders in queue
	 * @param o order 
	 * @return 0
	 */
	public int compareTo(Order o) {
		// TODO Auto-generated method stub
		return 0;
	}
	/**
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * setter for amount
	 * @param amount amount of order
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * 
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	
}

