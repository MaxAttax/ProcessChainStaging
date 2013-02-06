package org.maxhoffmann.dev.object;

import java.util.ArrayList;

public class ProcessChainEvaluation {

	ArrayList<String> currentMainChains;
	ArrayList<String> regularProcessChains;
	ArrayList<String> specialProcessChains;
	ArrayList<Integer> regularChains;
	ArrayList<Integer> specialChains;

	public ProcessChainEvaluation() {
	}

	public ProcessChainEvaluation(ArrayList<String> currentMainChains,
			ArrayList<String> regularProcessChains,
			ArrayList<String> specialProcessChains,
			ArrayList<Integer> regularChains, ArrayList<Integer> specialChains) {
		this.currentMainChains = currentMainChains;
		this.regularProcessChains = regularProcessChains;
		this.specialProcessChains = specialProcessChains;
		this.regularChains = regularChains;
		this.specialChains = specialChains;
	}

	public void setCurrentMainChains(ArrayList<String> currentMainChains) {
		this.currentMainChains = currentMainChains;
	}

	public ArrayList<String> getCurrentMainChains() {
		return currentMainChains;
	}
	
	public void setRegularProcessChains(ArrayList<String> regularProcessChains) {
		this.regularProcessChains = regularProcessChains;
	}
	
	public ArrayList<String> getRegularProcessChains() {
		return regularProcessChains;
	}
	
	public void setSpecialProcessChains(ArrayList<String> specialProcessChains) {
		this.specialProcessChains = specialProcessChains;
	}
	
	public ArrayList<String> getSpecialProcessChains() {
		return specialProcessChains;
	}

	public void setRegularChains(ArrayList<Integer> regularChains) {
		this.regularChains = regularChains;
	}

	public ArrayList<Integer> getRegularChains() {
		return regularChains;
	}

	public void setSpecialChains(ArrayList<Integer> specialChains) {
		this.specialChains = specialChains;
	}

	public ArrayList<Integer> getSpecialChains() {
		return specialChains;
	}

}