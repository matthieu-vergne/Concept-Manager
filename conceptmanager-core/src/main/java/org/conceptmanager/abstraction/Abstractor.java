package org.conceptmanager.abstraction;

import org.conceptmanager.modeling.Model;

/**
 * An {@link Abstractor} aims at providing {@link Abstraction}s, which are
 * {@link Model}s having a specific focus on some attributes. More formally, an
 * {@link Abstractor} builds an {@link Abstraction} by building a {@link Model}
 * for which the attributes and values depends solely on the ones of the
 * original {@link Model}. Thus, unless the {@link Abstractor} considers
 * additional assumptions, the resulting {@link Model} is at most as informative
 * as the original one, justifying the name of {@link Abstraction}.<br/>
 * <br/>
 * In the case where additional assumptions are considered by the
 * {@link Abstractor}, attributes can be added or values can be changed compared
 * to the original {@link Model}. But these assumptions are provided by the
 * {@link Abstractor} independently of the {@link Model} abstracted (otherwise
 * such dependence is also an assumption provided by the {@link Abstractor}). In
 * such a case, we can still speak about the resulting {@link Model} being an
 * {@link Abstraction} given the knowledge of the {@link Abstractor}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Abstraction>
 */
public interface Abstractor<Abstraction extends Model> {

	/**
	 * This method aims at building a new {@link Model}, called
	 * {@link Abstraction}, based on an initial {@link Model}.
	 * 
	 * @param model
	 *            the {@link Model} we want to abstract
	 * @return the {@link Abstraction} of the given {@link Model}
	 */
	public Abstraction abstracts(Model model);
}
