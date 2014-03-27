package org.conceptmanager.symbol;

/**
 * A {@link SymbolManager} aims at learning relations between {@link Symbol}s
 * and exploit them to answer to requests. A request is a specific
 * {@link Symbol} for which an answer, another {@link Symbol}, is expected to be
 * returned. Such answer is generated via {@link #request(Object)}. The learning
 * process consists in using/identifying a logics to produce these answers. To
 * support this learning, the notion of {@link Judgment} allows to compare the
 * {@link Symbol}s answered. This {@link Judgment} can be provided by a
 * {@link SymbolJudge} and learned via
 * {@link #learn(Object, Object, Comparable)}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Symbol>
 * @param <Judgment>
 */
public interface SymbolManager<Symbol, Judgment extends Comparable<Judgment>> {

	/**
	 * This method aims at requesting an answer, by providing a specific
	 * {@link Symbol} and waiting for another {@link Symbol} in return. A basic
	 * example is a question (request {@link Symbol}) for which we expect an
	 * answer to be provided (answer {@link Symbol}).
	 * 
	 * @param request
	 *            request's {@link Symbol}
	 * @return answer's {@link Symbol}
	 */
	public Symbol request(Symbol request);

	/**
	 * This method aims at providing learning evidences to refine the behavior
	 * of {@link #request(Object)}. In other words, if a given request
	 * {@link Symbol}, provided as argument to {@link #request(Object)}, results
	 * in a given answer {@link Symbol}, then a specific {@link Judgment} value
	 * is provided. This {@link Judgment} value aims at indicating the fitting
	 * of a given answer to the given request. In particular, an answer having a
	 * superior {@link Judgment} value than another answer is assumed to fit
	 * better, while the actual interpretation of which {@link Judgment} values
	 * correspond to "fits" or "does not fit" is an internal implementation
	 * choice. Such {@link Judgment} value can be provided for instance from a
	 * {@link SymbolJudge}.
	 * 
	 * @param request
	 *            request's {@link Symbol}
	 * @param answer
	 *            answer's {@link Symbol}
	 * @param judgment
	 *            the {@link Judgment} value assigned to this request-answer
	 *            pairing
	 */
	public void learn(Symbol request, Symbol answer, Judgment judgment);
}
