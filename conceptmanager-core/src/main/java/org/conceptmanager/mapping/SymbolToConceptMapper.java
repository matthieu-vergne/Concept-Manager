package org.conceptmanager.mapping;

import org.conceptmanager.util.Mapper;

/**
 * A {@link SymbolToConceptMapper} aims at providing a sequence of
 * {@link Concept}s corresponding to a given {@link Symbol}. The {@link Weight}s
 * associated to each {@link Concept} indicates how relevant the {@link Concept}
 * seems to be to understand the {@link Symbol}.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 *
 * @param <Symbol>
 * @param <Concept>
 * @param <Weight>
 */
public interface SymbolToConceptMapper<Symbol, Concept, Weight extends Comparable<Weight>>
		extends Mapper<Symbol, Concept, Weight> {

}
