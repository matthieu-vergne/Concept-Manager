package org.conceptmanager.sample1;

import java.util.Arrays;
import java.util.Collection;

import org.conceptmanager.symbol.SymbolJudge;
import org.conceptmanager.symbol.SymbolManager;

/**
 * In this sample, we have a little set of simple symbols and we consider that,
 * when we request a symbol, any similar symbol (the same without caring about
 * case-sensitivity) is a good answer. The judgments are simple
 * <code>true</code>/<code>false</code> values.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Sample1 {

	public static void main(String[] args) {
		SymbolJudge<String, Boolean> judge = new Judge();
		SymbolManager<String, Boolean> manager = new Manager();

		Collection<String> symbols = Arrays.asList("A", "B", "C", "a", "b",
				"c", "1", "2", "3");

		System.out.println("FIRST REQUESTS - NO LEARNING");
		for (String request : symbols) {
			String answer = manager.request(request);
			System.out.println(request + "? " + answer + " -> "
					+ judge.judge(request, answer));
		}

		boolean allCorrect;
		do {
			allCorrect = true;
			System.out.println("RETRY REQUESTS - LEARNING");
			for (String request : symbols) {
				String answer = manager.request(request);
				Boolean judgment = judge.judge(request, answer);
				allCorrect = allCorrect && judgment;
				manager.learn(request, answer, judgment);
				System.out.println(request + "? " + answer + " -> " + judgment);
			}
		} while (!allCorrect);
	}
}
