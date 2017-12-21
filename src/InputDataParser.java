import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mohit
 *
 */
public class InputDataParser {

	public List<String> attributeNames;
	
	public List<Integer> classValues;
	
	public Map<String, List<Integer>> attributeDataMap;
	
	public InputDataParser getParsedDataObj(String csvFilePath)
	{
		InputDataParser parsedDataObj = new InputDataParser();
		
		BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        boolean isFirstRow = true;
        
        List<String> attrList = new ArrayList<String> ();
        Map<String, List<Integer>> attributeDataMap = new HashMap<String, List<Integer>>();
        List<Integer> classValueList = new ArrayList<Integer>();

        try {

            br = new BufferedReader(new FileReader(csvFilePath));
            while ((line = br.readLine()) != null) {

            	if(isFirstRow) {

                    String[] headerName = line.split(csvSplitBy);
                    
                    for(String st: headerName) {
                    	if(!st.equalsIgnoreCase("Class")) {
                    		attrList.add(st);
                    	}
                    }
                    parsedDataObj.setAttributeNames(attrList);
            		isFirstRow = false;
            	}else {
            		
	                // use comma as separator
	                String[] dataColumns = line.split(csvSplitBy);
	                List<Integer> dataList; 
	                int index = 0;
	                for(String val: dataColumns) 
	                {
	                	if(index < parsedDataObj.getAttributeNames().size()) {
	                		
		                	//if(attributeDataMap.containsKey(parsedDataObj.getAttributeNames().get(index))) {
		                		
		                		if(attributeDataMap.get(parsedDataObj.getAttributeNames().get(index)) != null) {
		                			
		                			dataList = attributeDataMap.get(parsedDataObj.getAttributeNames().get(index));
		                		}else {
		                			
		                			dataList = new ArrayList<Integer>();
		                		}
		                		dataList.add(Integer.parseInt(val));
		                		attributeDataMap.put(parsedDataObj.getAttributeNames().get(index), dataList);
		                	//}
		                	
	                	}else {
	                		classValueList.add(Integer.parseInt(val));
	                	}
	                	
	                	index++;
	                } 
            	}
            }
            parsedDataObj.setAttributeDataMap(attributeDataMap);
            parsedDataObj.setClassValues(classValueList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
		return parsedDataObj;
	}

	/**
	 * @return the attributeNames
	 */
	public List<String> getAttributeNames() {
		return attributeNames;
	}

	/**
	 * @param attributeNames the attributeNames to set
	 */
	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	/**
	 * @return the classValues
	 */
	public List<Integer> getClassValues() {
		return classValues;
	}

	/**
	 * @param classValues the classValues to set
	 */
	public void setClassValues(List<Integer> classValues) {
		this.classValues = classValues;
	}

	/**
	 * @return the attributeDataMap
	 */
	public Map<String, List<Integer>> getAttributeDataMap() {
		return attributeDataMap;
	}

	/**
	 * @param attributeDataMap the attributeDataMap to set
	 */
	public void setAttributeDataMap(Map<String, List<Integer>> attributeDataMap) {
		this.attributeDataMap = attributeDataMap;
	}
}