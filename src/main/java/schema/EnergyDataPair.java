package schema;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class EnergyDataPair {

	private final Timestamp timestamp;
	private final CountryPair countryPair;
	private final EnergyValuePair energyValuePair;
}
