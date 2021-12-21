package elements;
/**
 * buying order class child of order
 * @author onurd
 *
 */

public class BuyingOrder extends Order implements Comparable<BuyingOrder>{
	/**
	 * constructor of buying order
	 * @param traderID id of trader
	 * @param amount order amount
	 * @param price price of order
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * compare to method to order orders in queue
	 */
	@Override
	public int compareTo(BuyingOrder o) {
		if(this.getPrice()-o.getPrice()>0) {
			return -1;}
		else if(this.getPrice()-o.getPrice()==0){
			return this.getTraderID()-o.getTraderID();
			
		}else  {
			return 1;
		}
	
	}
	/**
	 * toString method for order
	 */
	public String toString() {
		return getTraderID()+" "+getPrice()+" "+getAmount();
	}

	

}
