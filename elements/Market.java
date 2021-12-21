package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Market class
 * @author onurd
 *
 */
public class Market {
	private int transactionfee=0;
	private PriorityQueue<SellingOrder> sellingOrders;
	private PriorityQueue<BuyingOrder> buyingOrders;
	private ArrayList<Transaction> transactions ;
	public ArrayList<Trader>traders;
	/**
	 * constructor of market
	 * @param fee transaction fee
	 */
	public Market(int fee) {
		this.transactionfee=fee;
		this.sellingOrders=new PriorityQueue<SellingOrder>();
		this.buyingOrders=new PriorityQueue<BuyingOrder> ();
		this.traders=new ArrayList<Trader>();
		this.transactions=new ArrayList<Transaction> ();
	}
	
	/**
	 * @return market price which is the lowest price in selling orders
	 */
	public double marketprice() {
		if(sellingOrders.isEmpty()) {
			return 0;
			
		}else {
				return sellingOrders.peek().getPrice();
		}	
	}
	/**
	 * 
	 * @return market buying price which is the highest price in buying orders 
	 */
	public double marketpricesell() {
		if(buyingOrders.isEmpty()) {
			return 0;
		}else {
			return buyingOrders.peek().getPrice();
			
		}
	}
	/**
	 * 
	 * @return current prices and their average
	 *
	 */
	public String currentPrices() {
		double ave=0;
		double cpbuying=0;
		double cpselling=0;
		if(!sellingOrders.isEmpty()&&!buyingOrders.isEmpty()) {
			cpbuying=buyingOrders.peek().getPrice();
			cpselling=sellingOrders.peek().getPrice();
			ave=(cpbuying+cpselling)/2;
		}
		else if(!sellingOrders.isEmpty()&&buyingOrders.isEmpty()) {
			cpbuying=0;
			cpselling=sellingOrders.peek().getPrice();
			ave=cpselling;
		}
		else if(sellingOrders.isEmpty()&&!buyingOrders.isEmpty()) {
			cpselling=0;
			cpbuying=buyingOrders.peek().getPrice();
			ave=cpbuying;	
		}else if(sellingOrders.isEmpty()&&buyingOrders.isEmpty()) {
			cpselling=0;
			cpbuying=0;
			ave=0;
		}
		return String.format("%.5f", cpbuying)+" "+String.format("%.5f", cpselling)+" "+String.format("%.5f", ave);
		}
	
	/**
	 * 
	 * @param order selling order
	 * adds order to selling orders queue
	 */
	public void giveSellOrder(SellingOrder order) {
			sellingOrders.add(order);
	}
	/**
	 * 
	 * @param order buying order
	 * adds order to buying orders queue
	 */
	public void giveBuyOrder(BuyingOrder order) {
			buyingOrders.add(order);
		
	}
	/**
	 * 
	 * @param price wanted marketprice
	 * @param traders traders arraylist
	 * changes market price by doing necessary transactions
	 */
	public void makeOpenMarketOperation(double price,ArrayList<Trader>traders) {
		while(!(buyingOrders.peek().getPrice()< price&&sellingOrders.peek().getPrice() > price)) {
			BuyingOrder buyingO=buyingOrders.peek();
			SellingOrder sellingO=sellingOrders.peek();
			if(price<buyingO.getPrice()) {
				SellingOrder sellingO2=new SellingOrder(0,buyingO.getAmount(),buyingO.getPrice());
				sellingOrders.add(sellingO2);
				checkTransactions(traders);	
			}else if(price>=sellingO.getPrice()) {
				BuyingOrder buyingO2=new BuyingOrder(0,sellingO.getAmount(),sellingO.getPrice());
				buyingOrders.add(buyingO2);
				checkTransactions(traders);
				
			}
		}
	}
	/**
	 * 			
	 * @param traders traders arraylist
	 * checks if there should be any transactions and applies transactions
	 */
	public void checkTransactions(ArrayList<Trader>traders) {
		if(buyingOrders.isEmpty()==true ||sellingOrders.isEmpty()==true) {
			return;
			
		}
			while(!(buyingOrders.isEmpty() ||sellingOrders.isEmpty())&&this.marketpricesell()>=this.marketprice()) {
			
					BuyingOrder buyingO=buyingOrders.peek();
					SellingOrder sellingO=sellingOrders.peek();
					if(buyingO.getPrice()>=sellingO.getPrice()) {
						if(buyingO.getAmount()>sellingO.getAmount()) {
							traders.get(buyingO.getTraderID()).getWallet().removeBlockedDollars(sellingO.getPrice()*sellingO.getAmount(),(buyingO.getPrice()-sellingO.getPrice())*sellingO.getAmount());
							traders.get(sellingO.getTraderID()).getWallet().removeBlockedcoin(sellingO.getAmount());
							traders.get(buyingO.getTraderID()).getWallet().depositcoins(sellingO.getAmount());
							traders.get(sellingO.getTraderID()).getWallet().deposit(sellingO.getAmount()*sellingO.getPrice()*(double)(1-this.transactionfee/1000));
							transactions.add(new Transaction(new BuyingOrder(buyingO.getTraderID(),sellingO.getAmount(),buyingO.getPrice()),sellingO));
							sellingOrders.poll();
							double xa=(buyingO.getAmount()-sellingO.getAmount());
							buyingO.setAmount(xa);
						}else if(buyingO.getAmount()==sellingO.getAmount()) {
							traders.get(buyingO.getTraderID()).getWallet().removeBlockedDollars(sellingO.getPrice()*sellingO.getAmount(),(buyingO.getPrice()-sellingO.getPrice())*sellingO.getAmount());
							traders.get(sellingO.getTraderID()).getWallet().removeBlockedcoin(sellingO.getAmount());
							traders.get(buyingO.getTraderID()).getWallet().depositcoins(sellingO.getAmount());
							traders.get(sellingO.getTraderID()).getWallet().deposit((sellingO.getAmount()*sellingO.getPrice()*(1-(double)this.transactionfee/1000)));
							transactions.add(new Transaction(buyingO,sellingO));
							sellingOrders.poll();
							buyingOrders.poll();
						}else if(buyingO.getAmount()<sellingO.getAmount()) {
							traders.get(sellingO.getTraderID()).getWallet().removeBlockedcoin(buyingO.getAmount());
							traders.get(buyingO.getTraderID()).getWallet().removeBlockedDollars(sellingO.getPrice()*buyingO.getAmount(),(buyingO.getPrice()-sellingO.getPrice())*buyingO.getAmount());
							traders.get(buyingO.getTraderID()).getWallet().depositcoins(buyingO.getAmount());
							traders.get(sellingO.getTraderID()).getWallet().deposit(buyingO.getAmount()*sellingO.getPrice()*(double)(1-this.transactionfee/1000));
							transactions.add(new Transaction(buyingO,new SellingOrder(sellingO.getTraderID(),buyingO.getAmount(),sellingO.getPrice())));
							buyingOrders.poll();
							double xx=sellingO.getAmount()-buyingO.getAmount();
							sellingO.setAmount(xx);
							
						}
	
			}		
		}
				
			
	}
	/**
	 * 
	 * @return current market size in dollars and coins
	 */
	public String currentMarketSize() {
		double sumdollar=0;
		for(BuyingOrder each:buyingOrders) {
			sumdollar+=each.getAmount()*each.getPrice();
			
		}
		double sumqoins=0;
		for(SellingOrder each:sellingOrders) {
			sumqoins+=each.getAmount();
			
		}
		return  String.format("%.5f", sumdollar)+" "+String.format("%.5f", sumqoins); 
		
	}
	/**
	 * 
	 * @return number of transactions
	 */
	public int numTransactions() {
		return transactions.size();
	}



}