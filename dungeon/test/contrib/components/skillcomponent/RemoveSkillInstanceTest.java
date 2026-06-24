package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.removeSkill(Skill) (Issue #121). */
public class RemoveSkillInstanceTest extends SkillComponentTestBase {

  @Test
  void testRemoveExistingSkill() {
    sc = new SkillComponent(skillA, skillB, skillC);
    sc.removeSkill(skillB);
    assertEquals(2, sc.getSkills().size(), "Should have two skills after removal");
    assertFalse(sc.getSkills().contains(skillB), "skillB should no longer be in the list");
  }

  @Test
  void testRemoveSkillAdjustsActiveIndices() {
    sc = new SkillComponent(skillA, skillB, skillC);
    sc.removeSkill(skillA);
    assertEquals(2, sc.getSkills().size());
    // After removal with 2 remaining: activeMain = size-2 = 0, activeSecond = size-1 = 1
    assertTrue(sc.activeMainSkill().isPresent(), "Should still have active main skill");
    assertTrue(sc.activeSecondSkill().isPresent(), "Should still have active second skill");
  }

  @Test
  void testRemoveDownToOneSkill() {
    sc = new SkillComponent(skillA, skillB);
    sc.removeSkill(skillB);
    assertEquals(1, sc.getSkills().size());
    assertTrue(sc.activeMainSkill().isPresent(), "Main skill should still be active");
    assertTrue(sc.activeSecondSkill().isEmpty(), "No second skill when only one remains");
  }

  @Test
  void testRemoveLastSkill() {
    sc = new SkillComponent(skillA);
    sc.removeSkill(skillA);
    assertTrue(sc.getSkills().isEmpty(), "No skills after removing last one");
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main after removing all");
    assertTrue(sc.activeSecondSkill().isEmpty(), "No active second after removing all");
  }

  @Test
  void testRemoveNonExistingSkillDoesNothing() {
    sc = new SkillComponent(skillA, skillB);
    TestSkill unknown = new TestSkill("Unknown");
    sc.removeSkill(unknown);
    assertEquals(2, sc.getSkills().size(), "Size should be unchanged");
  }
}
