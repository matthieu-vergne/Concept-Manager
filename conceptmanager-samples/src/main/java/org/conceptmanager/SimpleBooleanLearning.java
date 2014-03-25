package org.conceptmanager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleBooleanLearning {

	public static void main(String[] args) {
		SymbolJudge<String, Boolean> judge = createJudge();
		SymbolManager<String, Boolean> manager = createManager();

		Collection<String> symbols = Arrays.asList("A", "B", "C", "a", "b",
				"c", "1", "2", "3");

		System.out.println("FIRST REQUEST - DISCOVER SYMBOLS");
		for (String request : symbols) {
			String answer = manager.request(request);
			System.out.println(request + "? " + answer + " -> "
					+ judge.judge(request, answer));
		}

		System.out.println("RETRY REQUEST - KNOWN SYMBOLS");
		for (String request : symbols) {
			String answer = manager.request(request);
			System.out.println(request + "? " + answer + " -> "
					+ judge.judge(request, answer));
		}

		boolean allCorrect;
		do {
			allCorrect = true;
			System.out.println("RETRY REQUEST - LEARN");
			for (String request : symbols) {
				String answer = manager.request(request);
				Boolean judgment = judge.judge(request, answer);
				allCorrect = allCorrect && judgment;
				manager.learn(request, answer, judgment);
				System.out.println(request + "? " + answer + " -> " + judgment);
			}
		} while (!allCorrect);
	}

	private static SymbolManager<String, Boolean> createManager() {
		return new SymbolManager<String, Boolean>() {

			Set<String> symbols = new HashSet<String>();
			Map<String, Map<String, Double>> memory = new HashMap<String, Map<String, Double>>();
			double memoryInertia = 0.9;

			@Override
			public String request(String request) {
				symbols.add(request);
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

			@Override
			public void learn(String request, String answer, Boolean judgment) {
				symbols.add(request);
				symbols.add(answer);
				double expectation = retrieveWeightFor(request, answer);
				expectation = memoryInertia * expectation + (1 - memoryInertia)
						* (judgment ? 1 : -1);
				memory.get(request).put(answer, expectation);
			}

			private double retrieveWeightFor(String request, String answer) {
				Map<String, Double> answers = memory.get(request);
				if (answers == null) {
					answers = new HashMap<String, Double>();
					memory.put(request, answers);
				} else {
					// keep current value
				}
				Double weight = answers.get(answer);
				return weight == null ? 0 : weight;
			}
		};
	}

	private static SymbolJudge<String, Boolean> createJudge() {
		return new SymbolJudge<String, Boolean>() {

			@Override
			public Boolean judge(String request, String answer) {
				return request.equalsIgnoreCase(answer);
			}
		};
	}
}
