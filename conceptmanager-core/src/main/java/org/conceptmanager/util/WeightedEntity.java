package org.conceptmanager.util;

/**
 * A {@link WeightedEntity} is a simple wrapper to relate an {@link Entity} to a
 * {@link Weight}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Entity>
 * @param <Weight>
 */
public interface WeightedEntity<Entity, Weight extends Comparable<Weight>> {

	/**
	 * 
	 * @return the {@link Entity} considered
	 */
	public Entity getEntity();

	/**
	 * 
	 * @return the {@link Weight} associated to the {@link Entity} considered
	 */
	public Weight getWeight();
}
