package contrib.systems.healthsystem;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import contrib.utils.components.health.IHealthObserver;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Tests for Issue #73: HealthSystem observer registration/removal. */
public class ObserverTest extends HealthSystemTestBase {

  @Test
  void testRegisterAndNotifyObserver() {
    IHealthObserver mockObserver = mock(IHealthObserver.class);
    system.registerObserver(mockObserver);

    hc.receiveHit(new Damage(3, DamageType.PHYSICAL, null));
    system.execute();

    verify(mockObserver, times(1))
        .onHealthEvent(Mockito.any(), Mockito.eq(IHealthObserver.HealthEvent.DAMAGE));
  }

  @Test
  void testRemoveObserverDoesNotNotify() {
    IHealthObserver mockObserver = mock(IHealthObserver.class);
    system.registerObserver(mockObserver);
    system.removeObserver(mockObserver);

    hc.receiveHit(new Damage(3, DamageType.PHYSICAL, null));
    system.execute();

    verifyNoInteractions(mockObserver);
  }
}
