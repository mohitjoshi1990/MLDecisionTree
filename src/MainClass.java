
/**
 * @author mohit
 *
 */
public class MainClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int successfulcount = 0;
		double validationAccuracy = 0.00;
		int validationSuccessCount = 0;
		
		
		//System.out.println(args[0] + args[1]+args[2]+ args[4]+args[6]);
		String trainingFilePath = args[0];
		String validationFilePath = args[1];
		String testFilePath = args[2];
		String toPrint = args[4];
		String toPrune = args[6];
		
		InputDataParser trainingSetObj = new InputDataParser();
		trainingSetObj = trainingSetObj.getParsedDataObj(trainingFilePath);
		InputDataParser validationFileObj = new InputDataParser();
		validationFileObj = validationFileObj.getParsedDataObj(validationFilePath);
		InputDataParser testFileObj = new InputDataParser();
		testFileObj = testFileObj.getParsedDataObj(testFilePath);
		String heuristic_1 = "Heuristic_1";
		String heuristic_2 = "Heuristic_2";
		
		DecisionTree decTreeObj = new DecisionTree();	
		decTreeObj.generateDecisionTree(trainingSetObj, heuristic_1);
		
		successfulcount = decTreeObj.validateDataSet(trainingSetObj, heuristic_1);
		System.out.println("H1 NP Training set accuracy is : "+ (double) successfulcount/trainingSetObj.getClassValues().size());		
		validationSuccessCount = decTreeObj.validateDataSet(validationFileObj, heuristic_1);
		validationAccuracy = (double) validationSuccessCount/validationFileObj.getClassValues().size();
		System.out.println("H1 NP Validation set accuracy is : "+ validationAccuracy);		
		successfulcount = decTreeObj.validateDataSet(testFileObj, heuristic_1);
		System.out.println("H1 NP Test set accuracy is : "+ (double) successfulcount/testFileObj.getClassValues().size());
		if(toPrint.equalsIgnoreCase("yes") && !toPrune.equalsIgnoreCase("yes")) {
			decTreeObj.printDecisionTree(heuristic_1);
		}
		
		if(toPrune.equalsIgnoreCase("yes")) {
			decTreeObj.pruneDecisionTree(heuristic_1, validationAccuracy, validationFileObj);
			
			successfulcount = decTreeObj.validateDataSet(trainingSetObj, heuristic_1);
			System.out.println("H1 P Training set accuracy is : "+ (double) successfulcount/trainingSetObj.getClassValues().size());			
			validationSuccessCount = decTreeObj.validateDataSet(validationFileObj, heuristic_1);
			validationAccuracy = (double) validationSuccessCount/validationFileObj.getClassValues().size();
			System.out.println("H1 P Validation set accuracy is : "+ validationAccuracy);			
			successfulcount = decTreeObj.validateDataSet(testFileObj, heuristic_1);
			System.out.println("H1 P Test set accuracy is : "+ (double) successfulcount/testFileObj.getClassValues().size());
			if(toPrint.equalsIgnoreCase("yes")) {
				decTreeObj.printDecisionTree(heuristic_1);
			}
		}
		
		

		
		DecisionTree decTreeImpurityObj = new DecisionTree();
		decTreeImpurityObj.generateDecisionTree(trainingSetObj, heuristic_2);
		
		successfulcount = decTreeImpurityObj.validateDataSet(trainingSetObj, heuristic_2);
		System.out.println("H2 NP Training set accuracy is : "+ (double) successfulcount/trainingSetObj.getClassValues().size());		
		validationSuccessCount = decTreeImpurityObj.validateDataSet(validationFileObj, heuristic_2);
		validationAccuracy = (double) validationSuccessCount/validationFileObj.getClassValues().size();
		System.out.println("H2 NP Validation set accuracy is : "+ validationAccuracy);		
		successfulcount = decTreeImpurityObj.validateDataSet(testFileObj, heuristic_2);
		System.out.println("H2 NP Test set accuracy is : "+ (double) successfulcount/testFileObj.getClassValues().size());
		if(toPrint.equalsIgnoreCase("yes") && !toPrune.equalsIgnoreCase("yes")) {
			decTreeImpurityObj.printDecisionTree(heuristic_2);
		}

		
		if(toPrune.equalsIgnoreCase("yes")) {		
			decTreeImpurityObj.pruneDecisionTree(heuristic_2, validationAccuracy, validationFileObj);
			
			successfulcount = decTreeImpurityObj.validateDataSet(trainingSetObj, heuristic_2);
			System.out.println("H2 P Training set accuracy is : "+ (double) successfulcount/trainingSetObj.getClassValues().size());		
			validationSuccessCount = decTreeImpurityObj.validateDataSet(validationFileObj, heuristic_2);
			validationAccuracy = (double) validationSuccessCount/validationFileObj.getClassValues().size();
			System.out.println("H2 P Validation set accuracy is : "+ validationAccuracy);		
			successfulcount = decTreeImpurityObj.validateDataSet(testFileObj, heuristic_2);
			System.out.println("H2 P Test set accuracy is : "+ (double) successfulcount/testFileObj.getClassValues().size());
			if(toPrint.equalsIgnoreCase("yes")) {
				decTreeImpurityObj.printDecisionTree(heuristic_2);
			}
		}
	}

}
