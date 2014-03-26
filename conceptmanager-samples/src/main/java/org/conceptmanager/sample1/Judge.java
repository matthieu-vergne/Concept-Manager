package org.conceptmanager.sample1;

import org.conceptmanager.SymbolJudge;

/**
 * This {@link Judge} consider that if we request a given {@link String}, a good
 * answer is the same, case-insensitive {@link String}. Otherwise, it is a wrong
 * answer.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class Judge implements SymbolJudge<String, Boolean> {

	@Override
	public Boolean judge(String request, String answer) {
		return request.equalsIgnoreCase(answer);
	}
}
