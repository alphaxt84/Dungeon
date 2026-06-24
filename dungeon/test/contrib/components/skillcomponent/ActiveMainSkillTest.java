package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.activeMainSkill() (Issue #125). */
public class ActiveMainSkillTest extends SkillComponentTestBase {

  @Test
  void testActiveMainSkillWithNoSkills() {
    sc = new SkillComponent();
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main skill when component is empty");
  }

  @Test
  void testActiveMainSkillWithOneSkill() {
    sc = new SkillComponent(skillA);
    assertTrue(sc.activeMainSkill().isPresent());
    assertEquals(skillA, sc.activeMainSkill().get(), "Only skill should be active main");
  }

  @Test
  void testActiveMainSkillWithMultipleSkills() {
    sc = new SkillComponent(skillA, skillB, skillC);
    assertTrue(sc.activeMainSkill().isPresent());
    assertEquals(skillA, sc.activeMainSkill().get(), "First skill should be active main");
  }

  @Test
  void testActiveMainSkillAfterRemoveAll() {
    sc = new SkillComponent(skillA, skillB);
    sc.removeAll();
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main after removeAll");
  }
}
