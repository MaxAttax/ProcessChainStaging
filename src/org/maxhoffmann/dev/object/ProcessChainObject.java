package org.maxhoffmann.dev.object;

public class ProcessChainObject implements Comparable<ProcessChainObject> {

	private String processChain;
	private int number;
	private int countContain;
	private int countSub;
	private int countContainDiff;
	private int countSubDiff;

	private static int sortByNumber = 1;

	public ProcessChainObject() {
	}

	public ProcessChainObject(String processChain, int number,
			int countContain, int countSub, int countContainDiff,
			int countSubDiff) {
		this.processChain = processChain;
		this.number = number;
		this.countContain = countContain;
		this.countSub = countSub;
		this.countContainDiff = countContainDiff;
		this.countSubDiff = countSubDiff;
	}
	
	public ProcessChainObject(String processChain, int number,
			int countSub) {
		this.processChain = processChain;
		this.number = number;
		this.countSub = countSub;
	}

	public String getProcessChain() {
		return processChain;
	}

	public void setProcessChain(String processChain) {
		this.processChain = processChain;
	}

	public static void setSortByNumber(int sortByNumber) {
		ProcessChainObject.sortByNumber = sortByNumber;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getCountContain() {
		return countContain;
	}

	public void setCountContain(int countContain) {
		this.countContain = countContain;
	}

	public int getCountSub() {
		return countSub;
	}

	public void setCountSub(int countSub) {
		this.countSub = countSub;
	}

	public int getCountContainDiff() {
		return countContainDiff;
	}

	public void setCountContainDiff(int countContainDiff) {
		this.countContainDiff = countContainDiff;
	}

	public int getCountSubDiff() {
		return countSubDiff;
	}

	public void setCountSubDiff(int countSubDiff) {
		this.countSubDiff = countSubDiff;
	}

	/**
	 * Determines the comparison of the current object to the passed one.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ProcessChainObject o) {
		int i = 0;
		switch (sortByNumber) {
		case 1:
			if (this.number > o.number)
				i = -1;
			else
				i = 1;
			break;
		case 2:
			if (this.countContain > o.countContain)
				i = -1;
			else
				i = 1;
			break;
		case 3:
			if (this.countSub > o.countSub)
				i = -1;
			else
				i = 1;
			break;
		case 4:
			if (this.countContainDiff > o.countContainDiff)
				i = -1;
			else
				i = 1;
			break;
		case 5:
			if (this.countSubDiff > o.countSubDiff)
				i = -1;
			else
				i = 1;
			break;
		}
		return i;
	}
}