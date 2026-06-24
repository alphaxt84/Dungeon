package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import contrib.entities.HeroController;
import core.utils.Point;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.useMainSkill() (Issue #50). */
public class UseMainSkillTest extends HeroControllerTestBase {

  @Test
  public void testUseMainSkillExecutesActiveSkill() {
    TestSkill skill = new TestSkill("MainSkill");
    SkillComponent sc = new SkillComponent(skill);
    hero.add(sc);

    HeroController.useMainSkill(hero, new Point(1.0f, 1.0f));
    assertTrue(skill.wasExecuted(), "Main skill should have been executed");
  }

  @Test
  public void testUseMainSkillNoSkillComponentDoesNotThrow() {
    hero.remove(SkillComponent.class);
    assertDoesNotThrow(
        () -> HeroController.useMainSkill(hero, new Point(1.0f, 1.0f)),
        "Should not throw when no SkillComponent is present");
  }

  @Test
  public void testUseMainSkillNoActiveSkillDoesNotThrow() {
    SkillComponent sc = new SkillComponent();
    hero.add(sc);
    assertDoesNotThrow(
        () -> HeroController.useMainSkill(hero, new Point(1.0f, 1.0f)),
        "Should not throw when no active main skill");
  }

  @Test
  public void testUseMainSkillWithNullTarget() {
    TestSkill skill = new TestSkill("MainSkill");
    SkillComponent sc = new SkillComponent(skill);
    hero.add(sc);

    HeroController.useMainSkill(hero, null);
    assertTrue(skill.wasExecuted(), "Skill should execute even with null target");
  }

  @Test
  public void testUseMainSkillWithMultipleSkillsUsesMain() {
    TestSkill mainSkill = new TestSkill("Main");
    TestSkill secondSkill = new TestSkill("Second");
    SkillComponent sc = new SkillComponent(mainSkill, secondSkill);
    hero.add(sc);

    HeroController.useMainSkill(hero, new Point(0, 0));
    assertTrue(mainSkill.wasExecuted(), "Only main skill should be executed");
    assertFalse(secondSkill.wasExecuted(), "Second skill should NOT be executed");
  }
}
