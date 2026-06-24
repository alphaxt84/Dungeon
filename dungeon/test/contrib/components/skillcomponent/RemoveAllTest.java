package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.removeAll() (Issue #122). */
public class RemoveAllTest extends SkillComponentTestBase {

  @Test
  void testRemoveAllFromPopulated() {
    sc = new SkillComponent(skillA, skillB, skillC);
    sc.removeAll();
    assertTrue(sc.getSkills().isEmpty(), "All skills should be removed");
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main skill after removeAll");
    assertTrue(sc.activeSecondSkill().isEmpty(), "No active second skill after removeAll");
  }

  @Test
  void testRemoveAllFromEmpty() {
    sc = new SkillComponent();
    sc.removeAll();
    assertTrue(sc.getSkills().isEmpty(), "Still empty after removeAll on empty component");
    assertTrue(sc.activeMainSkill().isEmpty());
    assertTrue(sc.activeSecondSkill().isEmpty());
  }

  @Test
  void testRemoveAllThenAddSkill() {
    sc = new SkillComponent(skillA, skillB);
    sc.removeAll();
    sc.addSkill(skillC);
    assertEquals(1, sc.getSkills().size(), "Should have one skill after re-adding");
    assertEquals(
        skillC, sc.activeMainSkill().get(), "Re-added skill should become active main skill");
  }
}
