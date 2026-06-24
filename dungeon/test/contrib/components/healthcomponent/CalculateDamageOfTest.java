package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import org.junit.jupiter.api.Test;

/** Tests for Issue #110: HealthComponent.calculateDamageOf(). */
public class CalculateDamageOfTest extends HealthComponentTestBase {

  @Test
  public void testCalculateDamageOfNoHits() {
    assertEquals(0, hc.calculateDamageOf(DamageType.FIRE));
    assertEquals(0, hc.calculateDamageOf(DamageType.PHYSICAL));
  }

  @Test
  public void testCalculateDamageOfSingleHit() {
    Damage dmg = new Damage(5, DamageType.FIRE, null);
    hc.receiveHit(dmg);
    assertEquals(5, hc.calculateDamageOf(DamageType.FIRE));
    assertEquals(0, hc.calculateDamageOf(DamageType.PHYSICAL));
  }

  @Test
  public void testCalculateDamageOfMultipleHitsSameType() {
    hc.receiveHit(new Damage(3, DamageType.FIRE, null));
    hc.receiveHit(new Damage(4, DamageType.FIRE, null));
    assertEquals(7, hc.calculateDamageOf(DamageType.FIRE));
  }

  @Test
  public void testCalculateDamageOfMultipleHitsDifferentTypes() {
    hc.receiveHit(new Damage(3, DamageType.FIRE, null));
    hc.receiveHit(new Damage(5, DamageType.PHYSICAL, null));
    hc.receiveHit(new Damage(2, DamageType.FIRE, null));
    assertEquals(5, hc.calculateDamageOf(DamageType.FIRE));
    assertEquals(5, hc.calculateDamageOf(DamageType.PHYSICAL));
  }
}
