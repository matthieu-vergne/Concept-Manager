package org.conceptmanager.util;

import java.util.Iterator;


/**
 * A {@link Mapper} aims at mapping a {@link SourceEntity} to some
 * {@link TargetEntity}s. In order to choose a {@link TargetEntity} among the
 * several provided, a {@link Weight} is associated to each {@link TargetEntity}
 * to represent its relevance. Thus, for a given {@link SourceEntity}, the
 * {@link Weight} can be different for a same {@link TargetEntity}. More
 * generally, such sequence of {@link TargetEntity}s, and their corresponding
 * {@link Weight}s, are not constrained: one {@link TargetEntity} can be
 * provided several time, with a different {@link Weight} at each time, in any
 * order, and so on. Is it not even ensured to get the same {@link TargetEntity}
 * s between two consecutive calls.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <SourceEntity>
 * @param <TargetEntity>
 * @param <Weight>
 */
public interface Mapper<SourceEntity, TargetEntity, Weight extends Comparable<Weight>> {

	/**
	 * This method aims at providing a sequence of {@link TargetEntity}s which
	 * correspond to a given {@link SourceEntity}. Each {@link TargetEntity} is
	 * associated to a {@link Weight} which indicates its relevance.
	 * 
	 * @param entity
	 *            the {@link SourceEntity}
	 * @return an {@link Iterator} of {@link TargetEntity}s with their
	 *         {@link Weight}s
	 */
	public Iterator<WeightedEntity<TargetEntity, Weight>> getWeightsFor(
			SourceEntity entity);
}
