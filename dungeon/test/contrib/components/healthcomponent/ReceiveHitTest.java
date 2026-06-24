package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import core.Entity;
import org.junit.jupiter.api.Test;

/** Tests for Issue #106: HealthComponent.receiveHit(). */
public class ReceiveHitTest extends HealthComponentTestBase {

  @Test
  void testReceiveHitAddsDamage() {
    Damage dmg = new Damage(5, DamageType.PHYSICAL, null);
    hc.receiveHit(dmg);
    assertEquals(5, hc.calculateDamageOf(DamageType.PHYSICAL));
  }

  @Test
  void testReceiveHitUpdatesLastCause() {
    Entity damager = new Entity();
    hc.receiveHit(new Damage(5, DamageType.PHYSICAL, damager));
    assertTrue(hc.lastDamageCause().isPresent());
    assertEquals(damager, hc.lastDamageCause().get());
  }
}
