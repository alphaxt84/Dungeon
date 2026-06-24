package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests for Issue #112: HealthComponent.currentHealthpoints(). */
public class CurrentHealthpointsTest extends HealthComponentTestBase {

  @Test
  public void testGetCurrentHealthpoints() {
    assertEquals(10, hc.currentHealthpoints());
  }

  @Test
  public void testSetCurrentHealthpointsValid() {
    hc.currentHealthpoints(7);
    assertEquals(7, hc.currentHealthpoints());
  }

  @Test
  public void testSetCurrentHealthpointsCapsAtMax() {
    hc.currentHealthpoints(15);
    assertEquals(10, hc.currentHealthpoints()); // Setup max is 10
  }

  @Test
  public void testSetCurrentHealthpointsZeroOrNegative() {
    hc.currentHealthpoints(0);
    assertEquals(0, hc.currentHealthpoints());

    hc.currentHealthpoints(-5);
    assertEquals(-5, hc.currentHealthpoints());
  }

  @Test
  public void testSetCurrentHealthpointsWithGodMode() {
    hc.godMode(true);
    hc.currentHealthpoints(5);
    assertEquals(5, hc.currentHealthpoints());

    hc.currentHealthpoints(0);
    assertEquals(1, hc.currentHealthpoints()); // God mode keeps it at minimum 1

    hc.currentHealthpoints(-5);
    assertEquals(1, hc.currentHealthpoints());
  }
}
