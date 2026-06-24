package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.prevMainSkill() (Issue #128). */
public class PrevMainSkillTest extends SkillComponentTestBase {

  @Test
  public void testPrevMainSkillCyclesToPreviousSkill() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // Initial: main=skillA(0), second=skillB(1)
    sc.prevMainSkill();
    // prevMainSkill from 0: goes to 2 (skip 1 which is second)
    assertEquals(skillC, sc.activeMainSkill().get(), "Main should go to last skill (wrap)");
  }

  @Test
  public void testPrevMainSkillMultipleCycles() {
    sc = new SkillComponent(skillA, skillB, skillC);
    // main=0, second=1
    sc.prevMainSkill(); // 0 -> 2 (skip 1)
    assertEquals(skillC, sc.activeMainSkill().get());
    sc.prevMainSkill(); // 2 -> 0 (skip 1)
    assertEquals(skillA, sc.activeMainSkill().get(), "Should cycle back to skillA");
  }

  @Test
  public void testPrevMainSkillWithTwoSkills() {
    sc = new SkillComponent(skillA, skillB);
    sc.prevMainSkill();
    // With 2 skills, prev cycles 0->1(skip)->0, no change
    assertEquals(skillA, sc.activeMainSkill().get(), "With 2 skills, main stays the same");
  }

  @Test
  public void testPrevMainSkillWithOneSkillDoesNothing() {
    sc = new SkillComponent(skillA);
    sc.prevMainSkill();
    assertEquals(skillA, sc.activeMainSkill().get());
  }

  @Test
  public void testPrevMainSkillWithNoSkillsDoesNothing() {
    sc = new SkillComponent();
    sc.prevMainSkill();
    assertTrue(sc.activeMainSkill().isEmpty());
  }
}
