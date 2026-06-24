package contrib.systems.healthsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.systems.HealthSystem;
import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import org.junit.jupiter.api.Test;

/** Tests for Issue #70: HealthSystem.calculateDamage(). */
public class CalculateDamageTest extends HealthSystemTestBase {

  @Test
  public void testCalculateDamageNoHits() {
    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertEquals(0, system.calculateDamage(hsd));
  }

  @Test
  public void testCalculateDamageSingleHit() {
    hc.receiveHit(new Damage(5, DamageType.FIRE, null));
    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertEquals(5, system.calculateDamage(hsd));
  }

  @Test
  public void testCalculateDamageMultipleHits() {
    hc.receiveHit(new Damage(3, DamageType.FIRE, null));
    hc.receiveHit(new Damage(4, DamageType.PHYSICAL, null));
    hc.receiveHit(new Damage(2, DamageType.FIRE, null));
    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertEquals(9, system.calculateDamage(hsd));
  }
}
