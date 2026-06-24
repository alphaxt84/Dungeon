package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests for Issue #114: HealthComponent.restoreHealthpoints(). */
public class RestoreHealthpointsTest extends HealthComponentTestBase {

  @Test
  void testRestoreValidAmount() {
    hc.currentHealthpoints(5);
    hc.restoreHealthpoints(3);
    assertEquals(8, hc.currentHealthpoints());
  }

  @Test
  void testRestoreCappedAtMax() {
    hc.currentHealthpoints(8);
    hc.restoreHealthpoints(5);
    assertEquals(10, hc.currentHealthpoints()); // Max is 10 in setup
  }

  @Test
  void testRestoreZeroAmount() {
    hc.currentHealthpoints(5);
    hc.restoreHealthpoints(0);
    assertEquals(5, hc.currentHealthpoints());
  }

  @Test
  void testRestoreNegativeAmount() {
    hc.currentHealthpoints(5);
    hc.restoreHealthpoints(-3);
    assertEquals(5, hc.currentHealthpoints());
  }
}
