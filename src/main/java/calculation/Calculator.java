package calculation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;

public class Calculator {

	private List<List<String>> dataList = new ArrayList<List<String>>();
	private List<List<String>> resultList = new ArrayList<List<String>>();
	private String formula1;
	private String formula2;
	private String formula3;
	private int Data1 = 0;
	private int Data2 = 1;
	private int Data3 = 2;
	private int Data4 = 3;

	public Calculator() {

	}

	public Calculator(List<List<String>> ls, String... formula) {
		dataList = ls;

		formula1 = formula[0];
		formula2 = formula[1];
		formula3 = formula[2];
	}

	private StaticVariableSet<Double> replaceWithValues(List<String> inner, double result1, double result2) {
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		variables.set("Data2", Double.parseDouble(inner.get(Data2)));
		variables.set("Data3", Double.parseDouble(inner.get(Data3)));
		variables.set("Data4", Double.parseDouble(inner.get(Data4)) / 100);
		variables.set("Result1", result1);
		variables.set("Result2", result2);

		return variables;
	}

	public List<List<String>> calculateResult() throws ScriptException {
		Double result1 = 0.0;
		Double result2 = 0.0;
		Double result3 = 0.0;

		for (int i = 0; i < dataList.size(); i++) {
			List<String> inner = dataList.get(i);

			final DoubleEvaluator eval = new DoubleEvaluator();
			StaticVariableSet<Double> variables;

			String newFormula1;
			if (!formula1.equals("")) {
				newFormula1 = formula1;
				variables = replaceWithValues(inner, result1, result2);
				result1 = eval.evaluate(newFormula1, variables);
				System.out.println(result1);
			}

			if (!formula2.equals("")) {
				String newFormula2 = formula2;
				variables = replaceWithValues(inner, result1, result2);
				result2 = eval.evaluate(newFormula2, variables);
				System.out.println(result2);
			}

			if (!formula3.equals("")) {
				String newFormula3 = formula3;
				variables = replaceWithValues(inner, result1, result2);
				result3 = eval.evaluate(newFormula3, variables);
				System.out.println(result3);
			}

			List<String> resultInner = new ArrayList<String>();

			Locale currentLocale = Locale.getDefault();
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(currentLocale);
			otherSymbols.setDecimalSeparator('.');
			DecimalFormat df = new DecimalFormat("##########.##", otherSymbols);

			if (result1 != 0.0)
				resultInner.add(df.format(result1));
			else
				resultInner.add("");

			if (result2 != 0.0)
				resultInner.add(df.format(result2));
			else
				resultInner.add("");

			if (result3 != 0.0)
				resultInner.add(df.format(result3));
			else
				resultInner.add("");

			resultList.add(resultInner);

		}

		resultList.forEach(element -> {
			element.forEach(inner -> System.out.println(inner));
		});

		return resultList;
	}

	public static void main(String[] args) throws ScriptException {
		/*final String expression = "Data2*Data3"; // Here is the expression to evaluate
		// Create the evaluator
		final DoubleEvaluator eval = new DoubleEvaluator();
		// Create a new empty variable set
		final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
		double x = 3;
		double y = -2;
		// Set the value of x
		variables.set("Data2", x);
		variables.set("Data3", y);
		// Evaluate the expression
		Double result = eval.evaluate( expression, variables);
		// Output the result
		System.out.println("x=" + x + " y=" + y + " -> " + expression + " = " + result);*/
		
		String str = "United Arab Emirates Dirham (3) dwadwa 2[67] ffes [4444.5]";
		String answer = str.substring(str.indexOf("[")+1, str.indexOf("]"));
		String answer2 = str.substring(str.indexOf("[")-1, str.indexOf("]")-2);
		
		System.out.println(answer);
		System.out.println(answer2);
	}
}
