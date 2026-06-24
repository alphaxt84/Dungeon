package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for Issue #118: HealthComponent.alreadyDead(). */
public class AlreadyDeadTest extends HealthComponentTestBase {

  @Test
  void testInitialAlreadyDeadState() {
    assertFalse(hc.alreadyDead());
  }

  @Test
  void testSetAlreadyDeadTrue() {
    hc.alreadyDead(true);
    assertTrue(hc.alreadyDead());
  }

  @Test
  void testSetAlreadyDeadFalse() {
    hc.alreadyDead(true);
    hc.alreadyDead(false);
    assertFalse(hc.alreadyDead());
  }
}
