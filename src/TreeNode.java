import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author mohit
 *
 */
public class TreeNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6995313407665401054L;
	
	public List<String> nodeAttributeNames;	
	public Map<String, List<Integer>> nodeAttributeDataMap;	
	public List<Integer> nodeClassValues;
	double nodeGainValue;
	int nodeDepthIndex;
	String nodeAttrName;
	String nodeClassification;
	int nodeClassNegativeCount;
	int nodeClassPositiveCount;
	int nodeId;
	static int nodeIdIndex;

	//left and right child for the binary tree
	TreeNode parentNode;
	TreeNode lchild;	
	TreeNode rchild;
	
	 public TreeNode clone() throws CloneNotSupportedException {
         return (TreeNode) super.clone();
	 }
	
	public TreeNode(List<String> nodeAttributeNames, Map<String, List<Integer>> nodeAttributeDataMap,
			List<Integer> nodeClassValues, int nodeDepthIndex) {
		super();
		this.nodeAttributeNames = nodeAttributeNames;
		this.nodeAttributeDataMap = nodeAttributeDataMap;
		this.nodeClassValues = nodeClassValues;
		this.nodeDepthIndex = nodeDepthIndex;
		this.nodeIdIndex++;
		this.nodeId = nodeIdIndex;
	}



	public TreeNode(List<String> nodeAttributeNames, Map<String, List<Integer>> nodeAttributeDataMap,
			List<Integer> nodeClassValues, double nodeGainValue, int nodeDepthIndex, String nodeAttrName,
			String nodeClassification, int nodeClassNegativeCount, int nodeClassPositiveCount, int nodeId,
			TreeNode parentNode, TreeNode lchild, TreeNode rchild) {
		super();
		this.nodeAttributeNames = nodeAttributeNames;
		this.nodeAttributeDataMap = nodeAttributeDataMap;
		this.nodeClassValues = nodeClassValues;
		this.nodeGainValue = nodeGainValue;
		this.nodeDepthIndex = nodeDepthIndex;
		this.nodeAttrName = nodeAttrName;
		this.nodeClassification = nodeClassification;
		this.nodeClassNegativeCount = nodeClassNegativeCount;
		this.nodeClassPositiveCount = nodeClassPositiveCount;
		this.nodeId = nodeId;
		this.parentNode = parentNode;
		this.lchild = lchild;
		this.rchild = rchild;
	}

	public List<String> getNodeAttributeNames() {
		return nodeAttributeNames;
	}



	public void setNodeAttributeNames(List<String> nodeAttributeNames) {
		this.nodeAttributeNames = nodeAttributeNames;
	}



	public Map<String, List<Integer>> getNodeAttributeDataMap() {
		return nodeAttributeDataMap;
	}



	public void setNodeAttributeDataMap(Map<String, List<Integer>> nodeAttributeDataMap) {
		this.nodeAttributeDataMap = nodeAttributeDataMap;
	}



	public List<Integer> getNodeClassValues() {
		return nodeClassValues;
	}



	public void setNodeClassValues(List<Integer> nodeClassValues) {
		this.nodeClassValues = nodeClassValues;
	}



	public double getNodeGainValue() {
		return nodeGainValue;
	}



	public void setNodeGainValue(double nodeGainValue) {
		this.nodeGainValue = nodeGainValue;
	}



	public int getNodeDepthIndex() {
		return nodeDepthIndex;
	}



	public void setNodeDepthIndex(int nodeDepthIndex) {
		this.nodeDepthIndex = nodeDepthIndex;
	}



	public String getNodeAttrName() {
		return nodeAttrName;
	}



	public void setNodeAttrName(String nodeAttrName) {
		this.nodeAttrName = nodeAttrName;
	}



	public String getNodeClassification() {
		return nodeClassification;
	}



	public void setNodeClassification(String nodeClassification) {
		this.nodeClassification = nodeClassification;
	}



	public TreeNode getLchild() {
		return lchild;
	}



	public void setLchild(TreeNode lchild) {
		this.lchild = lchild;
	}



	public TreeNode getRchild() {
		return rchild;
	}



	public void setRchild(TreeNode rchild) {
		this.rchild = rchild;
	}



	public int getNodeClassNegativeCount() {
		return nodeClassNegativeCount;
	}



	public void setNodeClassNegativeCount(int nodeClassNegativeCount) {
		this.nodeClassNegativeCount = nodeClassNegativeCount;
	}



	public int getNodeClassPositiveCount() {
		return nodeClassPositiveCount;
	}



	public void setNodeClassPositiveCount(int nodeClassPositiveCount) {
		this.nodeClassPositiveCount = nodeClassPositiveCount;
	}



	public TreeNode getParentNode() {
		return parentNode;
	}



	public void setParentNode(TreeNode parentNode) {
		this.parentNode = parentNode;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public int getNodeIdIndex() {
		return nodeIdIndex;
	}

	public void setNodeIdIndex(int nodeIdIndex) {
		this.nodeIdIndex = nodeIdIndex;
	}
	

}
