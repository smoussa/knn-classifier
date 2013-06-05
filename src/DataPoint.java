import java.util.ArrayList;

/**
 *	DataPoint class allows creating an object for each data point
 */
public class DataPoint implements Comparable<DataPoint> {

	private ArrayList<Double> variables;
	private String category;
	private double relativeDistance;
	private int index;

	/**
	 * Constructor takes a set of dimensions and the position of the point in
	 * the data set
	 * @param variables The set of dimensions
	 * @param index The integer position in the data list
	 */
	public DataPoint(Double[] variables, int index) {

		this.index = index;

		// populate variable values
		this.variables = new ArrayList<Double>();
		for (int i = 0; i < variables.length; i++) { // load the values
			this.variables.add(variables[i]);
		}

		relativeDistance = 0;
	}

	/**
	 * Returns the index of the data point in the data list
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Stores the class of the data point object
	 * @param The category The class of the data point
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the list of variables for the data point
	 * @return an ArrayList of variable values
	 */
	public ArrayList<Double> getVariables() {
		return variables;
	}

	/**
	 * Sets the value for a particular variable
	 * @param The index the variables index
	 * @param The value the value to set
	 */
	public void setValue(int index, double value) {
		variables.set(index, value);
	}

	/**
	 * Sets the relative distance from this point to another. This changes
	 * during scaling
	 * @param The distance 
	 */
	public void setRelativeDistance(double distance) {
		relativeDistance = distance;
	}

	/**
	 * Returns the distance from this point to another
	 * @return the distance
	 */
	public double getRelativeDistance() {
		return relativeDistance;
	}

	/**
	 * Prints the data point and its values for testing purposes
	 */
	public void printDataPoint() {
		System.out.println();
		for (Double v : variables) {
			System.out.print(v + " ");
		}
		System.out.print(category);
	}

	@Override
	public int compareTo(DataPoint point) {
		double t = this.getRelativeDistance();
		double p = point.getRelativeDistance();
		return t > p ? 1 : t < p ? -1 : 0;
	}

	@Override
	public String toString() {
		return category;
	}

}
