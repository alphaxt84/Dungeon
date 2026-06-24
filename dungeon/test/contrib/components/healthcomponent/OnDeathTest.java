package contrib.components.healthcomponent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.Entity;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #108: HealthComponent.onDeath(). */
public class OnDeathTest extends HealthComponentTestBase {

  @Test
  void testOnDeathCallbackTriggered() {
    Consumer<Entity> mockOnDeath = mock(Consumer.class);
    hc.onDeath(mockOnDeath);

    hc.triggerOnDeath(entity);
    verify(mockOnDeath, times(1)).accept(entity);
  }
}
