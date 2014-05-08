package org.conceptmanager.abstraction.impl;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.conceptmanager.evaluation.impl.ManualEvaluator;
import org.conceptmanager.modeling.impl.DynamicModel;
import org.junit.Test;

public class FilteringAbstractorTest {

	@Test
	public void testCorrectAttributes() {
		ManualEvaluator<String, Object> evaluatorA = new ManualEvaluator<String, Object>(
				"A");
		ManualEvaluator<String, Object> evaluatorB = new ManualEvaluator<String, Object>(
				"B");
		ManualEvaluator<String, Object> evaluatorC = new ManualEvaluator<String, Object>(
				"C");
		FilteringAbstractor abstractor = new FilteringAbstractor(Arrays.asList(
				"A", "B"));

		{
			DynamicModel model = new DynamicModel(null);
			DynamicModel abstraction = abstractor.abstracts(model);
			assertEquals(2, abstraction.getAttributes().size());
			assertTrue(abstraction.getAttributes().contains("A"));
			assertTrue(abstraction.getAttributes().contains("B"));
		}

		{
			DynamicModel model = new DynamicModel(null);
			model.addEvaluator(evaluatorA);
			DynamicModel abstraction = abstractor.abstracts(model);
			assertEquals(2, abstraction.getAttributes().size());
			assertTrue(abstraction.getAttributes().contains("A"));
			assertTrue(abstraction.getAttributes().contains("B"));
		}

		{
			DynamicModel model = new DynamicModel(null);
			model.addEvaluator(evaluatorA);
			model.addEvaluator(evaluatorB);
			DynamicModel abstraction = abstractor.abstracts(model);
			assertEquals(2, abstraction.getAttributes().size());
			assertTrue(abstraction.getAttributes().contains("A"));
			assertTrue(abstraction.getAttributes().contains("B"));
		}

		{
			DynamicModel model = new DynamicModel(null);
			model.addEvaluator(evaluatorA);
			model.addEvaluator(evaluatorB);
			model.addEvaluator(evaluatorC);
			DynamicModel abstraction = abstractor.abstracts(model);
			assertEquals(2, abstraction.getAttributes().size());
			assertTrue(abstraction.getAttributes().contains("A"));
			assertTrue(abstraction.getAttributes().contains("B"));
		}
	}

	@Test
	public void testCorrectValues() {
		ManualEvaluator<String, Object> evaluatorA = new ManualEvaluator<String, Object>(
				"A");
		ManualEvaluator<String, Object> evaluatorB = new ManualEvaluator<String, Object>(
				"B");
		ManualEvaluator<String, Object> evaluatorC = new ManualEvaluator<String, Object>(
				"C");
		FilteringAbstractor abstractor = new FilteringAbstractor(Arrays.asList(
				"A", "B"));

		DynamicModel model = new DynamicModel(null);
		DynamicModel abstraction = abstractor.abstracts(model);
		assertEquals(null, abstraction.getValueFor("A"));
		assertEquals(null, abstraction.getValueFor("B"));

		model.addEvaluator(evaluatorA);
		assertEquals(null, abstraction.getValueFor("A"));
		assertEquals(null, abstraction.getValueFor("B"));

		evaluatorA.setValue(123);
		assertEquals(123, abstraction.getValueFor("A"));
		assertEquals(null, abstraction.getValueFor("B"));

		model.addEvaluator(evaluatorB);
		assertEquals(123, abstraction.getValueFor("A"));
		assertEquals(null, abstraction.getValueFor("B"));

		evaluatorB.setValue("test");
		assertEquals(123, abstraction.getValueFor("A"));
		assertEquals("test", abstraction.getValueFor("B"));

		model.addEvaluator(evaluatorC);
		assertEquals(123, abstraction.getValueFor("A"));
		assertEquals("test", abstraction.getValueFor("B"));

		evaluatorA.setValue(321);
		assertEquals(321, abstraction.getValueFor("A"));
		assertEquals("test", abstraction.getValueFor("B"));

		evaluatorB.setValue("value");
		assertEquals(321, abstraction.getValueFor("A"));
		assertEquals("value", abstraction.getValueFor("B"));

		evaluatorC.setValue(new Object());
		assertEquals(321, abstraction.getValueFor("A"));
		assertEquals("value", abstraction.getValueFor("B"));
	}

}
