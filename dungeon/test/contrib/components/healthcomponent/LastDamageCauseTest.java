package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import core.Entity;
import org.junit.jupiter.api.Test;

/** Tests for Issue #115: HealthComponent.lastDamageCause(). */
public class LastDamageCauseTest extends HealthComponentTestBase {

  @Test
  public void testLastDamageCauseInitial() {
    assertFalse(hc.lastDamageCause().isPresent());
  }

  @Test
  public void testLastDamageCauseAfterHit() {
    Entity damager = new Entity();
    Damage dmg = new Damage(5, DamageType.PHYSICAL, damager);
    hc.receiveHit(dmg);
    assertTrue(hc.lastDamageCause().isPresent());
    assertEquals(damager, hc.lastDamageCause().get());
  }

  @Test
  public void testLastDamageCauseNullDamagerDoesNotOverwrite() {
    Entity damager = new Entity();
    hc.receiveHit(new Damage(5, DamageType.PHYSICAL, damager));
    hc.receiveHit(new Damage(2, DamageType.FIRE, null));
    assertTrue(hc.lastDamageCause().isPresent());
    assertEquals(damager, hc.lastDamageCause().get()); // Still the old damager
  }

  @Test
  public void testLastDamageCauseOverwrittenByNewDamager() {
    Entity damager1 = new Entity();
    Entity damager2 = new Entity();
    hc.receiveHit(new Damage(5, DamageType.PHYSICAL, damager1));
    hc.receiveHit(new Damage(3, DamageType.FIRE, damager2));
    assertTrue(hc.lastDamageCause().isPresent());
    assertEquals(damager2, hc.lastDamageCause().get());
  }
}
