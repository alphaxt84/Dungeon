package contrib.systems.healthsystem;

import static org.mockito.Mockito.mock;

import contrib.components.HealthComponent;
import contrib.systems.HealthSystem;
import core.Entity;
import core.Game;
import core.components.DrawComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Base class for granular HealthSystem tests. */
public class HealthSystemTestBase {
  protected TestableHealthSystem system;
  protected Entity entity;
  protected HealthComponent hc;
  protected DrawComponent dc;

  /** Testable subclass of HealthSystem that exposes protected methods. */
  public static class TestableHealthSystem extends HealthSystem {
    @Override
    public int calculateDamage(HSData hsd) {
      return super.calculateDamage(hsd);
    }

    @Override
    public HSData applyDamage(HSData hsd) {
      return super.applyDamage(hsd);
    }

    @Override
    public HSData activateDeathAnimation(HSData hsd) {
      return super.activateDeathAnimation(hsd);
    }

    @Override
    public boolean isDeathAnimationFinished(HSData hsd) {
      return super.isDeathAnimationFinished(hsd);
    }

    @Override
    public void triggerOnDeath(HSData hsd) {
      super.triggerOnDeath(hsd);
    }
  }

  @BeforeEach
  public void setUp() {
    Game.removeAllEntities();
    system = new TestableHealthSystem();
    entity = new Entity();
    hc = new HealthComponent(10);
    dc = mock(DrawComponent.class);
    entity.add(hc);
    entity.add(dc);
    Game.add(entity);
    Game.add(system);
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
  }
}
