package contrib.systems.healthsystem;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.systems.HealthSystem;
import core.components.PositionComponent;
import core.utils.Direction;
import core.utils.Point;
import org.junit.jupiter.api.Test;

/** Tests for Issue #71: HealthSystem.activateDeathAnimation(). */
public class ActivateDeathAnimationTest extends HealthSystemTestBase {

  @Test
  void testActivateDeathAnimationWithoutPositionComponent() {
    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    system.activateDeathAnimation(hsd);
    verify(dc, times(1)).sendSignal(HealthSystem.DEATH_SIGNAL);
  }

  @Test
  void testActivateDeathAnimationWithPositionComponent() {
    PositionComponent pc = new PositionComponent(new Point(0, 0), Direction.RIGHT);
    entity.add(pc);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    system.activateDeathAnimation(hsd);
    verify(dc, times(1)).sendSignal(HealthSystem.DEATH_SIGNAL, Direction.RIGHT);
  }
}
