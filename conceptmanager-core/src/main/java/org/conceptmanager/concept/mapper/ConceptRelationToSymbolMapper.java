package org.conceptmanager.concept.mapper;

import org.conceptmanager.concept.ConceptRelation;
import org.conceptmanager.util.Mapper;

/**
 * A {@link ConceptRelationToSymbolMapper} is equivalent to a
 * {@link ConceptToSymbolMapper}, excepted that the {@link Concept} is assumed
 * to be considered at a relational level. Such {@link Concept}s are returned by
 * the {@link ConceptRelation#getLeftToRightConcept()} and
 * {@link ConceptRelation#getRightToLeftConcept()} methods. Thus, the
 * {@link Symbol}s returned by this {@link ConceptRelationToSymbolMapper} are
 * {@link Symbol}s used to relate the {@link Symbol}s of other {@link Concept}s
 * (provided by a {@link ConceptToSymbolMapper}). For instance, while a
 * {@link Concept} could be represented by the {@link Symbol} "food" if used
 * alone, at a relational level it could be represented by the {@link Symbol}
 * "eat", such as "cats <i>eat</i> mouses", which has the same meaning as "mouses
 * are <i>food</i> for cats").
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Concept>
 * @param <Symbol>
 * @param <Weight>
 */
public interface ConceptRelationToSymbolMapper<Concept, Symbol, Weight extends Comparable<Weight>>
		extends Mapper<Concept, Symbol, Weight> {

}
