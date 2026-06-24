package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for Issue #116: HealthComponent.isDead(). */
public class IsDeadTest extends HealthComponentTestBase {

  @Test
  public void testIsDeadWithPositiveHealth() {
    hc.currentHealthpoints(10);
    assertFalse(hc.isDead());
  }

  @Test
  public void testIsDeadWithZeroHealth() {
    hc.currentHealthpoints(0);
    assertTrue(hc.isDead());
  }

  @Test
  public void testIsDeadWithNegativeHealth() {
    hc.currentHealthpoints(-5);
    assertTrue(hc.isDead());
  }
}
