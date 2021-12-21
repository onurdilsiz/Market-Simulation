
package executable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

import elements.*;

public class Main {
	public static Random myRandom=new Random();
	public static void main(String[] args) {
 		Trader[] traders;
		
		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
		PrintStream outstream1;
		try {
	        outstream1 = new PrintStream(outFile);
	}catch(FileNotFoundException e2) {
	        e2.printStackTrace();
	        return;
	}

		Scanner reader;
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			outstream1.close();
			return;
		}
		
		
		int invalidQueries=0;
		int randomseed=reader.nextInt();
		myRandom.setSeed(randomseed);
		int transactionfee=reader.nextInt();
		Market market=new Market(transactionfee);
		
		int numofUsers=reader.nextInt();
		int numofQueries=reader.nextInt();
		
		traders=new Trader[numofUsers];
		
		int currentuser=0;
		while(currentuser<numofUsers) {
			double dollar=reader.nextDouble();
			double coins=reader.nextDouble();
			Trader newTrader=new Trader(dollar,coins);
			traders[currentuser]=newTrader;
			market.traders.add(newTrader);
			
			currentuser++;
		}
		int currentquery=0;
		
		while(currentquery<numofQueries) {
			int decider=reader.nextInt();
			//Trader specific queries:
			//give buying order of specific price:
			if(decider==10) {
				int id=reader.nextInt();
				double price=reader.nextDouble();
				double amount=reader.nextDouble();
				BuyingOrder buyingO=new BuyingOrder(id,amount,price);
				if(traders[id].buy(amount,price,market)==1) {
				market.giveBuyOrder(buyingO);
				}else {
					invalidQueries++;	
				}
			}
			//give buying order of market price:
			else if(decider==11) {
				int id=reader.nextInt();
				double amount=reader.nextDouble();
				if(market.marketprice()!=0) {
					BuyingOrder buyingO=new BuyingOrder(id,amount,market.marketprice());
					if(traders[id].buy(amount,market.marketprice(),market)==1) {
						market.giveBuyOrder(buyingO);
						}else {
							invalidQueries++;	
						}		}
				else {
					invalidQueries++;
				}
						}
			//give selling order of specific price
			else if(decider==20) {
				int idS=reader.nextInt();
				double priceS=reader.nextDouble();
				double amountS=reader.nextDouble();
				SellingOrder sellingO=new SellingOrder(idS,amountS,priceS);
				if(traders[idS].sell(amountS, priceS, market)==1) {
					market.giveSellOrder(sellingO);
				}else {
					invalidQueries++;
				}
				
			}
			//give selling order of market price:
			else if(decider==21) {
				int id=reader.nextInt();				
				double amount=reader.nextDouble();
				if(market.marketpricesell()!=0) {
					SellingOrder sellingO=new SellingOrder(id,amount,market.marketpricesell());
					if(traders[id].sell(amount, market.marketpricesell(), market)==1) {
						market.giveSellOrder(sellingO);
					}else {
						invalidQueries++;
				}
					}else {
					invalidQueries++;
				}
			}
			//deposit a certain amount of dollars to wallet:
			else if(decider==3) {
				int id=reader.nextInt();				
				double amount=reader.nextDouble();
				traders[id].getWallet().deposit(amount);
			}
			
			//withdraw a certain amount of dollars from wallet
			else if(decider==4) {
				int id=reader.nextInt();				
				double amount=reader.nextDouble();
				if(traders[id].getWallet().getDollars()>=amount) {
					traders[id].getWallet().withdraw(amount);}
				else {
					invalidQueries++;
				}
			}
			
			//print wallet status:
			else if(decider==5) {
				int id=reader.nextInt();
				outstream1.println("Trader "+id+":"+traders[id].getWallet().toString());
			}
				
			//SYSTEM QUERIES
			//give rewards to all traders
			else if(decider==777) {
				if(!market.traders.isEmpty()) {
				for(Trader each:traders) {
					double x=myRandom.nextDouble()*10;
					each.getWallet().depositcoins(x);
				}				}
			}
			
			//make open market operation:
			else if(decider==666) {
				double price=reader.nextDouble();
				market.makeOpenMarketOperation(price,market.traders);
			}
			
			//print the current market size:
			else if(decider==500) {
				outstream1.println("Current market size: "+market.currentMarketSize());				
			}
			
			//print number of successful transactions
			else if(decider==501) {
				outstream1.println("Number of successful transactions: "+market.numTransactions());
				
			}
			
			//print the number of invalid queries:
			else if(decider==502) {
				outstream1.println("Number of invalid queries: "+invalidQueries);
				
			}
			
			//print the current prices:
			else if(decider==505) {
				outstream1.println("Current prices: "+market.currentPrices());
			}
			
			//print all traders’ wallet status
			else if(decider==555) {
				for(int i =0;i<traders.length;i++) {
					outstream1.println("Trader "+i+": "+traders[i].getWallet().toString());
				}	
			}
			
		
			market.checkTransactions(market.traders);
			currentquery++;
		}
		
		outstream1.close();
		reader.close();
		
	}

}