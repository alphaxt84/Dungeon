package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests for Issue #113: HealthComponent.maximalHealthpoints(). */
public class MaximalHealthpointsTest extends HealthComponentTestBase {

  @Test
  public void testGetMaximalHealthpoints() {
    assertEquals(10, hc.maximalHealthpoints());
  }

  @Test
  public void testSetMaximalHealthpointsHigher() {
    hc.maximalHealthpoints(15);
    assertEquals(15, hc.maximalHealthpoints());
    assertEquals(10, hc.currentHealthpoints()); // Current HP remains unchanged
  }

  @Test
  public void testSetMaximalHealthpointsLower() {
    hc.maximalHealthpoints(8);
    assertEquals(8, hc.maximalHealthpoints());
    assertEquals(8, hc.currentHealthpoints()); // Current HP reduced to new max
  }
}
