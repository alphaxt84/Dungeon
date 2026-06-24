package contrib.systems.healthsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.systems.HealthSystem;
import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import contrib.utils.components.health.IHealthObserver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Tests for Issue #69: HealthSystem.applyDamage(). */
public class ApplyDamageTest extends HealthSystemTestBase {

  @Test
  public void testApplyDamageReducesHealthAndClearsDamage() {
    hc.receiveHit(new Damage(4, DamageType.PHYSICAL, null));
    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);

    system.execute(); // Or we can test applyDamage indirectly or directly if visible.
    // Since applyDamage is protected, we can invoke it via subclass or execute it.
    // Execute will invoke applyDamage(hsd). Let's use system.execute().
    assertEquals(6, hc.currentHealthpoints());
    assertEquals(0, hc.calculateDamageOf(DamageType.PHYSICAL));
  }

  @Test
  public void testApplyDamageSendsSignal() {
    hc.receiveHit(new Damage(4, DamageType.PHYSICAL, null));
    system.execute();
    verify(dc, times(1)).sendSignal(HealthSystem.DAMAGE_SIGNAL);
  }

  @Test
  public void testApplyDamageNotifiesObservers() {
    IHealthObserver mockObserver = Mockito.mock(IHealthObserver.class);
    system.registerObserver(mockObserver);

    hc.receiveHit(new Damage(4, DamageType.PHYSICAL, null));
    system.execute();

    verify(mockObserver, times(1))
        .onHealthEvent(Mockito.any(), Mockito.eq(IHealthObserver.HealthEvent.DAMAGE));
  }
}
