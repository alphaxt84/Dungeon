package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.nextSecondSkill() (Issue #129). */
public class NextSecondSkillTest extends SkillComponentTestBase {

  @Test
  public void testNextSecondSkillCyclesToNext() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // Initial: main=skillA(0), second=skillB(1)
    sc.nextSecondSkill();
    // nextSecondSkill from 1: goes to 2 (skip 0 which is main)
    assertEquals(skillC, sc.activeSecondSkill().get(), "Second should skip main and go to skillC");
  }

  @Test
  public void testNextSecondSkillWrapsAround() {
    sc = new SkillComponent(skillA, skillB, skillC);
    sc.nextSecondSkill(); // 1 -> 2 (skip 0)
    assertEquals(skillC, sc.activeSecondSkill().get());
    sc.nextSecondSkill(); // 2 -> 1 (skip 0, wrap)
    assertEquals(skillB, sc.activeSecondSkill().get(), "Should wrap back to skillB");
  }

  @Test
  public void testNextSecondSkillWithTwoSkills() {
    sc = new SkillComponent(skillA, skillB);
    sc.nextSecondSkill();
    // With 2 skills, second at 1, cycles 1->0(skip)->1, no change
    assertEquals(skillB, sc.activeSecondSkill().get(), "With 2 skills, second stays the same");
  }

  @Test
  public void testNextSecondSkillWithOneSkillDoesNothing() {
    sc = new SkillComponent(skillA);
    sc.nextSecondSkill();
    assertTrue(sc.activeSecondSkill().isEmpty(), "No second skill with only one skill");
  }

  @Test
  public void testNextSecondSkillWithNoSkillsDoesNothing() {
    sc = new SkillComponent();
    sc.nextSecondSkill();
    assertTrue(sc.activeSecondSkill().isEmpty());
  }
}
