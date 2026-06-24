package contrib.components.healthcomponent;

import contrib.components.HealthComponent;
import core.Entity;
import core.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Base class for granular HealthComponent tests. */
public class HealthComponentTestBase {
  protected Entity entity;
  protected HealthComponent hc;

  @BeforeEach
  public void setUp() {
    Game.removeAllEntities();
    entity = new Entity();
    hc = new HealthComponent(10);
    entity.add(hc);
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
  }
}
