package elements;

public class Transaction {
	
	private SellingOrder sellingOrder;
	private BuyingOrder buyingOrder;
	/**
	 * constructor of Transaction class
	 * @param buyingOrder2 buying order
	 * @param sellingOrder2 selling order
	 */
	public Transaction(BuyingOrder buyingOrder2, SellingOrder sellingOrder2) {
		sellingOrder=sellingOrder2;
		buyingOrder=buyingOrder2;
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * toString method for transactions	
	 */
	public String toString() {
		return sellingOrder.toString()+buyingOrder.toString();
	}

}
