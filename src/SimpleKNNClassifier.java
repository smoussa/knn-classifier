import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The SimpleKNNClassifier class computes a simple KNN classification
 */
public class SimpleKNNClassifier extends KNNClassifier {

	private ArrayList<DataPoint> dataList;
	private int dataSize;
	private int numVariables;

	/**
	 * Constructor for initialising variables
	 * @param dataList The list of data points
	 */
	public SimpleKNNClassifier(ArrayList<DataPoint> dataList) {
		this.dataList = dataList;
		dataSize = dataList.size();
		numVariables = dataList.get(0).getVariables().size();
	}

	/**
	 * Gets the nearest neighbour for a given data point
	 * @param pointA The data point
	 * @return the list of neighbour data points
	 */
	private ArrayList<DataPoint> getNearestNeighbours(DataPoint pointA) {

		ArrayList<Double> pointAValues = pointA.getVariables();
		ArrayList<DataPoint> pointANeighbours = new ArrayList<DataPoint>();

		// for each other data point
		for (int j = 0; j < dataSize; j++) {

			DataPoint pointB = dataList.get(j);
			ArrayList<Double> pointBValues = pointB.getVariables();

			if (pointA.getIndex() != j) { // if they are not the same

				double distance = calculateEuclideanDistance(
						numVariables,
						pointAValues,
						pointBValues);
				pointB.setRelativeDistance(distance);
				pointANeighbours.add(pointB);

			}
		}

		Collections.sort(pointANeighbours);

		return pointANeighbours;
	}

	/**
	 * Predicts the category for a given data point
	 * @param neighbours The neighbours of the data point
	 * @param k The number of neighbours, K
	 * @return the predicted category/class
	 */
	private String predictCategory(ArrayList<DataPoint> neighbours, int k) {

		// map for a count of nearest neighbours occurrences
		HashMap<String, Integer> map = new HashMap<String, Integer>(k);

		// initialise the most common
		String category = neighbours.get(0).toString();
		int max = 0;

		// rank nearest neighbours
		for (int n = 0; n < k; n++) {

			String neighbourClass = neighbours.get(n).toString();
			if (map.get(neighbourClass) != null) {
				int count = map.get(neighbourClass);
				count += 1;
				map.put(neighbourClass, count);
				if (count > max) {
					category = neighbourClass;
				}
			} else {
				map.put(neighbourClass, 1);
			}

		}

		return category;
	}

	/**
	 * This method loops through each data point and classifies them
	 * @param k The number of neighbours, K
	 * @return the score for the classification
	 */
	public double classify(int k) {

		double score = 0;

		// for each data point
		for (int i = 0; i < dataSize; i++) {

			DataPoint pointA = dataList.get(i);
			ArrayList<DataPoint> pointANeighbours =
					getNearestNeighbours(pointA);
			String category = predictCategory(pointANeighbours, k);

			if (category.equals(pointA.toString())) {
				score++;
			}

		}

		return score;
	}

}
