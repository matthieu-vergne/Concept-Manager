package org.conceptmanager.evaluation.impl;

import static org.junit.Assert.*;

import org.conceptmanager.evaluation.Evaluator.CannotEvaluateException;
import org.junit.Test;

public class ManualEvaluatorTest {

	@Test
	public void testAttribute() {
		assertEquals("test", new ManualEvaluator<Object, Object>("test").getAttribute());
		assertEquals(123, new ManualEvaluator<Object, Object>(123).getAttribute());
		assertEquals(null, new ManualEvaluator<Object, Object>(null).getAttribute());
		Object attribute = new Object();
		assertSame(attribute, new ManualEvaluator<Object, Object>(attribute).getAttribute());
	}

	@Test
	public void testException() {
		ManualEvaluator<Object, Object> evaluator = new ManualEvaluator<Object, Object>(
				null);
		assertFalse(evaluator.isEvaluationImpossible());
		try {
			evaluator.evaluates(null);
		} catch (CannotEvaluateException e) {
			fail("Exception thrown by default.");
		}

		evaluator.setEvaluationImpossible(true);
		assertTrue(evaluator.isEvaluationImpossible());
		try {
			evaluator.evaluates(null);
			fail("Exception not thrown.");
		} catch (CannotEvaluateException e) {
		}

		evaluator.setEvaluationImpossible(false);
		assertFalse(evaluator.isEvaluationImpossible());
		try {
			evaluator.evaluates(null);
		} catch (CannotEvaluateException e) {
			fail("Exception thrown.");
		}
	}

	@Test
	public void testValue() throws CannotEvaluateException {
		ManualEvaluator<Object, Object> evaluator = new ManualEvaluator<Object, Object>(
				null);
		assertNull(evaluator.evaluates(null));

		evaluator.setValue(123);
		assertEquals(123, evaluator.evaluates(null));

		evaluator.setValue("test");
		assertEquals("test", evaluator.evaluates(null));

		evaluator.setValue(null);
		assertEquals(null, evaluator.evaluates(null));

		Object value = new Object();
		evaluator.setValue(value);
		assertEquals(value, evaluator.evaluates(null));
	}

}
