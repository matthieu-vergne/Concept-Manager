package org.conceptmanager;

/**
 * A {@link SymbolJudge} aims at evaluating the fitting of an answer to a
 * request, as provided by {@link SymbolManager#request(Object)}. It can be seen
 * as an oracle in the sense that "it is the one which knows". In particular, it
 * provides a {@link Judgment} value of the fitting between a request and an
 * answer via {@link #judge(Object, Object)}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Symbol>
 * @param <Judgment>
 */
public interface SymbolJudge<Symbol, Judgment extends Comparable<Judgment>> {

	/**
	 * This method aims at judging the fitting of an answer given its request.
	 * In other words, by giving a request {@link Symbol} to
	 * {@link SymbolManager#request(Object)}, an answer {@link Symbol} can be
	 * returned. This method provide a {@link Judgment} value to this answer,
	 * describing its fitting to the request.
	 * 
	 * @param request
	 *            request {@link Symbol}
	 * @param answer
	 *            answer {@link Symbol}
	 * @return {@link Judgment} value
	 */
	public Judgment judge(Symbol request, Symbol answer);
}
