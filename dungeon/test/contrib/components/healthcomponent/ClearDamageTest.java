package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import org.junit.jupiter.api.Test;

/** Tests for Issue #111: HealthComponent.clearDamage(). */
public class ClearDamageTest extends HealthComponentTestBase {

  @Test
  public void testClearDamageResetsAccumulatedDamage() {
    hc.receiveHit(new Damage(5, DamageType.FIRE, null));
    hc.receiveHit(new Damage(10, DamageType.PHYSICAL, null));
    assertEquals(5, hc.calculateDamageOf(DamageType.FIRE));
    assertEquals(10, hc.calculateDamageOf(DamageType.PHYSICAL));

    hc.clearDamage();
    assertEquals(0, hc.calculateDamageOf(DamageType.FIRE));
    assertEquals(0, hc.calculateDamageOf(DamageType.PHYSICAL));
  }
}
