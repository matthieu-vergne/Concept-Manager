package org.conceptmanager.modeling.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.conceptmanager.modeling.Model;

/**
 * A {@link StaticModel} provide a simple way to get a static (in the sense of
 * non-dynamic) representation.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public class StaticModel implements Model {

	private final Map<Object, Object> attributes;

	/**
	 * Instantiate a new model on the fly by providing the specific
	 * {@link Attribute}s and their {@link Value}s.
	 * 
	 * @param attributes
	 *            the {@link Attribute}s and {@link Value}s to consider
	 */
	public <Attribute, Value> StaticModel(Map<Attribute, Value> attributes) {
		this.attributes = Collections
				.unmodifiableMap(new HashMap<Object, Object>(attributes));
	}

	/**
	 * Instantiate a snapshot of another {@link Model}. Even if the input
	 * {@link Model} changes after this {@link StaticModel} has been
	 * instantiated, the {@link StaticModel} will still have the same attributes
	 * and values than when it was instantiated.
	 * 
	 * @param model
	 *            the {@link Model} from which to take a snapshot
	 */
	public StaticModel(Model model) {
		HashMap<Object, Object> temp = new HashMap<Object, Object>();
		for (Object attribute : model.getAttributes()) {
			temp.put(attribute, model.getValueFor(attribute));
		}
		this.attributes = Collections.unmodifiableMap(temp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Attribute> Collection<? extends Attribute> getAttributes() {
		return (Collection<? extends Attribute>) attributes.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Attribute, Value> Value getValueFor(Attribute attribute) {
		if (attributes.containsKey(attribute)) {
			return (Value) attributes.get(attribute);
		} else {
			throw new UnknownAttributeException(attribute);
		}
	}

}
