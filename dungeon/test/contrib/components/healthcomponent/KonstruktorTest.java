package contrib.components.healthcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.components.HealthComponent;
import core.Entity;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #105: HealthComponent constructors. */
public class KonstruktorTest {

  @Test
  void testDefaultConstructor() {
    HealthComponent hc = new HealthComponent();
    assertEquals(1, hc.maximalHealthpoints());
    assertEquals(1, hc.currentHealthpoints());
  }

  @Test
  void testSingleParameterConstructor() {
    HealthComponent hc = new HealthComponent(15);
    assertEquals(15, hc.maximalHealthpoints());
    assertEquals(15, hc.currentHealthpoints());
  }

  @Test
  void testTwoParameterConstructor() {
    Consumer<Entity> mockOnDeath = mock(Consumer.class);
    HealthComponent hc = new HealthComponent(20, mockOnDeath);
    assertEquals(20, hc.maximalHealthpoints());
    assertEquals(20, hc.currentHealthpoints());

    Entity entity = new Entity();
    hc.triggerOnDeath(entity);
    verify(mockOnDeath, times(1)).accept(entity);
  }

  @Test
  void testNegativeHealthPoints() {
    HealthComponent hc = new HealthComponent(-5);
    assertEquals(-5, hc.maximalHealthpoints());
    assertEquals(-5, hc.currentHealthpoints());
  }
}
