package contrib.components.healthcomponent;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.utils.components.health.Damage;
import contrib.utils.components.health.DamageType;
import core.Entity;
import java.util.function.BiConsumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #109: HealthComponent.onHit(). */
public class OnHitTest extends HealthComponentTestBase {

  @Test
  public void testOnHitCallbackTriggered() {
    BiConsumer<Entity, Damage> mockOnHit = mock(BiConsumer.class);
    hc.onHit(mockOnHit);

    Entity damager = new Entity();
    Damage dmg = new Damage(5, DamageType.PHYSICAL, damager);
    hc.receiveHit(dmg);

    verify(mockOnHit, times(1)).accept(damager, dmg);
  }
}
