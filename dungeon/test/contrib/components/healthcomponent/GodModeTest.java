package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for Issue #117: HealthComponent.godMode(). */
public class GodModeTest extends HealthComponentTestBase {

  @Test
  void testInitialGodModeState() {
    assertFalse(hc.godMode());
  }

  @Test
  void testSetGodModeTrue() {
    hc.godMode(true);
    assertTrue(hc.godMode());
  }

  @Test
  void testSetGodModeFalse() {
    hc.godMode(true);
    hc.godMode(false);
    assertFalse(hc.godMode());
  }
}
