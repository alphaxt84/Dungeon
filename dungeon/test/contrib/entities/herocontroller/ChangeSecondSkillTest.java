package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.components.SkillComponent;
import contrib.entities.HeroController;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.changeSecondSkill() (Issue #56). */
public class ChangeSecondSkillTest extends HeroControllerTestBase {

  @Test
  void testChangeSecondSkillNextCyclesToNextSkill() {
    TestSkill skillA = new TestSkill("A");
    TestSkill skillB = new TestSkill("B");
    TestSkill skillC = new TestSkill("C");
    SkillComponent sc = new SkillComponent(skillA, skillB, skillC);
    hero.add(sc);

    // Initial: main=skillA(0), second=skillB(1)
    assertEquals(skillB, sc.activeSecondSkill().get());

    HeroController.changeSecondSkill(hero, true);
    // Should skip main(skillA) and go to skillC
    assertEquals(skillC, sc.activeSecondSkill().get(), "Second skill should advance to skillC");
  }

  @Test
  void testChangeSecondSkillPrevCyclesToPreviousSkill() {
    TestSkill skillA = new TestSkill("A");
    TestSkill skillB = new TestSkill("B");
    TestSkill skillC = new TestSkill("C");
    SkillComponent sc = new SkillComponent(skillA, skillB, skillC);
    hero.add(sc);

    HeroController.changeSecondSkill(hero, false);
    // prevSecondSkill from 1 -> 0(skip main) -> 2
    assertEquals(skillC, sc.activeSecondSkill().get(), "Second skill should wrap to skillC (prev)");
  }

  @Test
  void testChangeSecondSkillNoSkillComponentDoesNotThrow() {
    hero.remove(SkillComponent.class);
    assertDoesNotThrow(
        () -> HeroController.changeSecondSkill(hero, true),
        "Should not throw when no SkillComponent is present");
  }

  @Test
  void testChangeSecondSkillWithTwoSkills() {
    TestSkill skillA = new TestSkill("A");
    TestSkill skillB = new TestSkill("B");
    SkillComponent sc = new SkillComponent(skillA, skillB);
    hero.add(sc);

    HeroController.changeSecondSkill(hero, true);
    // With 2 skills: cycles 1->0(skip)->1, no change
    assertEquals(
        skillB, sc.activeSecondSkill().get(), "With 2 skills, second skill stays the same");
  }
}
