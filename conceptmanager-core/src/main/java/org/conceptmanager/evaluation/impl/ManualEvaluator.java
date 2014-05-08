package org.conceptmanager.evaluation.impl;

import org.conceptmanager.evaluation.Evaluator;

/**
 * A {@link ManualEvaluator} aims at providing an {@link Evaluator} which has a
 * completely manual valuation, thus allowing to set the value on the fly. It is
 * also possible to request throwing the {@link CannotEvaluateException}
 * exception. See {@link #setValue(Object)} and
 * {@link #setEvaluationImpossible(boolean)} for more details.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Attribute>
 * @param <Value>
 */
public class ManualEvaluator<Attribute, Value> implements
		Evaluator<Attribute, Value> {

	private final Attribute attribute;
	private Value value = null;
	private boolean isEvaluationImpossible = false;

	public ManualEvaluator(Attribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public Value evaluates(Object object) throws CannotEvaluateException {
		if (isEvaluationImpossible) {
			throw new CannotEvaluateException(getAttribute(), object);
		} else {
			return value;
		}
	}

	/**
	 * 
	 * @param value
	 *            the value to return during the evaluation if
	 *            {@link #isEvaluationImpossible()} is <code>false</code>,
	 *            <code>null</code> by default
	 */
	public void setValue(Value value) {
		this.value = value;
	}

	/**
	 * 
	 * @param isEvaluationImpossible
	 *            <code>true</code> if {@link #evaluates(Object)} should throw
	 *            the {@link CannotEvaluateException} exception,
	 *            <code>false</code> if it should return the {@link Value} given
	 *            to {@link #setValue(Object)}, <code>false</code> by default
	 */
	public void setEvaluationImpossible(boolean isEvaluationImpossible) {
		this.isEvaluationImpossible = isEvaluationImpossible;
	}

	/**
	 * 
	 * @return the value provided to {@link #setEvaluationImpossible(boolean)}
	 */
	public boolean isEvaluationImpossible() {
		return isEvaluationImpossible;
	}

}
