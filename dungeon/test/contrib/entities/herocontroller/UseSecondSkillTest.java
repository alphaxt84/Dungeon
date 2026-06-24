package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import contrib.entities.HeroController;
import core.utils.Point;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.useSecondSkill() (Issue #51). */
public class UseSecondSkillTest extends HeroControllerTestBase {

  @Test
  void testUseSecondSkillExecutesActiveSecondSkill() {
    TestSkill mainSkill = new TestSkill("Main");
    TestSkill secondSkill = new TestSkill("Second");
    SkillComponent sc = new SkillComponent(mainSkill, secondSkill);
    hero.add(sc);

    HeroController.useSecondSkill(hero, new Point(2.0f, 3.0f));
    assertTrue(secondSkill.wasExecuted(), "Second skill should have been executed");
    assertFalse(mainSkill.wasExecuted(), "Main skill should NOT be executed");
  }

  @Test
  void testUseSecondSkillNoSkillComponentDoesNotThrow() {
    hero.remove(SkillComponent.class);
    assertDoesNotThrow(
        () -> HeroController.useSecondSkill(hero, new Point(1.0f, 1.0f)),
        "Should not throw when no SkillComponent is present");
  }

  @Test
  void testUseSecondSkillNoActiveSecondDoesNothing() {
    TestSkill onlySkill = new TestSkill("Only");
    SkillComponent sc = new SkillComponent(onlySkill);
    hero.add(sc);

    HeroController.useSecondSkill(hero, new Point(1.0f, 1.0f));
    assertFalse(onlySkill.wasExecuted(), "Only skill (main) should NOT be used as second");
  }

  @Test
  void testUseSecondSkillWithNullTarget() {
    TestSkill mainSkill = new TestSkill("Main");
    TestSkill secondSkill = new TestSkill("Second");
    SkillComponent sc = new SkillComponent(mainSkill, secondSkill);
    hero.add(sc);

    HeroController.useSecondSkill(hero, null);
    assertTrue(secondSkill.wasExecuted(), "Skill should execute even with null target");
  }
}
