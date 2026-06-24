package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.components.SkillComponent;
import contrib.entities.HeroController;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.changeMainSkill() (Issue #55). */
public class ChangeMainSkillTest extends HeroControllerTestBase {

  @Test
  void testChangeMainSkillNextCyclesToNextSkill() {
    TestSkill skillA = new TestSkill("A");
    TestSkill skillB = new TestSkill("B");
    TestSkill skillC = new TestSkill("C");
    SkillComponent sc = new SkillComponent(skillA, skillB, skillC);
    hero.add(sc);

    // Initial: main=skillA(0), second=skillB(1)
    assertEquals(skillA, sc.activeMainSkill().get());

    HeroController.changeMainSkill(hero, true);
    // Should skip second(skillB) and go to skillC
    assertEquals(skillC, sc.activeMainSkill().get(), "Main skill should advance to skillC");
  }

  @Test
  void testChangeMainSkillPrevCyclesToPreviousSkill() {
    TestSkill skillA = new TestSkill("A");
    TestSkill skillB = new TestSkill("B");
    TestSkill skillC = new TestSkill("C");
    SkillComponent sc = new SkillComponent(skillA, skillB, skillC);
    hero.add(sc);

    HeroController.changeMainSkill(hero, false);
    // prevMainSkill from 0 -> 2 (skip 1 which is second)
    assertEquals(skillC, sc.activeMainSkill().get(), "Main skill should go to skillC (wrap)");
  }

  @Test
  void testChangeMainSkillNoSkillComponentDoesNotThrow() {
    hero.remove(SkillComponent.class);
    assertDoesNotThrow(
        () -> HeroController.changeMainSkill(hero, true),
        "Should not throw when no SkillComponent is present");
  }

  @Test
  void testChangeMainSkillWithSingleSkill() {
    TestSkill onlySkill = new TestSkill("Only");
    SkillComponent sc = new SkillComponent(onlySkill);
    hero.add(sc);

    HeroController.changeMainSkill(hero, true);
    assertEquals(onlySkill, sc.activeMainSkill().get(), "Single skill should remain active");
  }
}
