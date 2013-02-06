package org.maxhoffmann.dev.Chain;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.maxhoffmann.dev.ProductionAnalysisAnnotation.ProductionOrderHistory;

public class ProcessChainTimeGeneration {
	
	private static final Logger LOGGER = Logger.getLogger(ProcessChainTimeGeneration.class);
	
	public ProcessChainTimeGeneration() {
	}
	
	public ArrayList<String> GenerateChainTimes(List<ProductionOrderHistory> pohInput) {
		
		List<ProductionOrderHistory> ProductionOrderData = pohInput;
		
		ArrayList<Double> processTimes = new ArrayList<Double>();
		ArrayList<Integer> orders = new ArrayList<Integer>();
		
		for ( ProductionOrderHistory poh : ProductionOrderData ) {
			orders.add(poh.getOrder().getOrderNo());
			processTimes.add(poh.getOperationTime());
		}

		LOGGER.info("\n\nProzessketten-Operation-Times:\n");
		
		ArrayList<String> chainTimes = new ArrayList<String>();
		
		int j = 0;
		String currentTimeLine = new String();
		
		for ( int i = 0; i < processTimes.size()-1; i++ ) {
			
			String currentTime = Double.toString(processTimes.get(i));
			
			if ( currentTimeLine.length() == 0 ) {
				currentTimeLine = currentTime;
			}
			
			if ( orders.get(i+1).equals(orders.get(i)) ) {
				currentTimeLine = currentTimeLine + "-" + processTimes.get(i+1);
			} else {
				chainTimes.add(j,currentTimeLine);
				currentTimeLine = "";
				j++;
			}
		}
		
		for ( j = 0; j < 100/*chainTimes.size()*/; j++ ) {
			LOGGER.info((j+1) + ".\t Prozesszeiten-Kette: \t" + chainTimes.get(j) );
		}
		LOGGER.info("\n");
		
		return chainTimes;
	}
	
}