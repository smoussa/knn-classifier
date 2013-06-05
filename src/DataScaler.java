import java.util.ArrayList;

/**
 * The DataScaler class that scales the given data using Euclidean distance
 * scaling
 */
public class DataScaler {

	private int numVariables;
	private ArrayList<ArrayList<Double>> rawData;
	private int dataSize;
	private Double[] means;
	private Double[] sDs;

	/**
	 * Constructor that takes in the number of dimensions and initialises all
	 * the variables
	 * @param num The number of dimensions
	 */
	public DataScaler(int num) {
		numVariables = num;
		rawData = new ArrayList<ArrayList<Double>>();
		means = new Double[num];
		sDs = new Double[num];
	}

	/**
	 * This method scales the given data set
	 * @param rawData The two-dimensional array of data
	 * @return a list of scaled DataPoint objects
	 */
	public ArrayList<DataPoint> scaleData(
			ArrayList<ArrayList<Double>> rawData) {

		this.rawData = rawData;
		dataSize = rawData.size();
		getMeans(); // get the mean of each dimension
		getSDs(); // get the standard deviation of each dimension

		ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();

		// for each row, create a new data point
		for (int row = 0; row < dataSize; row++) {
			Double[] values = new Double[numVariables];
			for (int col = 0; col < numVariables; col++) {
				values[col] =
						(rawData.get(row).get(col) - means[col]) / sDs[col];
			}
			dataPoints.add(new DataPoint(values, row));
		}

		return dataPoints;
	}

	/**
	 * Gets the mean for each dimension
	 */
	private void getMeans() {

		// set default values for each variable means
		for (int i = 0; i < numVariables; i++) {
			means[i] = 0.0;
		}

		// get total sum for each variable
		for (int col = 0; col < numVariables; col++) {
			for (int row = 0; row < dataSize; row++) {
				means[col] += rawData.get(row).get(col);
			}
		}

		// divide by number of data points to get mean
		for (int i = 0; i < numVariables; i++) {
			means[i] /= dataSize;
		}

	}

	/**
	 * Gets the standard deviation for each dimension
	 */
	private void getSDs() {

		// set default values for each variable standard deviation
		for (int i = 0; i < numVariables; i++) {
			sDs[i] = 0.0;
		}

		// sum the squared difference from mean of each variable
		for (int col = 0; col < numVariables; col++) {
			for (int row = 0; row < dataSize; row++) {
				double squaredDiff =
						Math.pow(rawData.get(row).get(col) - means[col], 2);
				sDs[col] += squaredDiff;
			}
		}

		// divide by dataSize - 1 and root the variance
		int size = dataSize - 1;
		for (int i = 0; i < numVariables; i++) {
			sDs[i] /= size;
			sDs[i] = Math.sqrt(sDs[i]);
		}

	}

}
