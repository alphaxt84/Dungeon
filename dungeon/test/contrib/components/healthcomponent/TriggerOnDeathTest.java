package contrib.components.healthcomponent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import core.Entity;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #107: HealthComponent.triggerOnDeath(). */
public class TriggerOnDeathTest extends HealthComponentTestBase {

  @Test
  public void testTriggerOnDeathInvokesConsumer() {
    Consumer<Entity> mockOnDeath = mock(Consumer.class);
    hc.onDeath(mockOnDeath);

    hc.triggerOnDeath(entity);
    verify(mockOnDeath, times(1)).accept(entity);
  }
}
