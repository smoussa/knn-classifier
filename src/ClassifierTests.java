import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * The ClassifierTests class is the test suite which is used to try different
 * classification methods
 */
public class ClassifierTests {

	public static void main(String[] args) {

		DataReader reader = new DataReader();
		try {
			reader.readFile("docs/diveData.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<DataPoint> data = reader.getDataList();

		// try different tests

		//singleK(data, 3);					// 70%
		//bestK(data);						// 71%

		//singleKWeighted(data, 3);			// 70%
		//bestKWeighted(data);				// 72%

		//singleKBestSubset(3);				// 79%
		//bestKBestSubset();				// 79%

		//singleKWeightedBestSubset(3);		// 78%
		bestKWeightedBestSubset();		// 80%

	}

	/**
	 * Get the best classification by trying every K value, weighting the
	 * distances and trying every subset of variables possible to return the
	 * best value for K, the best subset of dimensions and the accuracy score
	 */
	private static void bestKWeightedBestSubset() {

		Set<Integer> bestSet = null;
		double bestScore = 0.0;
		int dataSize = 0;
		int bestK = 0;

		// calculate power set
		Set<Integer> variableSet = new HashSet<Integer>();
		for (int i = 0; i < 8; i++) {
			variableSet.add(i);
		}

		// for each subset
		for (Set<Integer> subset : powerSet(variableSet)) {

			DataReader reader = new DataReader();
			try {
				reader.readFile("docs/diveData.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<DataPoint> data = reader.getDataList();
			dataSize = data.size();

			if (!subset.isEmpty()) {

				for (Integer i : subset) {
					for (DataPoint point : data) {
						point.setValue(i, 0); // remove values in dimension i
					}
				}

				for (int i = 0; i < dataSize; i++) {

					WeightedKNNClassifier knn = new WeightedKNNClassifier(data);

					double score = knn.classify(i);
					if (score > bestScore) { // record best score
						bestScore = score;
						bestSet = subset;
						bestK = i;

						System.out.println("Best subset so far: " + subset
								+ " [K = " + bestK + "] ["
								+ Math.round(bestScore / dataSize * 100)
								+ "%]");
					}

				}

			}
		}

		System.out.println("\nBest variable subset overall " + bestSet
				+ " [K = " + bestK + "] ["
				+ Math.round(bestScore / dataSize * 100) + "%]");

	}

	/**
	 * Get the best classification by trying a single K value, weighting the
	 * distances and trying every subset of variables possible to return the
	 * the best subset of dimensions and the accuracy score for that value of K
	 */
	private static void singleKWeightedBestSubset(int k) {

		Set<Integer> bestSet = null;
		double bestScore = 0.0;
		int dataSize = 0;

		// calculate power set
		Set<Integer> variableSet = new HashSet<Integer>();
		for (int i = 0; i < 8; i++) {
			variableSet.add(i);
		}

		for (Set<Integer> subset : powerSet(variableSet)) {

			DataReader reader = new DataReader();
			try {
				reader.readFile("docs/diveData.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<DataPoint> data = reader.getDataList();
			dataSize = data.size();

			if (!subset.isEmpty()) {

				for (Integer i : subset) {
					for (DataPoint point : data) {
						point.setValue(i, 0);
					}
				}

				WeightedKNNClassifier knn = new WeightedKNNClassifier(data);

				double score = knn.classify(k);
				if (score > bestScore) {
					bestScore = score;
					bestSet = subset;
					System.out.println("Best subset so far: " + subset + " ["
							+ Math.round(bestScore / dataSize * 100) + "%]");
				}

			}
		}

		System.out.println("\nBest variable subset overall " + bestSet + "\n"
				+ Math.round(bestScore / dataSize * 100) + "% accuracy");

	}

	/**
	 * Get the best classification by trying a single K value and trying every
	 * subset of variables possible to return the best subset of dimensions and
	 * the accuracy score for that value of K
	 */
	private static void singleKBestSubset(int k) {

		Set<Integer> bestSet = null;
		double bestScore = 0.0;
		int dataSize = 0;

		// calculate power set
		Set<Integer> variableSet = new HashSet<Integer>();
		for (int i = 0; i < 8; i++) {
			variableSet.add(i);
		}

		for (Set<Integer> subset : powerSet(variableSet)) {

			DataReader reader = new DataReader();
			try {
				reader.readFile("docs/diveData.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<DataPoint> data = reader.getDataList();
			dataSize = data.size();

			if (!subset.isEmpty()) {

				for (Integer i : subset) {
					for (DataPoint point : data) {
						point.setValue(i, 0);
					}
				}

				SimpleKNNClassifier knn = new SimpleKNNClassifier(data);

				double score = knn.classify(k);
				if (score > bestScore) {
					bestScore = score;
					bestSet = subset;
					System.out.println("Best subset so far: " + subset + " ["
							+ Math.round(bestScore / dataSize * 100) + "%]");
				}

			}
		}

		System.out.println("\nBest variable subset overall " + bestSet + "\n"
				+ Math.round(bestScore / dataSize * 100) + "% accuracy");

	}

	/**
	 * Get the best classification by trying every K value and trying every
	 * subset of variables possible to return the best value for K, the best
	 * subset of dimensions and the accuracy score
	 */
	private static void bestKBestSubset() {

		Set<Integer> bestSet = null;
		double bestScore = 0.0;
		int dataSize = 0;
		int bestK = 0;

		// calculate power set
		Set<Integer> variableSet = new HashSet<Integer>();
		for (int i = 0; i < 8; i++) {
			variableSet.add(i);
		}

		for (Set<Integer> subset : powerSet(variableSet)) {

			DataReader reader = new DataReader();
			try {
				reader.readFile("docs/diveData.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}

			ArrayList<DataPoint> data = reader.getDataList();
			dataSize = data.size();

			if (!subset.isEmpty()) {

				for (Integer i : subset) {
					for (DataPoint point : data) {
						point.setValue(i, 0);
					}
				}

				for (int i = 0; i < dataSize; i++) {

					SimpleKNNClassifier knn = new SimpleKNNClassifier(data);

					double score = knn.classify(i);
					if (score > bestScore) {
						bestScore = score;
						bestSet = subset;
						bestK = i;

						System.out.println("Best subset so far: " + subset
								+ " [K = " + bestK + "] ["
								+ Math.round(bestScore / dataSize * 100)
								+ "%]");
					}

				}

			}
		}

		System.out.println("\nBest variable subset overall " + bestSet
				+ " [K = " + bestK + "] ["
				+ Math.round(bestScore / dataSize * 100) + "%]");

	}

	/**
	 * Calculates the power set of a given set
	 * @param originalSet The original set
	 * @return the power set of the original set
	 */
	private static <T> Set<Set<T>> powerSet(Set<T> originalSet) {

		Set<Set<T>> sets = new HashSet<Set<T>>();
		if (originalSet.isEmpty()) {
			sets.add(new HashSet<T>());
			return sets;
		}
		ArrayList<T> list = new ArrayList<T>(originalSet);
		T head = list.get(0);
		Set<T> rest = new HashSet<T>(list.subList(1, list.size())); 
		for (Set<T> set : powerSet(rest)) {
			Set<T> newSet = new HashSet<T>();
			newSet.add(head);
			newSet.addAll(set);
			sets.add(newSet);
			sets.add(set);
		}		

		return sets;
	}

	/**
	 * Get the best classification by trying a single K value and weighting the
	 * distances to return the accuracy score for that value of K
	 */
	private static void singleKWeighted(ArrayList<DataPoint> data, int k) {

		WeightedKNNClassifier knn = new WeightedKNNClassifier(data);

		double score = knn.classify(k);

		System.out.println("[K = " + k + "] [" + (int) score + "/" + data.size()
				+ "] ["	+ Math.round(score / data.size() * 100) + "%]");

	}

	/**
	 * Get the best classification by trying every possible K and weighting the
	 * distances to return the best K and accuracy score
	 */
	private static void bestKWeighted(ArrayList<DataPoint> data) {

		int bestK = 0;
		double bestScore = 0.0;
		int dataSize = data.size();

		for (int i = 0; i < dataSize; i++) {

			WeightedKNNClassifier knn = new WeightedKNNClassifier(data);

			double score = knn.classify(i);
			if (score > bestScore) {
				bestScore = score;
				bestK = i;
			}

			System.out.println("[K = " + i + "] [" + (int) score
					+ "/" + dataSize + "] ["
					+ Math.round(score / dataSize * 100) + "%]");

		}

		System.out.println("The best K is " + bestK + " with "
				+ Math.round(bestScore / dataSize * 100) + "% accuracy");

	}

	/**
	 * Get the best classification by doing the standard KNN classification,
	 * trying a single K value and returning the accuracy score
	 */
	private static void singleK(ArrayList<DataPoint> data, int k) {

		SimpleKNNClassifier knn = new SimpleKNNClassifier(data);

		double score = knn.classify(k);

		System.out.println("[K = " + k + "] [" + (int) score
				+ "/" + data.size() + "] ["
				+ Math.round(score / data.size() * 100) + "%]");

	}

	/**
	 * Get the best classification by doing the standard KNN classification but
	 * trying every K value and returning the best K and accuracy score
	 */
	private static void bestK(ArrayList<DataPoint> data) {

		int bestK = 0;
		double bestScore = 0.0;
		int dataSize = data.size();

		for (int i = 0; i < dataSize; i++) {

			SimpleKNNClassifier knn = new SimpleKNNClassifier(data);

			double score = knn.classify(i);
			if (score > bestScore) {
				bestScore = score;
				bestK = i;
			}

			System.out.println("[K = " + i + "] [" + (int) score
					+ "/" + dataSize + "] ["
					+ Math.round(score / dataSize * 100) + "%]");

		}

		System.out.println("The best K is " + bestK + " with "
				+ Math.round(bestScore / dataSize * 100) + "% accuracy");

	}

}
