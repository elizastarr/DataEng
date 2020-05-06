package statistics.mapper;

import java.util.Map;

import statistics.formula.FormulaComponentType;
import statistics.formula.FormulaComponentValue;

import static java.lang.Math.pow;
import static statistics.formula.FormulaComponentType.COUNT;
import static statistics.formula.FormulaComponentType.DIFF_SQUARED;

/**
 * Computes the correlation statistic given a map of respectively summed up formula components
 * Utilizes the Spearman correlation formula
 */
public class SpearmanStatisticComputer extends StatisticComputer {

	@Override
	public Double call(Map<FormulaComponentType, FormulaComponentValue> formulaComponents) {
		double count = formulaComponents.get(COUNT).getValue();
		double diffSquared = formulaComponents.get(DIFF_SQUARED).getValue();

		return 1 - 6 * diffSquared / count * (pow(count, 2) - 1);
	}
}
