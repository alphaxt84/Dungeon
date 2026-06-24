package contrib.components.skillcomponent;

import contrib.components.SkillComponent;
import contrib.utils.components.skill.Skill;
import core.Entity;
import core.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for granular SkillComponent tests. Provides common setup including a concrete Skill
 * subclass for testing purposes.
 */
public class SkillComponentTestBase {
  protected Entity entity;
  protected SkillComponent sc;

  /** A simple concrete Skill subclass for testing. */
  protected static class TestSkill extends Skill {
    private boolean executed = false;

    public TestSkill(String name) {
      super(name, 0);
    }

    public TestSkill(String name, long cooldown) {
      super(name, cooldown);
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

  protected TestSkill skillA;
  protected TestSkill skillB;
  protected TestSkill skillC;

  @BeforeEach
  public void setUp() {
    Game.removeAllEntities();
    entity = new Entity();
    skillA = new TestSkill("SkillA");
    skillB = new TestSkill("SkillB");
    skillC = new TestSkill("SkillC");
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
  }
}
