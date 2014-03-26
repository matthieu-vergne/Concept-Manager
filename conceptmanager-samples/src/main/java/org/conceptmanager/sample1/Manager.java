package org.conceptmanager.sample1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.conceptmanager.SymbolManager;

/**
 * This {@link Manager} is a simple {@link SymbolManager} which learns the
 * symbols and how they relate together during the learning phase (
 * {@link #learn(String, String, Boolean)}). Their relations are directed and
 * represented by a weight: 1 if it is a good request-answer pair, -1 if it is
 * not, 0 otherwise. The answers to any request are provided depending on the
 * learned weights.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Manager implements SymbolManager<String, Boolean> {

	/**
	 * The symbols known.
	 */
	Set<String> symbols = new HashSet<String>();
	/**
	 * The request-answer weights.
	 */
	Map<String, Map<String, Double>> weights = new HashMap<String, Map<String, Double>>();

	/**
	 * The known symbols are browsed and one of the symbols with the highest
	 * weight is returned.
	 */
	@Override
	public String request(String request) {
		String selectedAnswer = null;
		double selectedWeight = 0;
		for (String answer : symbols) {
			double weight = retrieveWeightFor(request, answer);
			if (selectedAnswer == null || weight > selectedWeight) {
				selectedAnswer = answer;
				selectedWeight = weight;
			} else {
				continue;
			}
		}
		return selectedAnswer;
	}

	/**
	 * If no judgment is provided, the weight is preserved (0 by default),
	 * otherwise a value of 1 or -1 is learned for, respectively, an agreement (
	 * <code>true</code>) or a disagreement (<code>false</code>).
	 */
	@Override
	public void learn(String request, String answer, Boolean judgment) {
		symbols.add(request);
		symbols.add(answer);

		Map<String, Double> answers = weights.get(request);
		if (answers == null) {
			answers = new HashMap<String, Double>();
			weights.put(request, answers);
		} else {
			// keep current value
		}
		double weight = retrieveWeightFor(request, answer);
		answers.put(answer, judgment == null ? weight : judgment ? 1.0 : -1.0);
	}

	/**
	 * 
	 * @param request
	 *            the request
	 * @param answer
	 *            the answer
	 * @return the weight learned, 0 if no information
	 */
	private double retrieveWeightFor(String request, String answer) {
		Double weight;
		try {
			weight = weights.get(request).get(answer);
		} catch (NullPointerException e) {
			weight = null;
		}
		return weight == null ? 0 : weight;
	}
}
