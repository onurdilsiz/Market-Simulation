package elements;
/**
 * Selling order class child of order 
 * @author onurd
 *
 */
public class SellingOrder extends Order  implements Comparable<SellingOrder>{
/**
 * constructor of selling order
* @param traderID id of trader
	 * @param amount order amount
	 * @param price price of order
 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
		// TODO Auto-generated constructor stub
	}

/**
 * compare to method to order queue
 */
	@Override
	public int compareTo(SellingOrder o) {
		// TODO Auto-generated method stub
		if(this.getPrice()-o.getPrice()<0) {
			return -1;}
		else if(this.getPrice()-o.getPrice()==0){
			return this.getTraderID()-o.getTraderID();
			
		}else  {
			return 1;
		}
		
	}
	/**
	 * toString method of SellingOrder
	 */
	public String toString() {
		return this.getTraderID()+" "+ this.getPrice()+" "+this.getAmount()+"   ";
	}
	

}
