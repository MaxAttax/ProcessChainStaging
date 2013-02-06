package org.maxhoffmann.dev.Chain;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.maxhoffmann.dev.object.ProcessChainEvaluation;
import org.maxhoffmann.dev.object.ProcessChainObject;

public class ProcessChainReconfiguration {

	private static final Logger LOGGER = Logger
			.getLogger(ProcessChainReconfiguration.class);

	ProcessChainEvaluation evaluation;
	ArrayList<String> workingChains;
	ArrayList<String> regularProcessChains;
	ArrayList<String> specialProcessChains;

	ArrayList<String> listedChains;
	ArrayList<String> combinedChains = new ArrayList<String>();
	String combinedChain = new String();
	
	public ProcessChainReconfiguration() {
	}

	public ProcessChainReconfiguration(ProcessChainEvaluation evaluation,
			ArrayList<String> listedChains) {
		this.evaluation = evaluation;
		this.listedChains = listedChains;
	}
	
	public void setEvaluation(ProcessChainEvaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	public void setListedChains(ArrayList<String> listedChains) {
		this.listedChains = listedChains;
	}

	public void chainCombination() {

		int chainCounter = 1;
		workingChains = evaluation.getCurrentMainChains();
		regularProcessChains = evaluation.getRegularProcessChains();
		specialProcessChains = evaluation.getSpecialProcessChains();
		

		for (int i = 0; i < workingChains.size() - 1; i++) {
			for (int j = 1 + i; j < workingChains.size(); j++) {
				combinedChain = workingChains.get(i) + "-"
						+ workingChains.get(j);
				combinedChains.add(combinedChain);
				LOGGER.info(chainCounter + ". kombinierte Kette:\t" + "K"
						+ (i + 1) + "-" + "K" + (j + 1) + "\t" + combinedChain);
				chainCounter++;
			}
		}

		LOGGER.info("\n\n\t\t\t\t\t   listed   regular   special\n");
		for (String chain : workingChains) {
			int subChainCounter = 0;
			int subRegularCounter = 0;
			int subSpecialCounter = 0;
			for (String processChain : listedChains) {
				if (chain.contains(processChain)) {
					subChainCounter++;
				}
			}
			for (String processChain : regularProcessChains) {
				if (chain.contains(processChain)) {
					subRegularCounter++;
				}
			}
			for (String processChain : specialProcessChains) {
				if (chain.contains(processChain)) {
					subRegularCounter++;
				}
			}
			LOGGER.info(chain + "\t\t\t    " + subChainCounter + "\t     "
					+ subRegularCounter + "       " + subSpecialCounter);
		}

		LOGGER.info("\n\n\t\t\t\t\t   listed   regular   special\n");
		for (String chain : combinedChains) {
			int subChainCounter = 0;
			int subRegularCounter = 0;
			int subSpecialCounter = 0;
			for (String processChain : listedChains) {
				if (chain.contains(processChain)) {
					subChainCounter++;
				}
			}
			for (String processChain : regularProcessChains) {
				if (chain.contains(processChain)) {
					subRegularCounter++;
				}
			}
			for (String processChain : specialProcessChains) {
				if (chain.contains(processChain)) {
					subRegularCounter++;
				}
			}
			LOGGER.info(chain + "\t    " + subChainCounter + "\t     "
					+ subRegularCounter + "       " + subSpecialCounter);
		}
	}

	public Set<ProcessChainObject> chainReformation(int iterationNumber) {

		int distinctChainIndex = 0;
		int iterationNum = iterationNumber;
		
		String bestSpecialChain = new String();

		workingChains = evaluation.getCurrentMainChains();
		regularProcessChains = evaluation.getRegularProcessChains();
		specialProcessChains = evaluation.getSpecialProcessChains();

		ArrayList<String> distinctSpecialChains = new ArrayList<String>();

		/**
		 * Calculation of distinct special Chains
		 */

		for (String chain : specialProcessChains) {
			if (distinctSpecialChains.contains(chain) == false) {
				distinctSpecialChains.add(distinctChainIndex, chain);
				distinctChainIndex++;
			}
		}

		Set<ProcessChainObject> distinctChains = new TreeSet<>();
		
		for (String specialChain : distinctSpecialChains) {
			int countSpecialChain = 0;
			int subSpecialChain = 0;
			for (String chain : specialProcessChains) {
				if (specialChain.equals(chain)) {
					countSpecialChain++;
				}
				if (specialChain.contains(chain)) {
					subSpecialChain++;
				}
			}
			ProcessChainObject distinctChainObject = new ProcessChainObject();
			distinctChainObject.setNumber(countSpecialChain);
			distinctChainObject.setCountSub(subSpecialChain);
			distinctChainObject.setProcessChain(specialChain);
			distinctChains.add(distinctChainObject);
		}
		
		LOGGER.info("\n");
		for (ProcessChainObject distinctProcessChain : distinctChains) {
			LOGGER.info( distinctProcessChain.getNumber() + "\t"
					+ distinctProcessChain.getCountSub() + "\t"
					+ distinctProcessChain.getProcessChain());
		}
		
		for (ProcessChainObject distinctProcessChain : distinctChains) {
			bestSpecialChain = distinctProcessChain.getProcessChain();
			break;
		}
		
		workingChains.remove(workingChains.size() - 1 - iterationNum);
		workingChains.add(bestSpecialChain);
		
		Set<ProcessChainObject> currentMainChains = new TreeSet<>();
		for ( String chain : workingChains ) {
			ProcessChainObject processChainObject = new ProcessChainObject();
			processChainObject.setProcessChain(chain);
			currentMainChains.add(processChainObject);
		}
		
		return currentMainChains;

	}

}
