import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 	The DataReader class that reads the raw data file and scales the values
 */
public class DataReader {

	private static final int NUM = 8;
	ArrayList<DataPoint> dataPoints;

	/**
	 * Constructor for initialising the array list of data points
	 */
	public DataReader() {
		dataPoints = new ArrayList<DataPoint>();
	}

	/**
	 * This method reads a data file, creates a two-dimensional table of values
	 * and scales each of the data points.
	 * @param path The file path
	 * @throws IOException
	 */
	public void readFile(String path) throws IOException  {

		BufferedReader in = new BufferedReader(new FileReader(path));
		ArrayList<ArrayList<Double>> rawData =
				new ArrayList<ArrayList<Double>>();
		ArrayList<String> categories = new ArrayList<String>();

		String line = null;
		in.readLine(); // skip headers
		while ((line = in.readLine()) != null) { // for each row

			String[] dim = line.split("\\s+");
			ArrayList<Double> dataPoint = new ArrayList<Double>(NUM);

			// get the value of each variable from the line read
			for (int i = 0; i < NUM; i++) {
				dataPoint.add(Double.parseDouble(dim[i]));
			}

			categories.add(dim[NUM]); // store the set of categories

			rawData.add(dataPoint); // store the list of values
		}

		in.close();

		// scale data
		DataScaler scaler = new DataScaler(NUM);
		dataPoints = scaler.scaleData(rawData);

		// assign their categories
		int c = 0;
		for (DataPoint point : dataPoints) {
			point.setCategory(categories.get(c));
			//point.printDataPoint();
			c++;
		}

	}

	/**
	 * Returns the complete list of DataPoint objects
	 * @return the list of data points
	 */
	public ArrayList<DataPoint> getDataList() {
		return dataPoints;
	}

}
