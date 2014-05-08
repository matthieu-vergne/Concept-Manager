package org.conceptmanager.evaluation;

/**
 * An {@link Evaluator} allows to valuate an object on a specific
 * {@link Attribute}. This valuation can be quantitative, such as evaluating the
 * height of a person (i.e. measuring), or qualitative, such as evaluating the
 * color of an apple (i.e. classify). The {@link Attribute} is assumed to be
 * constant, as it represent the identified purpose of the {@link Evaluator},
 * but the {@link Value} can change over time.
 * 
 * @author Matthieu Vergne <matthieu.vergne@gmail.com>
 * 
 * @param <Attribute>
 *            type of element which represents the kind of evaluation provided
 * @param <Value>
 *            type of quantity or a quality provided by the evaluation,
 *            typically (but not necessarily) an extension of {@link Number} for
 *            a quantity or {@link Enum} for a quality
 */
public interface Evaluator<Attribute, Value> {

	/**
	 * This methods should provide the {@link Attribute} evaluated, whether it
	 * is quantitative (e.g. size, weight, speed) or not (e.g. taste, color).
	 * This {@link Attribute} should not change over time.
	 * 
	 * @return the constant {@link Attribute} evaluated
	 */
	public Attribute getAttribute();

	/**
	 * 
	 * @param object
	 *            the object we want to evaluate the {@link Attribute} of
	 * @return the {@link Value} of the {@link Attribute} for the given object
	 * @throws CannotEvaluateException
	 *             if the {@link Attribute} cannot be evaluated for the given
	 *             object
	 */
	public Value evaluates(Object object) throws CannotEvaluateException;

	@SuppressWarnings("serial")
	public static class CannotEvaluateException extends Exception {
		public <Attribute> CannotEvaluateException(Attribute attribute,
				Object object) {
			this(attribute, object, null, null);
		}

		public <Attribute> CannotEvaluateException(Attribute attribute,
				Object object, String message) {
			this(attribute, object, message, null);
		}

		public <Attribute> CannotEvaluateException(Attribute attribute,
				Object object, Throwable cause) {
			this(attribute, object, null, cause);
		}

		public <Attribute> CannotEvaluateException(Attribute attribute,
				Object object, String message, Throwable cause) {
			super("We cannot evaluate " + attribute + " on " + object
					+ (message == null ? "" : ": " + message), cause);
		}
	}
}
