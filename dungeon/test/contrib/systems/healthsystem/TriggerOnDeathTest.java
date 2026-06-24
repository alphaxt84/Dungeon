package contrib.systems.healthsystem;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.systems.HealthSystem;
import contrib.utils.components.health.IHealthObserver;
import core.Entity;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Tests for Issue #74: HealthSystem.triggerOnDeath(). */
public class TriggerOnDeathTest extends HealthSystemTestBase {

  @Test
  public void testTriggerOnDeathSetsAlreadyDeadAndNotifies() {
    Consumer<Entity> mockOnDeath = mock(Consumer.class);
    hc.onDeath(mockOnDeath);

    IHealthObserver mockObserver = mock(IHealthObserver.class);
    system.registerObserver(mockObserver);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    system.triggerOnDeath(hsd);

    assertTrue(hc.alreadyDead());
    verify(mockObserver, times(1))
        .onHealthEvent(Mockito.any(), Mockito.eq(IHealthObserver.HealthEvent.DEATH));
    verify(mockOnDeath, times(1)).accept(entity);
  }
}
