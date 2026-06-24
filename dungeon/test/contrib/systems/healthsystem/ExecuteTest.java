package contrib.systems.healthsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import org.junit.jupiter.api.Test;

/** Tests for Issue #68: HealthSystem.execute(). */
public class ExecuteTest extends HealthSystemTestBase {

  @Test
  void testExecuteAppliesDamageToAliveEntity() {
    hc.receiveHit(new Damage(3, DamageType.FIRE, null));
    assertEquals(10, hc.currentHealthpoints());

    system.execute();
    assertEquals(7, hc.currentHealthpoints());
  }

  @Test
  void testExecuteTriggersDeathForDeadEntity() {
    hc.currentHealthpoints(0);
    assertTrue(hc.isDead());

    system.execute();
    assertTrue(hc.alreadyDead());
  }
}
