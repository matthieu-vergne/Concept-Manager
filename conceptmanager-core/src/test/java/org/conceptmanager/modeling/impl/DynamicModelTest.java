package org.conceptmanager.modeling.impl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import org.conceptmanager.evaluation.Evaluator;
import org.conceptmanager.evaluation.impl.ManualEvaluator;
import org.junit.Test;

public class DynamicModelTest {

	@Test
	public void testAttributes() {
		DynamicModel model = new DynamicModel(null);
		assertEquals(0, model.getAttributes().size());

		Evaluator<Object, Object> evaluator = generateFakeEvaluator();

		model.setEvaluatorFor("A", evaluator);
		assertEquals(1, model.getAttributes().size());
		assertTrue(model.getAttributes().contains("A"));

		model.setEvaluatorFor("B", evaluator);
		assertEquals(2, model.getAttributes().size());
		assertTrue(model.getAttributes().contains("A"));
		assertTrue(model.getAttributes().contains("B"));

		model.setEvaluatorFor("C", evaluator);
		assertEquals(3, model.getAttributes().size());
		assertTrue(model.getAttributes().contains("A"));
		assertTrue(model.getAttributes().contains("B"));
		assertTrue(model.getAttributes().contains("C"));

		model.removeEvaluatorFor("B");
		assertEquals(2, model.getAttributes().size());
		assertTrue(model.getAttributes().contains("A"));
		assertTrue(model.getAttributes().contains("C"));

		model.removeEvaluatorFor("A");
		assertEquals(1, model.getAttributes().size());
		assertTrue(model.getAttributes().contains("C"));

		model.removeEvaluatorFor("C");
		assertEquals(0, model.getAttributes().size());
	}

	@Test
	public void testValues() {
		ManualEvaluator<Object, Object> evaluatorA = new ManualEvaluator<Object, Object>(
				null);
		ManualEvaluator<Object, Object> evaluatorB = new ManualEvaluator<Object, Object>(
				null);
		ManualEvaluator<Object, Object> evaluatorC = new ManualEvaluator<Object, Object>(
				null);

		DynamicModel model = new DynamicModel(null);
		model.setEvaluatorFor("A", evaluatorA);
		model.setEvaluatorFor("B", evaluatorB);
		model.setEvaluatorFor("C", evaluatorC);

		evaluatorA.setValue(123);
		evaluatorB.setValue(321);
		evaluatorC.setValue(0);
		assertEquals(123, model.getValueFor("A"));
		assertEquals(321, model.getValueFor("B"));
		assertEquals(0, model.getValueFor("C"));

		evaluatorA.setValue("test");
		evaluatorB.setValue(null);
		Object value = new Object();
		evaluatorC.setValue(value);
		assertEquals("test", model.getValueFor("A"));
		assertEquals(null, model.getValueFor("B"));
		assertEquals(value, model.getValueFor("C"));
	}

	@Test
	public void testObject() {
		Object object = new Object();
		DynamicModel model = new DynamicModel(object);
		assertSame(object, model.getObject());
	}

	@Test
	public void testSetGetEvaluatorForAttribute() {
		Evaluator<Object, Object> evaluatorA = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorB = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorC = generateFakeEvaluator();
		assertNotSame(evaluatorA, evaluatorB);
		assertNotSame(evaluatorA, evaluatorC);
		assertNotSame(evaluatorB, evaluatorC);

		DynamicModel model = new DynamicModel(null);
		assertSame(null, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));

		model.setEvaluatorFor("A", evaluatorA);
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));

		model.setEvaluatorFor("C", evaluatorC);
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(evaluatorC, model.getEvaluatorFor("C"));

		model.setEvaluatorFor("B", evaluatorB);
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(evaluatorB, model.getEvaluatorFor("B"));
		assertSame(evaluatorC, model.getEvaluatorFor("C"));
	}

	@Test
	public void testRemoveEvaluatorForAttribute() {
		Evaluator<Object, Object> evaluatorA = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorB = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorC = generateFakeEvaluator();
		assertNotSame(evaluatorA, evaluatorB);
		assertNotSame(evaluatorA, evaluatorC);
		assertNotSame(evaluatorB, evaluatorC);

		DynamicModel model = new DynamicModel(null);
		model.setEvaluatorFor("A", evaluatorA);
		model.setEvaluatorFor("B", evaluatorB);
		model.setEvaluatorFor("C", evaluatorC);
		model.setEvaluatorFor("B2", evaluatorB);
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(evaluatorB, model.getEvaluatorFor("B"));
		assertSame(evaluatorC, model.getEvaluatorFor("C"));
		assertSame(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluatorFor("C");
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(evaluatorB, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));
		assertSame(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluatorFor("B");
		assertSame(evaluatorA, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));
		assertSame(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluatorFor("A");
		assertSame(null, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));
		assertSame(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluatorFor("B2");
		assertSame(null, model.getEvaluatorFor("A"));
		assertSame(null, model.getEvaluatorFor("B"));
		assertSame(null, model.getEvaluatorFor("C"));
		assertSame(null, model.getEvaluatorFor("B2"));
	}

	@Test
	public void testAddEvaluator() {
		Evaluator<Object, Object> evaluatorA = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorB = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorC = generateFakeEvaluator();
		assertFalse(evaluatorA.getAttribute().equals(evaluatorB.getAttribute()));
		assertFalse(evaluatorA.getAttribute().equals(evaluatorC.getAttribute()));
		assertFalse(evaluatorB.getAttribute().equals(evaluatorC.getAttribute()));

		DynamicModel model = new DynamicModel(null);
		assertEquals(0, model.getAttributes().size());

		model.addEvaluator(evaluatorA);
		assertEquals(1, model.getAttributes().size());
		assertTrue(model.getAttributes().contains(evaluatorA.getAttribute()));
		assertEquals(evaluatorA,
				model.getEvaluatorFor(evaluatorA.getAttribute()));

		model.addEvaluator(evaluatorB);
		assertEquals(2, model.getAttributes().size());
		assertTrue(model.getAttributes().contains(evaluatorA.getAttribute()));
		assertTrue(model.getAttributes().contains(evaluatorB.getAttribute()));
		assertEquals(evaluatorA,
				model.getEvaluatorFor(evaluatorA.getAttribute()));
		assertEquals(evaluatorB,
				model.getEvaluatorFor(evaluatorB.getAttribute()));

		model.addEvaluator(evaluatorC);
		assertEquals(3, model.getAttributes().size());
		assertTrue(model.getAttributes().contains(evaluatorA.getAttribute()));
		assertTrue(model.getAttributes().contains(evaluatorB.getAttribute()));
		assertTrue(model.getAttributes().contains(evaluatorC.getAttribute()));
		assertEquals(evaluatorA,
				model.getEvaluatorFor(evaluatorA.getAttribute()));
		assertEquals(evaluatorB,
				model.getEvaluatorFor(evaluatorB.getAttribute()));
		assertEquals(evaluatorC,
				model.getEvaluatorFor(evaluatorC.getAttribute()));
	}

	@Test
	public void testRemoveEvaluator() {
		Evaluator<Object, Object> evaluatorA = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorB = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorC = generateFakeEvaluator();
		assertNotSame(evaluatorA, evaluatorB);
		assertNotSame(evaluatorA, evaluatorC);
		assertNotSame(evaluatorB, evaluatorC);

		DynamicModel model = new DynamicModel(null);
		model.setEvaluatorFor("A", evaluatorA);
		model.setEvaluatorFor("B", evaluatorB);
		model.setEvaluatorFor("C", evaluatorC);
		model.setEvaluatorFor("B2", evaluatorB);
		assertEquals(4, model.getAttributes().size());
		assertEquals(evaluatorA, model.getEvaluatorFor("A"));
		assertEquals(evaluatorB, model.getEvaluatorFor("B"));
		assertEquals(evaluatorC, model.getEvaluatorFor("C"));
		assertEquals(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluator(evaluatorC);
		assertEquals(3, model.getAttributes().size());
		assertEquals(evaluatorA, model.getEvaluatorFor("A"));
		assertEquals(evaluatorB, model.getEvaluatorFor("B"));
		assertEquals(null, model.getEvaluatorFor("C"));
		assertEquals(evaluatorB, model.getEvaluatorFor("B2"));

		model.removeEvaluator(evaluatorB);
		assertEquals(1, model.getAttributes().size());
		assertEquals(evaluatorA, model.getEvaluatorFor("A"));
		assertEquals(null, model.getEvaluatorFor("B"));
		assertEquals(null, model.getEvaluatorFor("C"));
		assertEquals(null, model.getEvaluatorFor("B2"));

		model.removeEvaluator(evaluatorA);
		assertEquals(0, model.getAttributes().size());
		assertEquals(null, model.getEvaluatorFor("A"));
		assertEquals(null, model.getEvaluatorFor("B"));
		assertEquals(null, model.getEvaluatorFor("C"));
		assertEquals(null, model.getEvaluatorFor("B2"));
	}

	@Test
	public void testInstanceWithEvaluators() {
		Evaluator<Object, Object> evaluatorA = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorB = generateFakeEvaluator();
		Evaluator<Object, Object> evaluatorC = generateFakeEvaluator();
		assertNotSame(evaluatorA, evaluatorB);
		assertNotSame(evaluatorA, evaluatorC);
		assertNotSame(evaluatorB, evaluatorC);

		@SuppressWarnings("unchecked")
		Collection<Evaluator<Object, Object>> evaluators = Arrays.asList(
				evaluatorA, evaluatorB, evaluatorC);
		DynamicModel model = new DynamicModel(null, evaluators);
		assertEquals(3, model.getAttributes().size());
		assertEquals(evaluatorA,
				model.getEvaluatorFor(evaluatorA.getAttribute()));
		assertEquals(evaluatorB,
				model.getEvaluatorFor(evaluatorB.getAttribute()));
		assertEquals(evaluatorC,
				model.getEvaluatorFor(evaluatorC.getAttribute()));
	}

	private final Random rand = new Random();
	private final Collection<Object> generatedAttributes = new HashSet<Object>();

	private Evaluator<Object, Object> generateFakeEvaluator() {
		Object attribute;
		do {
			attribute = rand.nextDouble();
		} while (generatedAttributes.contains(attribute));
		final Object finalAttribute = attribute;
		generatedAttributes.add(finalAttribute);
		return new Evaluator<Object, Object>() {

			@Override
			public Object getAttribute() {
				return finalAttribute;
			}

			@Override
			public Object evaluates(Object object)
					throws CannotEvaluateException {
				return rand.nextBoolean();
			}
		};
	}
}
