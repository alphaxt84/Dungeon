package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.nextMainSkill() (Issue #127). */
public class NextMainSkillTest extends SkillComponentTestBase {

  @Test
  public void testNextMainSkillCyclesToNextSkill() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // Initial: main=skillA(0), second=skillB(1)
    sc.nextMainSkill();
    // nextMainSkill skips activeSecondSkill, so should go to skillC(2)
    assertEquals(skillC, sc.activeMainSkill().get(), "Main should skip second and go to skillC");
  }

  @Test
  public void testNextMainSkillWrapsAround() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // main=0, second=1. nextMain -> 2 (skip 1)
    sc.nextMainSkill();
    assertEquals(skillC, sc.activeMainSkill().get());
    // nextMain from 2 -> 0 (skip 1)
    sc.nextMainSkill();
    assertEquals(skillA, sc.activeMainSkill().get(), "Should wrap around to skillA");
  }

  @Test
  public void testNextMainSkillWithTwoSkillsSwaps() {
    sc = new SkillComponent(skillA, skillB);
    // main=0, second=1. nextMain should skip 1, wrap to 0 again (stuck)
    sc.nextMainSkill();
    // With only 2 skills where second is at 1, nextMain cycles 0->1(skip)->0, no change
    assertEquals(skillA, sc.activeMainSkill().get(), "With 2 skills, main cannot change to second");
  }

  @Test
  public void testNextMainSkillWithOneSkillDoesNothing() {
    sc = new SkillComponent(skillA);
    sc.nextMainSkill();
    assertEquals(skillA, sc.activeMainSkill().get(), "Only one skill, no change");
  }

  @Test
  public void testNextMainSkillWithNoSkillsDoesNothing() {
    sc = new SkillComponent();
    sc.nextMainSkill();
    assertTrue(sc.activeMainSkill().isEmpty(), "No skills means no change");
  }
}
