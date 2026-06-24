package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.prevSecondSkill() (Issue #130). */
public class PrevSecondSkillTest extends SkillComponentTestBase {

  @Test
  public void testPrevSecondSkillCyclesToPrevious() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // Initial: main=skillA(0), second=skillB(1)
    sc.prevSecondSkill();
    // prevSecondSkill from 1: goes to 2 (skip 0 which is main, wrap)
    assertEquals(
        skillC, sc.activeSecondSkill().get(), "Second should wrap to last skill skipping main");
  }

  @Test
  public void testPrevSecondSkillMultipleCycles() {
    sc = new SkillComponent(skillA, skillB, skillC);
    sc.prevSecondSkill(); // 1 -> 0(skip) -> 2
    assertEquals(skillC, sc.activeSecondSkill().get());
    sc.prevSecondSkill(); // 2 -> 1 (skip 0)
    assertEquals(skillB, sc.activeSecondSkill().get(), "Should cycle back to skillB");
  }

  @Test
  public void testPrevSecondSkillWithTwoSkills() {
    sc = new SkillComponent(skillA, skillB);
    sc.prevSecondSkill();
    // With 2 skills, cycles 1->0(skip)->1, no change
    assertEquals(skillB, sc.activeSecondSkill().get(), "With 2 skills, second stays the same");
  }

  @Test
  public void testPrevSecondSkillWithOneSkillDoesNothing() {
    sc = new SkillComponent(skillA);
    sc.prevSecondSkill();
    assertTrue(sc.activeSecondSkill().isEmpty());
  }

  @Test
  public void testPrevSecondSkillWithNoSkillsDoesNothing() {
    sc = new SkillComponent();
    sc.prevSecondSkill();
    assertTrue(sc.activeSecondSkill().isEmpty());
  }
}
