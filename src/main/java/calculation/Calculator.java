package calculation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.script.ScriptException;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

public class Calculator {

	private List<String> columns = new ArrayList<String>();
	private List<List<String>> dataList = new ArrayList<List<String>>();
	private List<List<String>> resultList = new ArrayList<List<String>>();
	
	private StringBuilder formula1;
	private StringBuilder formula2;
	private StringBuilder formula3;
	private String digitsAfterPoint;
	private int Data2 = 1;
	private int Data3 = 2;
	private int Data4 = 3;
	private DecimalFormat df;
	
	public Calculator() {

	}

	public Calculator(List<String>cols ,List<List<String>> ls, String digits, StringBuilder... formula) {
		columns = cols;
		dataList = ls;

		formula1 = formula[0];
		formula2 = formula[1];
		formula3 = formula[2];
		
		this.digitsAfterPoint = digits;
	}
	
	public List<List<String>> calculateResult() throws ScriptException {
		Double result1 = 0.0;
		Double result2 = 0.0;
		Double result3 = 0.0;
		
		setFormat();
		
		
		for (int i = 0; i < dataList.size(); i++) {
			List<String> inner = dataList.get(i);
			List<String> resultInner = new ArrayList<String>();
			
			final DoubleEvaluator eval = new DoubleEvaluator();
			StaticVariableSet<Double> variables;
			
			if (!formula1.toString().equals("")) 
			{
				//if(formula1.toString().contains(s))
				variables = replaceWithValues(formula1, inner, result1, result2);
				result1 = eval.evaluate(formula1.toString(), variables);
				resultAdd(result1, 0, resultInner);
				dataList.get(i).add(resultInner.get(0));
			}

			if (!formula2.toString().equals("")) 
			{
				variables = replaceWithValues(formula2, inner, result1, result2);
				result2 = eval.evaluate(formula2.toString(), variables);
				resultAdd(result2, 1, resultInner);
				dataList.get(i).add(resultInner.get(1));
			}

			if (!formula3.toString().equals("")) 
			{
				variables = replaceWithValues(formula3, inner, result1, result2);
				result3 = eval.evaluate(formula3.toString(), variables);
				resultAdd(result3, 2, resultInner);
				dataList.get(i).add(resultInner.get(2));
			}
			
			resultList.add(resultInner);

		}
		
		return resultList;
	}
	
	private StaticVariableSet<Double> replaceWithValues(StringBuilder formula, List<String> inner, double result1, double result2) {
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		if(formula.toString().contains("[") && formula.toString().contains("]"))
		{
			String result = IndexOfData(formula.toString());
			formula.delete(0, formula.length());
			formula.append(result);
			System.out.println(formula);
		}
			variables.set(columns.get(1), Double.parseDouble(inner.get(Data2)));
			variables.set(columns.get(2), Double.parseDouble(inner.get(Data3)));
			variables.set(columns.get(3), Double.parseDouble(inner.get(Data4))/100);
			variables.set(columns.get(4), result1);
			variables.set(columns.get(5), result2);
		return variables;
	}
																    
	private String IndexOfData(String CurrentData) {
		for(int i = 0; i <columns.size(); i++ )
			{
				if(CurrentData.contains("[") && CurrentData.contains("]"))
				{
					String temp = columns.get(i);
					int indexOpenBracket = CurrentData.indexOf(temp) + temp.length();
					char[] charArray = CurrentData.toCharArray();
					while(CurrentData.toString().contains(columns.get(i)) && (charArray[indexOpenBracket] == '['))
					{ 
						String partOfCurrentData = CurrentData.substring(CurrentData.indexOf(temp));
						String col = partOfCurrentData.substring(partOfCurrentData.indexOf(temp), partOfCurrentData.indexOf("["));
						String row = partOfCurrentData.substring(partOfCurrentData.indexOf("[")+1, partOfCurrentData.indexOf("]"));	
						int index = columns.lastIndexOf(col);
						CurrentData = CurrentData.replace(col+"[" +row+ "]", dataList.get(Integer.parseInt(row)).get(index));
						System.out.println(col);  
						System.out.println(CurrentData);
					}
				}
			}
			return CurrentData;
	}

	private void resultAdd (Double result, int col, List<String> resultList) {	
		if (result != 0.0)
			resultList.add(col, df.format(result));
		else
			resultList.add("");
	}
	
	private void setFormat(){
		Locale currentLocale = Locale.getDefault();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
		otherSymbols.setDecimalSeparator('.');
		
		
        if (!digitsAfterPoint.equals("") && digitsAfterPoint.matches("^[1-9]\\d*$"))
        {
            StringBuilder format = new StringBuilder("##########.");
            for (int j = 0; j < Integer.parseInt(digitsAfterPoint); j++) {
                format.append("#");
            }
            df = new DecimalFormat(format.toString(), otherSymbols);
        }else
            df = new DecimalFormat("##########.##", otherSymbols);
	}
}