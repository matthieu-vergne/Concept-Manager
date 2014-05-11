package org.conceptmanager.sensing;

/**
 * A {@link Sensor} provides a the input to feel the world, whether it is the
 * external world (environment) or internal world (introspection). From the
 * {@link Value}s returned by each available {@link Sensor}, one can build
 * concepts of the world and related concepts.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Value>
 */
public interface Sensor<Value> {

	/**
	 * 
	 * @return the current {@link Value} sensed by this {@link Sensor}
	 */
	public Value sense();
}
