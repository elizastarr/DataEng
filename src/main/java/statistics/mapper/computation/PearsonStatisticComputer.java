package statistics.mapper.computation;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import statistics.formula.FormulaComponentType;
import statistics.formula.FormulaComponentValue;

import static statistics.formula.FormulaComponentType.COUNT;
import static statistics.formula.FormulaComponentType.FIRST_ELEMENT;
import static statistics.formula.FormulaComponentType.FIRST_SQUARED;
import static statistics.formula.FormulaComponentType.PRODUCT;
import static statistics.formula.FormulaComponentType.SECOND_ELEMENT;
import static statistics.formula.FormulaComponentType.SECOND_SQUARED;

/**
 * Computes the correlation statistic given a map of respectively summed up formula components
 * Utilizes the Pearson correlation formula
 */
public class PearsonStatisticComputer extends StatisticComputer {

	private final MathContext mathContext = MathContext.DECIMAL64;

	@Override
	public Double call(Map<FormulaComponentType, FormulaComponentValue> formulaComponents) {
		BigDecimal count = new BigDecimal(formulaComponents.get(COUNT).getValue());
		BigDecimal sumX = new BigDecimal(formulaComponents.get(FIRST_ELEMENT).getValue());
		BigDecimal sumY = new BigDecimal(formulaComponents.get(SECOND_ELEMENT).getValue());
		BigDecimal sumXSquared = new BigDecimal(formulaComponents.get(FIRST_SQUARED).getValue());
		BigDecimal sumYSquared = new BigDecimal(formulaComponents.get(SECOND_SQUARED).getValue());
		BigDecimal XDotY = new BigDecimal(formulaComponents.get(PRODUCT).getValue());

		BigDecimal numerator = count.multiply(XDotY).subtract(sumX.multiply(sumY));
		BigDecimal left = count.multiply(sumXSquared).subtract(sumX.pow(2)).sqrt(mathContext);
		BigDecimal right = count.multiply(sumYSquared).subtract(sumY.pow(2)).sqrt(mathContext);
		BigDecimal denominator = left.multiply(right);

		return numerator.divide(denominator, mathContext).doubleValue();
	}
}
