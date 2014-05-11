package org.conceptmanager.action;

import org.conceptmanager.sensing.Sensor;

/**
 * An {@link Actuator} aims at interacting with the world. The resulting
 * consequences can be then sensed through available {@link Sensor}s in order to
 * build or check internal concepts.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 */
public interface Actuator {

	/**
	 * Execute the action related to this {@link Actuator}
	 */
	public void acts();
}
