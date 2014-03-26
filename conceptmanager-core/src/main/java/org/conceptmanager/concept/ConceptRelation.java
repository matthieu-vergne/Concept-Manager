package org.conceptmanager.concept;

/**
 * A {@link ConceptRelation} is a relation holding between two {@link Concept}s,
 * a left {@link Concept} and a right {@link Concept}. This relation can be
 * assigned a semantic by providing a meaning to the {@link Concept} which
 * describes this relation from left to right or from right to left. It does not
 * matter whether this direction is "right" or "wrong", because it depends on
 * the interpretation made of "right to left" and "left to right". Both these
 * points can be decided from the internal implementation, thus it is a matter
 * of internal consistency rather than absolute interpretation. However, given
 * that this relation represent a semantic in opposite directions, we can assume
 * that the {@link Concept}s right-to-left and left-to-right are mirroring each
 * other (one provides the reversed meaning of the other, like "to eat" and
 * "to be eaten").
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Concept>
 */
public interface ConceptRelation<Concept> {

	/**
	 * 
	 * @return the left-hand-side {@link Concept}
	 */
	public Concept getLeftConcept();

	/**
	 * 
	 * @return the right-hand-side {@link Concept}
	 */
	public Concept getRightConcept();

	/**
	 * 
	 * @return the {@link Concept} providing the meaning of the left-to-right
	 *         direction
	 */
	public Concept getLeftToRightConcept();

	/**
	 * 
	 * @return the {@link Concept} providing the meaning of the right-to-left
	 *         direction
	 */
	public Concept getRightToLeftConcept();
}
