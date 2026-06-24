package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.addSkill() (Issue #120). */
public class AddSkillTest extends SkillComponentTestBase {

  @Test
  void testAddSkillToEmpty() {
    sc = new SkillComponent();
    sc.addSkill(skillA);
    assertEquals(1, sc.getSkills().size(), "Should have one skill after adding");
    assertTrue(
        sc.activeMainSkill().isPresent(),
        "First added skill should automatically become active main");
    assertEquals(skillA, sc.activeMainSkill().get());
  }

  @Test
  void testAddSecondSkillBecomesActiveSecond() {
    sc = new SkillComponent(skillA);
    sc.addSkill(skillB);
    assertEquals(2, sc.getSkills().size(), "Should have two skills");
    assertTrue(sc.activeSecondSkill().isPresent(), "Second skill should become active second");
    assertEquals(skillB, sc.activeSecondSkill().get());
  }

  @Test
  void testAddThirdSkillDoesNotChangeActive() {
    sc = new SkillComponent(skillA, skillB);
    sc.addSkill(skillC);
    assertEquals(3, sc.getSkills().size(), "Should have three skills");
    assertEquals(skillA, sc.activeMainSkill().get(), "Active main should remain skillA");
    assertEquals(skillB, sc.activeSecondSkill().get(), "Active second should remain skillB");
  }

  @Test
  void testAddNullSkillIsIgnored() {
    sc = new SkillComponent();
    sc.addSkill(null);
    assertTrue(sc.getSkills().isEmpty(), "Null skill should be ignored");
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main after adding null");
  }
}
