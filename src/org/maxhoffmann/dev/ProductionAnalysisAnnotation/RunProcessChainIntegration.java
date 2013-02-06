package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import org.maxhoffmann.dev.Chain.ProcessChainGeneration;
import org.maxhoffmann.dev.Chain.ProcessChainCounter;
import org.maxhoffmann.dev.Chain.ProcessChainMainOperations;
import org.maxhoffmann.dev.Chain.ProcessChainReconfiguration;
import org.maxhoffmann.dev.object.ProcessChainEvaluation;
import org.maxhoffmann.dev.object.ProcessChainObject;
// import org.maxhoffmann.dev.Chain.ProcessChainTimeGeneration;
// import org.maxhoffmann.dev.Chain.ProcessChainTimeOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RunProcessChainIntegration {

	public static void main(String[] args) {

		System.out.println("Let's start the Analysis!\n");

		ProjectDAO projectDAO = new ProjectDAO();
		MaterialDAO materialDAO = new MaterialDAO();
		OrderDAO orderDAO = new OrderDAO();
		ResourceGroupDAO resourceGroupDAO = new ResourceGroupDAO();
		ProductionOrderHistoryDAO productionOrderHistoryDAO = new ProductionOrderHistoryDAO();

		projectDAO.listProjects();
		materialDAO.listMaterial();
		orderDAO.listOrders();
		resourceGroupDAO.listResourceGroups();

		List<ProductionOrderHistory> pohResult = productionOrderHistoryDAO
				.listProductionOrderHistories();

		ProcessChainGeneration generator = new ProcessChainGeneration();
		ArrayList<String> generatedChains = generator
				.ProcessChainBuild(pohResult);

		/**
		 * sortAlgorithm - Possible values: 1 - Sort by total number chain 2 -
		 * Sort by the number of times the chain can be found within the listed
		 * chains 3 - Sort by the number of sub chains that can be found within
		 * the listed chains 4 - Sort by the number of contained chains
		 * excluding the process chains itself 5 - Sort by the number of sub
		 * chains excluding the process chains itself.
		 */

		int sortAlgorithm = 3;
		int numMainChains = 4;

		List<ProcessChainEvaluation> evaluationCollection = new ArrayList<ProcessChainEvaluation>();

		ProcessChainCounter chainCounter = new ProcessChainCounter();
		ProcessChainMainOperations operations = new ProcessChainMainOperations();

		
		Set<ProcessChainObject> currentWorkingChains = 
				chainCounter.ProcessChainOperations(generatedChains, sortAlgorithm, numMainChains);

		
		for ( int iterationNum = 0; iterationNum < numMainChains; iterationNum++) {
			
			operations.setMainChains(currentWorkingChains);
			operations.setGeneratedChains(generatedChains);
			operations.setIterationNumber(iterationNum);
			operations.setIterationMax(numMainChains - 1);
			
			ProcessChainEvaluation evaluation = operations.chainResults();

			evaluationCollection.add(evaluation);

			ProcessChainReconfiguration configuration = new ProcessChainReconfiguration();
			
			configuration.setEvaluation(evaluationCollection.get(evaluationCollection.size() - 1));
			configuration.setListedChains(generatedChains);
			// configuration.chainCombination();

			currentWorkingChains = configuration.chainReformation(iterationNum);
			
		}
		
		

		/*
		 * ProcessChainTimeGeneration timeGenerator = new
		 * ProcessChainTimeGeneration(); ArrayList<String> chainTimes =
		 * timeGenerator.GenerateChainTimes(pohResult);
		 * 
		 * ProcessChainTimeOperations timeOperations = new
		 * ProcessChainTimeOperations();
		 * timeOperations.chainTimeOperations(generatedChains, chainTimes);
		 */

		/*
		 * ProjectDAO projectDAO = new ProjectDAO();
		 * 
		 * long primaryIdProject001 = projectDAO.addProject("eins");
		 * projectDAO.addProject("zwei"); long primaryIdProject003 =
		 * projectDAO.addProject("drei");
		 * 
		 * projectDAO.listProjects();
		 * projectDAO.deleteProject(primaryIdProject003);
		 * projectDAO.listProjects();
		 * projectDAO.deleteProjectByStatus("zwei");
		 * projectDAO.listProjects();
		 * 
		 * ResourceGroupDAO resourceGroupDAO = new ResourceGroupDAO();
		 * 
		 * resourceGroupDAO.addResourceGroup("sawing", "R110",
		 * primaryIdProject001); resourceGroupDAO.addResourceGroup("milling",
		 * "R160", primaryIdProject001);
		 * resourceGroupDAO.addResourceGroup("drilling", "R150",
		 * primaryIdProject001); resourceGroupDAO.addResourceGroup("assembling",
		 * "R310", primaryIdProject001);
		 * resourceGroupDAO.addResourceGroup("quality controlling", "R340",
		 * primaryIdProject001); resourceGroupDAO.addResourceGroup("grinding",
		 * "R200", primaryIdProject001);
		 * resourceGroupDAO.addResourceGroup("turning", "R140",
		 * primaryIdProject001);
		 * 
		 * String searchedResourceGroup =
		 * resourceGroupDAO.searchResourceGroupDescription("R150");
		 * System.out.println
		 * ("Result of the returned ResourceGroup Description: '" +
		 * searchedResourceGroup + "'.\n");
		 * 
		 * resourceGroupDAO.listResourceGroups();
		 */

	}

}