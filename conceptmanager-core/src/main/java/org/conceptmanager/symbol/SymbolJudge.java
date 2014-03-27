package org.conceptmanager.symbol;

/**
 * A {@link SymbolJudge} aims at evaluating the fitting of an answer to a
 * request, as provided by {@link SymbolManager#request(Object)}. It can be seen
 * as an oracle in the sense that "it is the one which knows". In particular, it
 * provides a {@link Judgment} value of the fitting between a request and an
 * answer via {@link #judge(Object, Object)}. However, an oracle is only a
 * particular use which usually relies on an external authority to provide such
 * {@link Judgment} value. Another use is to consider an internal feedback loop,
 * such as a sensor which evaluates the distance between the request and the
 * answer provided (e.g. how close the answer is to the request for a repetition
 * purpose) so that a self-correction can be supported.
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
