import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author mohit
 *
 */
public class DecisionTree {

	public TreeNode rootNodeInformationGain;
	
	public TreeNode rootNodeVarianceGain;
	
	public TreeNode resultingTreeNode;
	
	void printDecisionTree(String heuristic)
	{
		if(heuristic.equalsIgnoreCase("Heuristic_1")) {
			inOrderTreeTraversal(rootNodeInformationGain);
		}else if(heuristic.equalsIgnoreCase("Heuristic_2")) {
			inOrderTreeTraversal(rootNodeVarianceGain);
		}
	}
	
	
	private void inOrderTreeTraversal(TreeNode rootNode)
	{
		
		if(rootNode != null) {
			StringBuffer slash = new StringBuffer("");
			for(int i = 0; i < rootNode.getNodeDepthIndex(); i++) {
				 slash = slash.append("| ");
			}
			
			if(rootNode.getLchild() != null && rootNode.getLchild().getNodeClassification()!=null
					&& !rootNode.getLchild().getNodeClassification().equalsIgnoreCase("")) {
				System.out.println(slash + rootNode.getNodeAttrName() +" = 0 :"+ rootNode.getLchild().getNodeClassification());
			}else if (rootNode.getLchild()!=null){
				System.out.println(slash + rootNode.getNodeAttrName() +" = 0 : ");
			}
			inOrderTreeTraversal(rootNode.getLchild());
			
			if(rootNode.getRchild()!=null && rootNode.getRchild().getNodeClassification()!=null
					&& !rootNode.getRchild().getNodeClassification().equalsIgnoreCase("")) {
				System.out.println(slash + rootNode.getNodeAttrName() +" = 1 :"+ rootNode.getRchild().getNodeClassification());
			}else if(rootNode.getRchild()!=null) {
				System.out.println(slash + rootNode.getNodeAttrName() +" = 1 : ");
			}
			inOrderTreeTraversal(rootNode.getRchild());
		}
	}
	
	
	/**
	 * Method to validate the element
	 * @param validationDataSet
	 */
	public int validateDataSet(InputDataParser validationDataSetObj, String heuristic)
	{
		int successfulValidationCount = 0;
		Integer classvalue = 0;
		TreeNode rootNode = null;
		
		if(heuristic.equalsIgnoreCase("Heuristic_1")) {
			rootNode = rootNodeInformationGain;
		}else if(heuristic.equalsIgnoreCase("Heuristic_2")) {
			rootNode = rootNodeVarianceGain;
		}		
		Map<String,Integer> singleDataRecord = new HashMap<String,Integer>();
		int totalValidationCount = validationDataSetObj.getClassValues().size();
		for(int k =0; k < totalValidationCount; k++ )
		{
			classvalue = validationDataSetObj.getClassValues().get(k);
			for(Map.Entry<String, List<Integer>> entry : validationDataSetObj.getAttributeDataMap().entrySet())
			{
				singleDataRecord.put(entry.getKey(), entry.getValue().get(k));
			}
			if(validateOnTree(classvalue, rootNode, singleDataRecord))
				successfulValidationCount++;
		}		
		return successfulValidationCount;
	}
	
	
	private boolean validateOnTree(Integer classValue, TreeNode rootNode, Map<String,Integer> singleDataRecord)
	{
		if(rootNode != null) {
			
			if(rootNode.getNodeAttrName() != null && !rootNode.getNodeAttrName().equalsIgnoreCase("")
					&& rootNode.getNodeClassification().equalsIgnoreCase("")) 
			{				
				Integer recordValue = singleDataRecord.get(rootNode.getNodeAttrName());
				if(recordValue == 0) {
					return validateOnTree(classValue, rootNode.getLchild(), singleDataRecord);					
				}else if(recordValue == 1) {
					return validateOnTree(classValue, rootNode.getRchild(), singleDataRecord);
				}
			}
			else {
				if(classValue == Integer.parseInt(rootNode.getNodeClassification())) {
					return true;
				}else {
					return false;
				}
			}
		}		
		return false;
	}
	
	
	
	//Starting point for generating the tree
	public void generateDecisionTree(InputDataParser parsedDataObj, String heuristic)
	{
		TreeNode.nodeIdIndex = 0;
		TreeNode root = new TreeNode(parsedDataObj.getAttributeNames(), parsedDataObj.getAttributeDataMap(),
												parsedDataObj.getClassValues(), 0);
		if(heuristic.equalsIgnoreCase("Heuristic_1")) {
			this.rootNodeInformationGain = root;
		}else if(heuristic.equalsIgnoreCase("Heuristic_2")) {
			this.rootNodeVarianceGain = root;
		}
		splitOnNode(root, heuristic);
	}
	
	
	private void splitOnNode(TreeNode root, String heuristic)
	{
		if(heuristic.equalsIgnoreCase("Heuristic_1")) {
			getInformationGain(root);
		}else if(heuristic.equalsIgnoreCase("Heuristic_2")) {
			getVarianceImpurityGain(root);
		}
		
		List<Integer> nodeValues = root.getNodeClassValues();
		int positiveCount = 0;
		int negativeCount = 0;
		for(Integer value : nodeValues) {
			if(value == 0) negativeCount ++;
			else positiveCount++;
		}
		root.setNodeClassNegativeCount(negativeCount);
		root.setNodeClassPositiveCount(positiveCount);
		
		if(positiveCount==0 || negativeCount == 0 || root.nodeAttributeNames.size() == 0) {
			
			if(positiveCount >= negativeCount) 
				root.setNodeClassification("1");
			else
				root.setNodeClassification("0");
			return;
		}else {
			List<String> childAttributeNames = new ArrayList<String>();
			childAttributeNames.addAll(root.getNodeAttributeNames());
			childAttributeNames.remove(root.getNodeAttrName());
			
			Map<String, List<Integer>> leftChildAttributeDataMap = new HashMap<String, List<Integer>> ();	
			List<Integer> leftChildClassValues = new ArrayList<Integer> ();
			List<Integer> leftZeroIndexList = new ArrayList<Integer>();
			
			Map<String, List<Integer>> rightChildAttributeDataMap = new HashMap<String, List<Integer>> ();	
			List<Integer> rightChildClassValues = new ArrayList<Integer> ();
			List<Integer> rightOneIndexList = new ArrayList<Integer>();
			int temp = 0;
			for(Integer decidingIndexValue: root.getNodeAttributeDataMap().get(root.getNodeAttrName()))
			{
				if(decidingIndexValue == 0) {
					leftZeroIndexList.add(temp);
					leftChildClassValues.add(root.getNodeClassValues().get(temp));
				}
				else {
					rightOneIndexList.add(temp);
					rightChildClassValues.add(root.getNodeClassValues().get(temp));
				}
				temp++;
			}
			for (Map.Entry<String, List<Integer>> entry : root.getNodeAttributeDataMap().entrySet()) 
			{  
				if(!entry.getKey().equalsIgnoreCase(root.getNodeAttrName())) {
					List<Integer> attributeList = entry.getValue();
					List<Integer> leftAttrList = new ArrayList<Integer> ();
					List<Integer> rightAttrList = new ArrayList<Integer> ();
					
					for(Integer leftIndex: leftZeroIndexList)
					{
						leftAttrList.add(attributeList.get(leftIndex));
					}
					for(Integer rightIndex: rightOneIndexList)
					{
						rightAttrList.add(attributeList.get(rightIndex));
					}
					leftChildAttributeDataMap.put(entry.getKey(), leftAttrList);
					rightChildAttributeDataMap.put(entry.getKey(), rightAttrList);
				}
			}
			
			//contains the 0 feature value 
			TreeNode leftChildNode = new TreeNode(childAttributeNames, leftChildAttributeDataMap, leftChildClassValues, root.getNodeDepthIndex()+1);
			
			//contains the 1 feature value
			TreeNode rightChildNode = new TreeNode(childAttributeNames, rightChildAttributeDataMap, rightChildClassValues, root.getNodeDepthIndex()+1);

			leftChildNode.setParentNode(root);
			rightChildNode.setParentNode(root);
			root.setLchild(leftChildNode);
			root.setRchild(rightChildNode);
			
			splitOnNode(leftChildNode, heuristic);
			splitOnNode(rightChildNode, heuristic);
		}
	}
	
	void getInformationGain(TreeNode root) 
	{
		//fetching the attribute dataMap 
		Map<String, List<Integer>> attributeDataMap = root.getNodeAttributeDataMap();
		List<Integer> classValueList = root.getNodeClassValues();
		String attributeName = "";
		String maxGainAttributeName="";
		double maxGainValue = -1000.0;
		int positiveRootCount = 0;
		int negativeRootCount = 0;
		for(int i : classValueList)
		{
			if(i == 1)
				positiveRootCount++;
			else
				negativeRootCount++;
		}
		int total = positiveRootCount + negativeRootCount;		
		double revisedRootEntropy = getRevisedEntropy(positiveRootCount, negativeRootCount, total);

		for (Map.Entry<String, List<Integer>> entry : attributeDataMap.entrySet()) 
		{ 
			attributeName = entry.getKey();
			int attributeZeroPos = 0;
			int attributeZeroNeg = 0;
			int attributeOnePos = 0;
			int attributeOneNeg = 0;
			double informationGain = 0.0;
			
			List<Integer> attributeValueList = entry .getValue();
			for(int i = 0; i < attributeValueList.size(); i++)
			{
				if (attributeValueList.get(i) == 0 && classValueList.get(i) == 1) {
					attributeZeroPos++;					
				}else if (attributeValueList.get(i) == 0 && classValueList.get(i) == 0) {
					attributeZeroNeg++;
				}else if (attributeValueList.get(i) == 1 && classValueList.get(i) == 1) {
					attributeOnePos++;
				}else if (attributeValueList.get(i) == 1 && classValueList.get(i) == 0) {
					attributeOneNeg++;
				}
			}

			int totalZeroResult = attributeZeroPos + attributeZeroNeg;
			double probZeroPositive = (totalZeroResult==0)?0:(attributeZeroPos/(double)totalZeroResult);
			double probZeroNegative = (totalZeroResult==0)?0:(attributeZeroNeg/(double)totalZeroResult);

			int totalOneResult = attributeOnePos + attributeOneNeg;
			double probOnePositive = (totalOneResult==0)?0:(attributeOnePos/(double)totalOneResult);
			double probOneNegative = (totalOneResult==0)?0:(attributeOneNeg/(double)totalOneResult);
			
			int parentNodeWeight = totalOneResult + totalZeroResult;
			
			double weightedSubEntropy0 = 0.0;
			double weightedSubEntropy1 = 0.0;
			if(parentNodeWeight != 0) {
				weightedSubEntropy0 = (totalZeroResult/(double)parentNodeWeight) * (-(probZeroPositive != 0.0 ? probZeroPositive*Math.log(probZeroPositive)/ Math.log(2):0)
																					-(probZeroNegative != 0.0 ? probZeroNegative*Math.log(probZeroNegative)/ Math.log(2):0));
	
				weightedSubEntropy1 = (totalOneResult/(double)parentNodeWeight) * (-(probOnePositive != 0.0 ? probOnePositive*Math.log(probOnePositive)/ Math.log(2):0)
																					-(probOneNegative != 0.0 ? probOneNegative*Math.log(probOneNegative)/ Math.log(2):0));
			}
			
			if(total == 0) {
				informationGain = revisedRootEntropy;
			}else {			
				informationGain = revisedRootEntropy - weightedSubEntropy0 - weightedSubEntropy1;
			}
			
			double tempGain = informationGain;
			if(tempGain > maxGainValue) {
				maxGainValue = tempGain;
				maxGainAttributeName = attributeName;
			}
		}
		root.setNodeAttrName(maxGainAttributeName);
		root.setNodeGainValue(maxGainValue);
		root.setNodeClassification("");
	}
	
	
	private double getRevisedEntropy(double positiveRootCount, double negativeRootCount, double total) {
		
		double revisedRootEntropy = 0.0;
		double probPositive = total==0?0.00:(positiveRootCount/(double)total);
		double probNegative = total==0?0.00:(negativeRootCount/(double)total);
		
		if(total != 0.00) {
			revisedRootEntropy =  -probPositive * (probPositive != 0.0 ? Math.log(probPositive)/ Math.log(2):0)
					 				-probNegative * (probNegative != 0.0 ? Math.log(probNegative)/ Math.log(2):0);
		}
		return revisedRootEntropy;
	}
	
	
	void getVarianceImpurityGain(TreeNode root) 
	{
		//fetching the attribute dataMap 
		Map<String, List<Integer>> attributeDataMap = root.getNodeAttributeDataMap();
		List<Integer> classValueList = root.getNodeClassValues();
		String attributeName = "";
		String maxGainAttributeName="";
		double maxGainValue = -1000.0;
		int positiveRootCount = 0;
		int negativeRootCount = 0;
		for(int i : classValueList)
		{
			if(i == 1)
				positiveRootCount++;
			else
				negativeRootCount++;
		}
		int total = positiveRootCount + negativeRootCount;		
		double revisedVarianceImpurity = getRevisedVarianceImpurity(positiveRootCount, negativeRootCount, total);

		for (Map.Entry<String, List<Integer>> entry : attributeDataMap.entrySet()) 
		{ 
			attributeName = entry.getKey();
			int attributeZeroPos = 0;
			int attributeZeroNeg = 0;
			int attributeOnePos = 0;
			int attributeOneNeg = 0;
			double varianceImpurityGain = 0.0;
			
			List<Integer> attributeValueList = entry .getValue();
			for(int i = 0; i < attributeValueList.size(); i++)
			{
				if (attributeValueList.get(i) == 0 && classValueList.get(i) == 1) {
					attributeZeroPos++;					
				}else if (attributeValueList.get(i) == 0 && classValueList.get(i) == 0) {
					attributeZeroNeg++;
				}else if (attributeValueList.get(i) == 1 && classValueList.get(i) == 1) {
					attributeOnePos++;
				}else if (attributeValueList.get(i) == 1 && classValueList.get(i) == 0) {
					attributeOneNeg++;
				}
			}

			int totalZeroResult = attributeZeroPos + attributeZeroNeg;
			double probZeroPositive = (totalZeroResult==0)?0:(attributeZeroPos/(double)totalZeroResult);
			double probZeroNegative = (totalZeroResult==0)?0:(attributeZeroNeg/(double)totalZeroResult);

			int totalOneResult = attributeOnePos + attributeOneNeg;
			double probOnePositive = (totalOneResult==0)?0:(attributeOnePos/(double)totalOneResult);
			double probOneNegative = (totalOneResult==0)?0:(attributeOneNeg/(double)totalOneResult);
			
			int parentNodeWeight = totalOneResult + totalZeroResult;
			
			double weightedVariance0 = 0.0;
			double weightedVariance1 = 0.0;
			if(parentNodeWeight != 0) {
				weightedVariance0 = (totalZeroResult/(double)parentNodeWeight) * (probZeroNegative * probZeroPositive);
	
				weightedVariance1 = (totalOneResult/(double)parentNodeWeight) * (probOneNegative * probOnePositive);
			}
			
			if(total == 0) {
				varianceImpurityGain = revisedVarianceImpurity;
			}else {			
				varianceImpurityGain = revisedVarianceImpurity - weightedVariance0 - weightedVariance1;
			}
			
			double tempGain = varianceImpurityGain;
			if(tempGain > maxGainValue) {
				maxGainValue = tempGain;
				maxGainAttributeName = attributeName;
			}
		}
		root.setNodeAttrName(maxGainAttributeName);
		root.setNodeGainValue(maxGainValue);
		root.setNodeClassification("");
	}
	
	
	private double getRevisedVarianceImpurity(double positiveRootCount, double negativeRootCount, double total) {
		
		double revisedRootEntropy = 0.0;
		double probPositive = total==0?0.00:(positiveRootCount/(double)total);
		double probNegative = total==0?0.00:(negativeRootCount/(double)total);		
		revisedRootEntropy = probNegative * probPositive;
		return revisedRootEntropy;
	}	
	
	
	/**
	 * Method to calculate the best tree from the original tree.
	 * @param heuristic_1
	 * @param validationFileObj
	 * @param validationCountThreshold
	 */
	public void pruneDecisionTree(String heuristic, double validationAccuracy, InputDataParser validationFileObj)
	{
		if(heuristic.equalsIgnoreCase("Heuristic_1")) {
			this.rootNodeInformationGain = reducedErrorTreePrune(rootNodeInformationGain, validationAccuracy, validationFileObj);
		}else if(heuristic.equalsIgnoreCase("Heuristic_2")) {
			this.rootNodeVarianceGain = reducedErrorTreePrune(rootNodeVarianceGain, validationAccuracy, validationFileObj);
		}
	}
	
	

	
	private TreeNode reducedErrorTreePrune(TreeNode rootNode, double currentAccuracy, InputDataParser validationDataSetObj)
	{ 
			try {
				double originalTreeAccuracy = currentAccuracy;
				int depth = 0;
				TreeNode prunedTree = (TreeNode)ObjectCloner.deepCopy(rootNode);
				TreeNode replacementPruneTree = (TreeNode)ObjectCloner.deepCopy(rootNode);;
				boolean isAccuracyImproved=true;
				int maxLevel = maxDepthTree(prunedTree, depth);
				while (isAccuracyImproved){
					
					isAccuracyImproved = false;
					int currentLevel = maxLevel;
					while(currentLevel >= 0){  
						
						List<Integer> nodesIdList = new ArrayList<Integer> ();
						getNonLeafNodesAtDepth(nodesIdList, prunedTree, currentLevel);
						
						for (int i = 0; i < nodesIdList.size(); i++){
							TreeNode prunedTreeCopy = (TreeNode)ObjectCloner.deepCopy(prunedTree);
							deleteNodeFromTree(prunedTreeCopy, nodesIdList.get(i));
							
							double newAccuracy = getAccuracy(prunedTreeCopy, validationDataSetObj);
							if (newAccuracy > currentAccuracy){
								currentAccuracy = newAccuracy;
								replacementPruneTree = (TreeNode)ObjectCloner.deepCopy(prunedTreeCopy);
							}
						}
						currentLevel--;
					}
					
					currentAccuracy = getAccuracy(replacementPruneTree, validationDataSetObj);
					if (currentAccuracy > getAccuracy(prunedTree, validationDataSetObj)){
						isAccuracyImproved = true;
						prunedTree = (TreeNode)ObjectCloner.deepCopy(replacementPruneTree);
					}
				}
				if (getAccuracy(prunedTree, validationDataSetObj) > originalTreeAccuracy){
					rootNode = (TreeNode)ObjectCloner.deepCopy(prunedTree);			
				}
			}catch (Exception e) {
				System.out.println("Exception caused while creating copy object");
			}
			return rootNode;
	}
	

	/**
	 * Method to validate the element
	 * @param validationDataSet
	 */
	public double getAccuracy(TreeNode rootNode, InputDataParser validationDataSetObj)
	{
		int successfulValidationCount = 0;
		Integer classvalue = 0;
		
		try {
			Map<String,Integer> singleDataRecord = new HashMap<String,Integer>();
			int totalValidationCount = validationDataSetObj.getClassValues().size();
			for(int k =0; k < totalValidationCount; k++ )
			{
				classvalue = validationDataSetObj.getClassValues().get(k);
				for(Map.Entry<String, List<Integer>> entry : validationDataSetObj.getAttributeDataMap().entrySet())
				{
					singleDataRecord.put(entry.getKey(), entry.getValue().get(k));
				}
				if(validateOnTree(classvalue, rootNode, singleDataRecord))
					successfulValidationCount++;
			}		
			return (double)successfulValidationCount/totalValidationCount;
		} catch (Exception e) {
			return 0.00;
		}
	}
	
	
	private int maxDepthTree(TreeNode rootNode, int maxDepth)
	{		
		if(rootNode != null) {
			maxDepth = maxDepthTree(rootNode.getLchild(), maxDepth);
			if(maxDepth < rootNode.getNodeDepthIndex()) {
				maxDepth = rootNode.getNodeDepthIndex();
			}
			maxDepth = maxDepthTree(rootNode.getRchild(), maxDepth);
		}
		return maxDepth;
	}
	
	private void getNonLeafNodesAtDepth(List<Integer> nodesIdList, TreeNode rootNode, int depth)
	{	
		if(rootNode != null) {
			getNonLeafNodesAtDepth(nodesIdList, rootNode.getLchild(), depth);
			if(rootNode.getNodeDepthIndex() == depth && rootNode.getNodeClassification().equalsIgnoreCase(""))
				nodesIdList.add(rootNode.getNodeId());
			getNonLeafNodesAtDepth(nodesIdList, rootNode.getRchild(), depth);
		}
	}
	
	
	private void deleteNodeFromTree(TreeNode rootNode, int nodeId)
	{		
		if(rootNode != null) {
			deleteNodeFromTree(rootNode.getLchild(), nodeId);
			if(rootNode.getNodeId() == nodeId)
			{				
				rootNode.setLchild(null);
				rootNode.setRchild(null);				
				if(rootNode.getNodeClassPositiveCount() >= rootNode.getNodeClassNegativeCount()) 
					rootNode.setNodeClassification("1");
				else 
					rootNode.setNodeClassification("0");
			}
			deleteNodeFromTree(rootNode.getRchild(), nodeId);
		}
	}
	
	
}
