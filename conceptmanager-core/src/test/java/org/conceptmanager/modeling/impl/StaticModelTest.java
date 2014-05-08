package org.conceptmanager.modeling.impl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.conceptmanager.evaluation.Evaluator;
import org.junit.Test;

public class StaticModelTest {

	@Test
	public void testInstanceOnTheFly() {
		Map<String, Object> attributes = new HashMap<String, Object>();
		attributes.put("null", null);
		attributes.put("boolean", true);
		attributes.put("int", 123);
		attributes.put("string", "Value");
		attributes.put("object", new Object());

		StaticModel model = new StaticModel(attributes);
		assertTrue(model.getAttributes().containsAll(attributes.keySet()));
		assertTrue(attributes.keySet().containsAll(model.getAttributes()));
		for (Object attribute : model.getAttributes()) {
			assertEquals(attributes.get(attribute),
					model.getValueFor(attribute));
		}
	}

	@Test
	public void testInstanceSnapshot() {
		DynamicModel model = new DynamicModel(null);
		model.setEvaluatorFor("A", new Evaluator<String, Integer>() {

			@Override
			public String getAttribute() {
				return null;
			}

			@Override
			public Integer evaluates(Object object)
					throws CannotEvaluateException {
				return 123;
			}
		});
		model.setEvaluatorFor("B", new Evaluator<String, String>() {

			@Override
			public String getAttribute() {
				return null;
			}

			@Override
			public String evaluates(Object object)
					throws CannotEvaluateException {
				return "value";
			}
		});

		StaticModel snapshot = new StaticModel(model);
		assertTrue(snapshot.getAttributes().containsAll(model.getAttributes()));
		assertTrue(model.getAttributes().containsAll(snapshot.getAttributes()));
		for (Object attribute : snapshot.getAttributes()) {
			assertEquals(model.getValueFor(attribute),
					snapshot.getValueFor(attribute));
		}

		model.setEvaluatorFor("A", new Evaluator<String, Integer>() {

			@Override
			public String getAttribute() {
				return null;
			}

			@Override
			public Integer evaluates(Object object)
					throws CannotEvaluateException {
				return 456;
			}
		});
		model.setEvaluatorFor("C", model.getEvaluatorFor("B"));
		model.removeEvaluatorFor("B");

		assertEquals(456, model.getValueFor("A"));
		assertEquals("value", model.getValueFor("C"));
		assertEquals(123, snapshot.getValueFor("A"));
		assertEquals("value", snapshot.getValueFor("B"));
	}

}
