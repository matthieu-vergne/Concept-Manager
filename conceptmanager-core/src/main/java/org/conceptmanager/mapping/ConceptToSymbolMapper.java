package org.conceptmanager.mapping;

import org.conceptmanager.util.Mapper;

/**
 * A {@link ConceptToSymbolMapper} aims at providing a sequence of
 * {@link Symbol}s corresponding to a given {@link Concept}. The {@link Weight}s
 * associated to each {@link Symbol} indicate how relevant the {@link Symbol}
 * seems to be to represent the {@link Concept}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Concept>
 * @param <Symbol>
 * @param <Weight>
 */
public interface ConceptToSymbolMapper<Concept, Symbol, Weight extends Comparable<Weight>>
		extends Mapper<Concept, Symbol, Weight> {

}
