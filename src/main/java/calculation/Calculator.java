package calculation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
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
	private int Data1 = 0;
	private int Data2 = 1;
	private int Data3 = 2;
	private int Data4 = 3;

	public Calculator() {

	}

	public Calculator(List<String>cols ,List<List<String>> ls, StringBuilder... formula) {
		columns = cols;
		dataList = ls;

		formula1 = formula[0];
		formula2 = formula[1];
		formula3 = formula[2];
	}
	
	/*void fillString(StringBuilder zText, String formula) { 
		zText.delete(0, zText.length()).append(formula);
		}*/
	
	private StaticVariableSet<Double> replaceWithValues(StringBuilder formula, List<String> inner, double result1, double result2) {
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		if(formula.toString().contains("[") && formula.toString().contains("]"))
		{
			/*variables.set(columns.get(1), IndexOfData(dataList, columns,  formula));
			variables.set(columns.get(2), IndexOfData(dataList, columns,  inner.get(Data3)));
			variables.set(columns.get(3), IndexOfData(dataList, columns, inner.get(Data4))/100);
			variables.set(columns.get(4), IndexOfData(dataList, columns, (Double.toString(result1))));
			variables.set(columns.get(5), IndexOfData(dataList, columns, (Double.toString(result2))));*/
			String result = IndexOfData(formula.toString());
			formula.delete(0, formula.length());
			formula.append(result);
			//formula = IndexOfData(formula);
			//StringBuilder zText = new StringBuilder ();
			//fillString(zText, formula);
			System.out.println(formula);
		}
		//else
		//{
			variables.set(columns.get(1), Double.parseDouble(inner.get(Data2)));
			variables.set(columns.get(2), Double.parseDouble(inner.get(Data3)));
			variables.set(columns.get(3), Double.parseDouble(inner.get(Data4))/100);
			variables.set(columns.get(4), result1);
			variables.set(columns.get(5), result2);
		//}
		return variables;
	}
																    //5+B[1]     5-C+C[2]-11
	private String IndexOfData(String CurrentData) {
		//char[] charArray = CurrentData.toCharArray();
		//boolean ifContainsLetters = CurrentData.matches(".*[a-zA-Z]+.*");
		//if(CurrentData.contains("[") && CurrentData.contains("]"))
		//{
			for(int i = 0; i <columns.size(); i++ )
			{
				if(CurrentData.contains("[") && CurrentData.contains("]"))
				{
					String temp = columns.get(i);
					int indexOpenBracket = CurrentData.indexOf(temp) + temp.length();
					char[] charArray = CurrentData.toCharArray();
					if(CurrentData.toString().contains(columns.get(i)) && (charArray[indexOpenBracket] == '['))
					{
						String col = CurrentData.substring(CurrentData.indexOf(temp.substring(0,1)), CurrentData.indexOf("["));
						String row = CurrentData.substring(CurrentData.indexOf("[")+1, CurrentData.indexOf("]"));	
						int index = columns.lastIndexOf(col);
						CurrentData = CurrentData.replace(col+"[" +row+ "]", dataList.get(Integer.parseInt(row)).get(index));
						System.out.println(col);  
						System.out.println(CurrentData);
					}
				}
			}
		//}
		//String row = CurrentData.substring(CurrentData.indexOf("[")+1, CurrentData.indexOf("]"));	
		//String col = CurrentData.substring(0, CurrentData.indexOf("["));
		//System.out.println(col);
		
		//int index = columns.lastIndexOf(col);
		//String col = CurrentData.substring(CurrentData.indexOf("[")-1, CurrentData.indexOf("]")-2);	
		//return Double.parseDouble(dataList.get(Integer.parseInt(row)).get(index));
			return CurrentData;
	}

	public List<List<String>> calculateResult() throws ScriptException {
		Double result1 = 0.0;
		Double result2 = 0.0;
		Double result3 = 0.0;
		
		for (int i = 0; i < dataList.size(); i++) {
			List<String> inner = dataList.get(i);
			
			final DoubleEvaluator eval = new DoubleEvaluator();
			StaticVariableSet<Double> variables;
			
			if (!formula1.toString().equals("")) {
				//String newFormula1 = formula1;
				variables = replaceWithValues(formula1, inner, result1, result2);
				result1 = eval.evaluate(formula1.toString(), variables);
			}

			if (!formula2.toString().equals("")) {
				//String newFormula2 = formula2;
				variables = replaceWithValues(formula2, inner, result1, result2);
				result2 = eval.evaluate(formula2.toString(), variables);
			}

			if (!formula3.toString().equals("")) {
				//String newFormula3 = formula3;
				variables = replaceWithValues(formula3, inner, result1, result2);
				result3 = eval.evaluate(formula3.toString(), variables);
			}

			List<String> resultInner = new ArrayList<String>();

			Locale currentLocale = Locale.getDefault();
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
			otherSymbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("##########.##", otherSymbols);

			if (result1 != 0.0)
				resultInner.add(0, df.format(result1));
			else
				resultInner.add("");
			
			if ((Double) result2 != 0.0)
				resultInner.add(1, df.format(result2));
			else
				resultInner.add("");
			
			if ((Double) result3 != 0.0)
				resultInner.add(2, df.format(result3));
			else
				resultInner.add("");

			resultList.add(resultInner);

		}
		
		//System.out.println(IndexOfData(dataList, columns,  "5+Datatatata[2]-D[1]"));
		
		return resultList;
	}
	
	public static void Test(String expression) {
		final DoubleEvaluator eval = new DoubleEvaluator();
		double result = eval.evaluate(expression);
		System.out.println(result);
	}
	
	public static void main(String[] args) {
		Calculator.Test("2.41--6.77*5.43*((7.73+-(1.24-((3.43+6.61)*7.34+6.64-2.58*(6.17-1.46)*3.37+8.63-5.43)*2.52*6.32-3.75+6.57))*7.52-0.82)");
	}
}