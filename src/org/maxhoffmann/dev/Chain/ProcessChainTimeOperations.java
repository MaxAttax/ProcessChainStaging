package org.maxhoffmann.dev.Chain;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class ProcessChainTimeOperations {
	
	private static final Logger LOGGER = Logger.getLogger(ProcessChainTimeOperations.class);
	
	public ProcessChainTimeOperations() {
	}
	
	public void chainTimeOperations(ArrayList<String> generatedChains, ArrayList<String> generatedChainTimes) {
		
		ArrayList<String> processChains = new ArrayList<String>();
		ArrayList<String> chainTimes = new ArrayList<String>();
		processChains = generatedChains;
		chainTimes = generatedChainTimes;
		
		for ( int i = 0; i < 100 /*chainTimes.size()*/; i++ ) {
			LOGGER.info((i+1) + ".\tProzesskette:\t" + processChains.get(i) + "\n\tProzesszeiten:\t" + chainTimes.get(i) + "\n");
		}
	
	}	
}