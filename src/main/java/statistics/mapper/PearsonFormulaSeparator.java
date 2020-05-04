package statistics.mapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.apache.spark.api.java.function.PairFlatMapFunction;

import scala.Tuple2;
import schema.EnergyDataPair;
import statistics.formula.FormulaComponent;
import statistics.formula.FormulaKey;
import statistics.formula.FormulaValue;

import static java.lang.Math.pow;
import static statistics.formula.FormulaComponent.COUNT;
import static statistics.formula.FormulaComponent.FIRST_ELEMENT;
import static statistics.formula.FormulaComponent.FIRST_SQUARED;
import static statistics.formula.FormulaComponent.PRODUCT;
import static statistics.formula.FormulaComponent.SECOND_ELEMENT;
import static statistics.formula.FormulaComponent.SECOND_SQUARED;

/**
 * PairFlatMapFunction implementation for Pearson correlation
 * Maps the given EnergyDataPair to formula components required for the Pearson statistic calculation
 */
public class PearsonFormulaSeparator implements PairFlatMapFunction<EnergyDataPair, FormulaKey, FormulaValue> {

	public Iterator<Tuple2<FormulaKey, FormulaValue>> call(EnergyDataPair pair) {
		double x = pair.getEnergyValuePair().getFirstValue();
		double y = pair.getEnergyValuePair().getSecondValue();
		Collection<Tuple2<FormulaKey, FormulaValue>> collection = Set.of(
				createTuple(pair, COUNT, Double.valueOf(1)),
				createTuple(pair, FIRST_ELEMENT, x),
				createTuple(pair, SECOND_ELEMENT, y),
				createTuple(pair, FIRST_SQUARED, pow(x, 2)),
				createTuple(pair, SECOND_SQUARED, pow(y, 2)),
				createTuple(pair, PRODUCT, x * y)
		);
		return collection.iterator();
	}

	private Tuple2<FormulaKey, FormulaValue> createTuple(EnergyDataPair pair, FormulaComponent component, double value) {
		FormulaKey key = new FormulaKey(pair.getCountryPair(), component);
		FormulaValue formulaValue = new FormulaValue(pair.getTimestamp(), pair.getCountryPair(), component, value);
		return new Tuple2<>(key, formulaValue);
	}
}
