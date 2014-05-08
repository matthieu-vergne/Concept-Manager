package org.conceptmanager.modeling.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.conceptmanager.evaluation.Evaluator;
import org.conceptmanager.evaluation.Evaluator.CannotEvaluateException;
import org.conceptmanager.modeling.Model;

/**
 * A {@link DynamicModel} is a {@link Model} which computes the attribute values
 * of a given object on the fly. {@link Evaluator}s are provided to this model
 * in order to compute the values when asked. No cache management is performed,
 * thus two calls of {@link #getValueFor(Object)} on the same attribute results
 * in two calls of the corresponding {@link Evaluator}.<br/>
 * <br/>
 * Each {@link DynamicModel} is assigned to a unique object, provided by
 * {@link #getObject()}, and all the {@link Evaluator}s are applied on this
 * object to compute the attribute values.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class DynamicModel implements Model {

	private final Object object;
	private final Map<Object, Evaluator<?, ?>> evaluators = new HashMap<Object, Evaluator<?, ?>>();

	public DynamicModel(Object object) {
		this.object = object;
	}

	public <Attribute, Value> DynamicModel(
			Object object,
			Collection<? extends Evaluator<? extends Attribute, ? extends Value>> evaluators) {
		this(object);
		for (Evaluator<? extends Attribute, ? extends Value> evaluator : evaluators) {
			addEvaluator(evaluator);
		}
	}

	/**
	 * 
	 * @return the object represented by this {@link DynamicModel}
	 */
	public Object getObject() {
		return object;
	}

	/**
	 * This method simply assign a given {@link Evaluator} to a given
	 * {@link Attribute}. At the opposite of {@link #addEvaluator(Evaluator)},
	 * no check is performed whether such {@link Attribute} can be evaluated
	 * with the provided {@link Evaluator} or if another {@link Evaluator} is
	 * already assigned. The new {@link Evaluator} is assigned to the
	 * {@link Attribute}, no matter what.
	 * 
	 * @param attribute
	 *            the {@link Attribute} to evaluate
	 * @param evaluator
	 *            the {@link Evaluator} to assign to the {@link Attribute}
	 */
	public <Attribute, Value> void setEvaluatorFor(Attribute attribute,
			Evaluator<? extends Object, ? extends Value> evaluator) {
		if (evaluator == null) {
			throw new NullPointerException("No evaluator has been provided.");
		} else {
			evaluators.put(attribute, evaluator);
		}
	}

	/**
	 * 
	 * @param attribute
	 *            the {@link Attribute} to evaluate
	 * @return the {@link Evaluator} assigned to the {@link Attribute}
	 */
	@SuppressWarnings("unchecked")
	public <Attribute, Value> Evaluator<Attribute, Value> getEvaluatorFor(
			Attribute attribute) {
		return (Evaluator<Attribute, Value>) evaluators.get(attribute);
	}

	/**
	 * This method removes the assignment of a specific {@link Attribute}. If
	 * not assignment has been set, this method has no effect.
	 * 
	 * @param attribute
	 *            the {@link Attribute} to forget
	 */
	public <Attribute> void removeEvaluatorFor(Attribute attribute) {
		evaluators.remove(attribute);
	}

	/**
	 * This method, as {@link #setEvaluatorFor(Object, Evaluator)}, assign an
	 * {@link Evaluator} to a specific {@link Attribute}. The {@link Attribute}
	 * is inferred from {@link Evaluator#getAttribute()}. The main difference
	 * with {@link #setEvaluatorFor(Object, Evaluator)} is that, if another
	 * {@link Evaluator} is assigned to the same {@link Attribute}, an exception
	 * is thrown.
	 * 
	 * @param evaluator
	 *            the {@link Evaluator} to add
	 * @throws IllegalArgumentException
	 *             if the {@link Attribute} has already an {@link Evaluator}
	 *             assigned to it
	 */
	public <Attribute, Value> void addEvaluator(
			Evaluator<Attribute, ? extends Value> evaluator) {
		Attribute attribute = evaluator.getAttribute();
		if (evaluators.containsKey(attribute)
				&& !evaluators.get(attribute).equals(evaluator)) {
			throw new IllegalArgumentException(
					"Another evaluator is currently used for the attribute "
							+ attribute + ": " + evaluators.get(attribute));
		} else {
			evaluators.put(attribute, evaluator);
		}
	}

	/**
	 * This method removes all the assignments of a given {@link Evaluator}. As
	 * {@link #setEvaluatorFor(Object, Evaluator)} allows to assign the same
	 * {@link Evaluator} to different {@link Attribute}s, this method removes
	 * all of them at once, at the opposite of
	 * {@link #removeEvaluatorFor(Object)}.
	 * 
	 * @param evaluator
	 *            the {@link Evaluator} to remove
	 */
	public <Attribute, Value> void removeEvaluator(
			Evaluator<Attribute, ? extends Value> evaluator) {
		Iterator<Evaluator<?, ?>> iterator = evaluators.values().iterator();
		while (iterator.hasNext()) {
			Evaluator<?, ?> evaluator2 = iterator.next();
			if (evaluator.equals(evaluator2)) {
				iterator.remove();
			} else {
				// continue searching
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Attribute> Collection<? extends Attribute> getAttributes() {
		return (Collection<Attribute>) evaluators.keySet();
	}

	/**
	 * @return the {@link Value} of the given {@link Attribute},
	 *         <code>null</code> if this {@link Value} cannot be computed
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <Attribute, Value> Value getValueFor(Attribute attribute) {
		Evaluator<Attribute, Value> evaluator = (Evaluator<Attribute, Value>) evaluators
				.get(attribute);
		if (evaluator == null) {
			throw new UnknownAttributeException(attribute);
		} else {
			try {
				return evaluator.evaluates(object);
			} catch (CannotEvaluateException e) {
				return null;
			}
		}
	}
}
