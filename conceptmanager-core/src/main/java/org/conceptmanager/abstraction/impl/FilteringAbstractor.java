package org.conceptmanager.abstraction.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.conceptmanager.abstraction.Abstractor;
import org.conceptmanager.evaluation.Evaluator;
import org.conceptmanager.modeling.Model;
import org.conceptmanager.modeling.Model.UnknownAttributeException;
import org.conceptmanager.modeling.impl.DynamicModel;

/**
 * A {@link FilteringAbstractor} is an {@link Abstractor} which aims at
 * providing the most basic abstraction process: retain only specific attributes
 * among the ones available. The abstraction {@link Model} (output of
 * {@link #abstracts(Model)}) is a {@link DynamicModel} tied to the abstracted
 * {@link Model} (input of {@link #abstracts(Model)}). Thus, if the abstracted
 * {@link Model} changes, so do the abstraction {@link Model}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class FilteringAbstractor implements Abstractor<DynamicModel> {

	private final Collection<Evaluator<? extends Object, ? extends Object>> evaluators;

	/**
	 * 
	 * @param attributes
	 *            the {@link Attribute}s to retain
	 */
	public <Attribute> FilteringAbstractor(Collection<Attribute> attributes) {
		evaluators = new LinkedList<Evaluator<? extends Object, ? extends Object>>();
		for (final Attribute attribute : new HashSet<Attribute>(attributes)) {
			evaluators.add(new Evaluator<Object, Object>() {

				@Override
				public Object getAttribute() {
					return attribute;
				}

				@Override
				public Object evaluates(Object object)
						throws CannotEvaluateException {
					if (object instanceof Model) {
						Model model = (Model) object;
						try {
							return model.getValueFor(attribute);
						} catch (UnknownAttributeException e) {
							return null;
						}
					} else {
						throw new CannotEvaluateException(attribute, object);
					}
				}
			});
		}
	}

	@Override
	public DynamicModel abstracts(Model model) {
		return new DynamicModel(model, evaluators);
	}

}
