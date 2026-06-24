package contrib.entities.herocontroller;

import contrib.components.InventoryComponent;
import contrib.components.SkillComponent;
import contrib.utils.components.skill.Skill;
import core.Entity;
import core.Game;
import core.components.VelocityComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for granular HeroController tests. Provides common entity setup with necessary
 * components.
 */
public class HeroControllerTestBase {
  protected Entity hero;

  /** A simple concrete Skill subclass for testing. */
  protected static class TestSkill extends Skill {
    private boolean executed = false;

    public TestSkill(String name) {
      super(name, 0);
    }

    @Override
    protected void executeSkill(Entity caster) {
      executed = true;
    }

    public boolean wasExecuted() {
      return executed;
    }

    public void reset() {
      executed = false;
    }
  }

  @BeforeEach
  void setUp() {
    Game.removeAllEntities();
    hero = new Entity();
    hero.add(new VelocityComponent(5.0f));
    hero.add(new SkillComponent());
    hero.add(new InventoryComponent());
  }

  @AfterEach
  void tearDown() {
    Game.removeAllEntities();
  }
}
