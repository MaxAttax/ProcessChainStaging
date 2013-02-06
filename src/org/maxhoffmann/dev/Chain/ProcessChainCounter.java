package org.maxhoffmann.dev.Chain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.maxhoffmann.dev.object.ProcessChainObject;

public class ProcessChainCounter {

	private static final Logger LOGGER = Logger
			.getLogger(ProcessChainCounter.class);

	public ProcessChainCounter() {
	}

	/**
	 * The following methods performs process Chain Operations. The input "generated chains" is used to calculate
	 * distribution of the different chains.
	 * 
	 * @param generatedChains
	 */

	public Set<ProcessChainObject> ProcessChainOperations(
			ArrayList<String> generatedChains, int sortAlgorithm, int numMainChains) {
		
		final int numberOfMainChains = numMainChains;
		final int sortByNumber = sortAlgorithm;
		final double mainAmountChains = 0.9;
		final double minAmountMainChain = 0.1;

		// Counter for all process chains
		ArrayList<Integer> countProcessChains = new ArrayList<Integer>();

		// Counter for process chains of all chain length
		ArrayList<Integer> countLengthProcessChains = new ArrayList<Integer>();

		// Counter for process chains of a certain length, which is determined
		// in the optimization algorithm
		ArrayList<Integer> countProcessChainsLength = new ArrayList<Integer>();

		// Counter for process chains, that contain one of the distinct
		// determined process chains
		ArrayList<Integer> countContainProcessChains = new ArrayList<Integer>();

		// Counter for Sub Process Chains within the distinctive determined
		// process chains
		ArrayList<Integer> countSubProcessChains = new ArrayList<Integer>();

		// Counter for the number of each distinctive chain which can be found
		// within all listed chains without counting the distinctive chains
		// itself
		ArrayList<Integer> countContainChainsDiff = new ArrayList<Integer>();

		// Counter for Sub Process Chains within the different process chains
		// without counting the chains itself
		ArrayList<Integer> countSubChainsDiff = new ArrayList<Integer>();

		// The chain counter types like above, just counting the chains of a
		// certain length
		ArrayList<Integer> countContainProcessChainsLength = new ArrayList<Integer>();
		ArrayList<Integer> countSubProcessChainsLength = new ArrayList<Integer>();
		ArrayList<Integer> countSubChainsDiffLength = new ArrayList<Integer>();
		ArrayList<Integer> countContainChainsDiffLength = new ArrayList<Integer>();

		// List of distinct process chains of a certain length (no duplicates)
		ArrayList<String> processChainsLength = new ArrayList<String>();

		// List containing all available process chains including duplicates
		ArrayList<String> listedChains = new ArrayList<String>();

		// List containing distinct process chains (no duplicates)
		ArrayList<String> processChains = new ArrayList<String>();

		// Reading output of the generated process chains
		listedChains = generatedChains;
		int n = 0;

		/**
		 * Determination of the maximum chain length within all process chains.
		 */

		int maxChainLength = 0;
		for (int m = 0; m < listedChains.size(); m++) {
			int currentChainLength = listedChains.get(m).length();
			if (currentChainLength > maxChainLength) {
				maxChainLength = currentChainLength;
			}
		}

		/**
		 * Conversion of character to chain length (number of production entities in a chain).
		 */

		int maxProcessChainLength = (maxChainLength + 1) / 5;
		LOGGER.info("Maximum Chain Length: " + maxProcessChainLength + "\n");

		/**
		 * Initialization of the counter list for each chain length.
		 */

		for (int c = 0; c < maxProcessChainLength; c++) {
			countLengthProcessChains.add(c, 0);
		}

		/**
		 * Calculation of the distinctive process chains.
		 */

		for (int m = 0; m < listedChains.size(); m++) {
			if (processChains.contains(listedChains.get(m)) == false) {
				processChains.add(n, listedChains.get(m));
				n++;
			}
		}

		/**
		 * Determination of the number of each distinctive process chain within all process chains. The routine counts
		 * all duplicates of process chains for each type.
		 */

		for (n = 0; n < processChains.size(); n++) {
			int counter = 0;
			for (int m = 0; m < listedChains.size(); m++) {
				if (processChains.get(n).equals(listedChains.get(m)) == true) {
					counter++;
				}
			}
			countProcessChains.add(n, counter);
		}

		/**
		 * This algorithm counts the total number of all process chains for each length. For example: number of 1-link
		 * chains, number of 2-link chains, etc. within the whole process chain list.
		 */

		for (int m = 0; m < listedChains.size(); m++) {
			int index = ((listedChains.get(m).length() + 1) / 5) - 1;
			countLengthProcessChains.set(index,
					countLengthProcessChains.get(index) + 1);
		}

		// List containing the percentage values of the chains of each length
		ArrayList<Float> percentChainCount = new ArrayList<Float>();

		/**
		 * Calculation of the percentage amount of each chain-length category compared to all chains.
		 */

		LOGGER.info("Anzahl der Prozessketten:\n");
		for (int c = 0; c < maxProcessChainLength; c++) {
			percentChainCount.add((float) countLengthProcessChains.get(c)
					/ listedChains.size());
			LOGGER.info((c + 1) + "er-Kette:\t"
					+ countLengthProcessChains.get(c)
					+ "\t Anteil an Gesamtzahl:  " + percentChainCount.get(c)
					* 100 + " %");
		}
		LOGGER.info("\nTotal number of chains: " + listedChains.size() + "\n");

		// Initialization of cumulated percentage value of all chains smaller or
		// equal a certain chain length.
		double cumulatedLengthPercentage = 0;

		// Counter variable in order to determine the longest chain with a
		// sufficient amount within all process chains.
		int chainCounter = 1;

		/**
		 * The percentage of each chain type (length) is cumulated until they make up more than 90 % of all chains. For
		 * example, if the 1-, 2-, 3-, 4- and 5-link chains together make up more than 90 % percent, break up.
		 */

		while (cumulatedLengthPercentage < mainAmountChains) {
			percentChainCount.add((float) countLengthProcessChains
					.get(chainCounter - 1) / listedChains.size());
			cumulatedLengthPercentage = cumulatedLengthPercentage
					+ percentChainCount.get(chainCounter - 1);
			chainCounter++;
		}

		LOGGER.info("Nach der " + (chainCounter - 1) + ". Kette: Kumulierte "
				+ cumulatedLengthPercentage * 100 + " %");
		LOGGER.info("Anteil der " + (chainCounter - 1) + ". Kette: "
				+ percentChainCount.get(chainCounter - 2) * 100 + " %\n");

		/**
		 * The longest chain type of the cumulated chains is taken in order to generate the main process chains.
		 * Condition: The longest chain type make up more than 10 % of all chains, because it has to be representative.
		 * If for example, the 5-link chain is the last chain of the cumulated chains until 90 %, but makes up less than
		 * 10 %, than the 4-link chain is taken in order to generate the main process chains, and so on...
		 */

		// index of the List that contains the percentage values of each chain length
		int actualPercentageIndex = 0;

		// Declaration of the actual percentage value according to the index of the percentage list.
		float actualPercentage = 0;

		// Variable in order to search for the percentage value that fits to the given conditions.
		int arrayNumber = 1;

		LOGGER.info("Start-Kette:\t"
				+ percentChainCount.get(chainCounter - 1 - arrayNumber));

		// Initialization of the actual percentage value
		actualPercentage = percentChainCount
				.get(chainCounter - 1 - arrayNumber);

		// Initialization of the actual percentage list index
		actualPercentageIndex = chainCounter - arrayNumber - 1;

		while (percentChainCount.get(chainCounter - 1 - arrayNumber) < minAmountMainChain) {
			arrayNumber++;
			// if no entry is higher than 10%, take the maximum value
			if (chainCounter - 1 - arrayNumber < 0) {
				Float maxPercentValue = Collections.max(percentChainCount);
				actualPercentage = maxPercentValue;
				// if the maximum value is taken, find the according index
				for (int c = 0; c < maxProcessChainLength; c++) {
					if (percentChainCount.get(c) == actualPercentage) {
						actualPercentageIndex = c;
					}
				}
				break;
			}

			// index and percentage values are determined
			actualPercentageIndex = chainCounter - arrayNumber - 1;

			// Percentage value after the run of the iteration process.
			actualPercentage = percentChainCount.get(actualPercentageIndex);
		}

		LOGGER.info("End-Kette:\t" + actualPercentage + "\n\n");
		LOGGER.info("Actual Percentage Index: " + (actualPercentageIndex + 1)
				+ "\n");

		/*
		 * Generation of a list that contains all process chain of the determined length (no duplicates).
		 */

		for (n = 0; n < processChains.size(); n++) {
			int chainLength = (processChains.get(n).length() + 1) / 5;
			if (chainLength == (actualPercentageIndex + 1)) {
				processChainsLength.add(processChains.get(n));
			}
		}

		/**
		 * count the number of process chains, that match the process chains of a certain length within all listed
		 * chains.
		 */

		for (int m = 0; m < processChainsLength.size(); m++) {
			int counter = 0;
			for (int j = 0; j < listedChains.size(); j++) {
				if (processChainsLength.get(m).equals(listedChains.get(j)) == true) {
					counter++;
				}
			}
			countProcessChainsLength.add(m, counter);
		}

		/**
		 * countContainPrcoessChainsLength - how many times can distinctive chains of a certain length be found within
		 * all process chains countSubProcessChainsLength - how many of the listed chains can be found within each
		 * distinctive process chain countContainChainsDiffLength - counterSub excluding the chains itself
		 * countSubChainsDiffLength - counterContain excluding equal chains
		 */

		for (int m = 0; m < processChainsLength.size(); m++) {
			int counterContain = 0;
			int counterSub = 0;
			for (int j = 0; j < listedChains.size(); j++) {
				if (listedChains.get(j).contains(processChainsLength.get(m)) == true) {
					counterContain++;
				}
				if (processChainsLength.get(m).contains(listedChains.get(j)) == true) {
					counterSub++;
				}
			}
			countContainProcessChainsLength.add(m, counterContain);
			countSubProcessChainsLength.add(m, counterSub);
			countContainChainsDiffLength.add(m, counterContain
					- countProcessChainsLength.get(m));
			countSubChainsDiffLength.add(m, counterSub
					- countProcessChainsLength.get(m));
		}

		LOGGER.info("Prozessketten-Nr.:      Count \tContain\t Sub\t DiffCon  DiffSub\n");
		for (int j = 0; j < processChainsLength.size(); j++) {
			LOGGER.info((j + 1) + ".\tProzesskette:\t"
					+ countProcessChainsLength.get(j) + "\t"
					+ countContainProcessChainsLength.get(j) + "\t "
					+ countSubProcessChainsLength.get(j) + "\t "
					+ countContainChainsDiffLength.get(j) + "\t  "
					+ countSubChainsDiffLength.get(j) + "\t  "
					+ processChainsLength.get(j));
		}

		LOGGER.info("\n");

		SortedSet<ProcessChainObject> setSortedChains = new TreeSet<>();
		Set<ProcessChainObject> mainChains = new TreeSet<>();

		int i = 0;

		ProcessChainObject.setSortByNumber(sortByNumber);

		for (int j = 0; j < processChainsLength.size(); j++) {
			ProcessChainObject theProcessChain = new ProcessChainObject(
					processChainsLength.get(j),
					countProcessChainsLength.get(j),
					countContainProcessChainsLength.get(j),
					countSubProcessChainsLength.get(j),
					countContainChainsDiffLength.get(j),
					countSubChainsDiffLength.get(j));
			setSortedChains.add(theProcessChain);
		}
		
		for (ProcessChainObject orderedProcessChain : setSortedChains) {
			if ( i < numberOfMainChains ) {
				mainChains.add(orderedProcessChain);
				i++;
			}
		}

		// BEGIN ReadDataFromProcessChainObject
		LOGGER.info("Sorted ProcessChains:   Count \tContain\t Sub\t DiffCon  DiffSub\n");
		for (ProcessChainObject o : setSortedChains) {
			LOGGER.info("\t\t\t" + o.getNumber() + "\t" + o.getCountContain()
					+ "\t " + o.getCountSub() + "\t " + o.getCountContainDiff()
					+ "\t  " + o.getCountSubDiff() + "\t  "
					+ o.getProcessChain());
		}
		LOGGER.info("\n");
		// END ReadDataFromProcessChainObject

		for (n = 0; n < processChains.size(); n++) {
			int counterContain = 0;
			int counterSub = 0;
			for (int m = 0; m < listedChains.size(); m++) {
				if (listedChains.get(m).contains(processChains.get(n)) == true) {
					counterContain++;
				}
				if (processChains.get(n).contains(listedChains.get(m)) == true) {
					counterSub++;
				}
			}
			countContainProcessChains.add(n, counterContain);
			countSubProcessChains.add(n, counterSub);
			countContainChainsDiff.add(n,
					counterContain - countProcessChains.get(n));
			countSubChainsDiff.add(n, counterSub - countProcessChains.get(n));
		}

		/**
		 * for ( int m = 0; m < listedChains.size(); m++ ) { int counterContainBreak = 0; for ( n = 0; n <
		 * processChains.size(); n++ ) { if ( listedChains.get(m).contains(processChains.get(n)) == true ) {
		 * counterContainBreak++; } countContainBreakProcessChains.add(n,counterContainBreak); } }
		 */
		
		LOGGER.info("Prozessketten:\n");
		for (n = 0; n < 10; n++) {
			LOGGER.info((n + 1) + ".\tProzesskette:\t"
					+ countProcessChains.get(n) + "\t"
					+ countContainProcessChains.get(n) + "\t "
					+ countSubProcessChains.get(n) + "\t "
					+ countContainChainsDiff.get(n) + "\t  "
					+ countSubChainsDiff.get(n) + "\t  " + processChains.get(n));
		}
		LOGGER.info("\n...\t...\t...\tinsgesamt " + processChains.size() + " Ketten.");
		
		return mainChains;
	}
}