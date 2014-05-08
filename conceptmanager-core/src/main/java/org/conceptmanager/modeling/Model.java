package org.conceptmanager.modeling;

import java.util.Collection;

import org.conceptmanager.modeling.impl.DynamicModel;
import org.conceptmanager.modeling.impl.StaticModel;

/**
 * A {@link Model} is a representation of something (an {@link Object}) as a set
 * of attributes and their values. The assumptions are the following:
 * <ul>
 * <li>an attribute is provided ⇔ it is the purpose of the {@link Model} to
 * provide it</li>
 * <li>an attribute has a <code>null</code> value ⇔ its value is unknown</li>
 * <li>the value of an attribute can change overtime</li>
 * </ul>
 * Thus, the set of attributes returned by {@link #getAttributes()} is assumed
 * to be constant if the purpose of the {@link Model} does not change overtime.
 * A {@link StaticModel} is such an example, while a {@link DynamicModel} does
 * not have fixed purpose and can evolve overtime. Also, if a value can have a
 * "null" value (not an absence of value but a value which express some kind of
 * nullity), a specific object should be used to represent it.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public interface Model {

	/**
	 * 
	 * @return the set of {@link Attribute}s managed by this {@link Model}
	 */
	public <Attribute> Collection<? extends Attribute> getAttributes();

	/**
	 * 
	 * @param attribute
	 *            the {@link Attribute} we want the {@link Value} for
	 * @return the {@link Value} of the {@link Attribute}, <code>null</code> if
	 *         the value is unknown
	 * @throws UnknownAttributeException
	 *             if the requested {@link Attribute} is not provided by this
	 *             {@link Model} through {@link #getAttributes()}
	 */
	public <Attribute, Value> Value getValueFor(Attribute attribute)
			throws UnknownAttributeException;

	@SuppressWarnings("serial")
	public static class UnknownAttributeException extends RuntimeException {
		public <Attribute> UnknownAttributeException(Attribute attribute) {
			super("Unknown attribute: " + attribute);
		}
	}
}
