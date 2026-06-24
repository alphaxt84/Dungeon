package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.activeSecondSkill() (Issue #126). */
public class ActiveSecondSkillTest extends SkillComponentTestBase {

  @Test
  public void testActiveSecondSkillWithNoSkills() {
    sc = new SkillComponent();
    assertTrue(sc.activeSecondSkill().isEmpty(), "No active second skill when component is empty");
  }

  @Test
  public void testActiveSecondSkillWithOneSkill() {
    sc = new SkillComponent(skillA);
    assertTrue(
        sc.activeSecondSkill().isEmpty(), "No active second skill with only one skill in list");
  }

  @Test
  public void testActiveSecondSkillWithTwoSkills() {
    sc = new SkillComponent(skillA, skillB);
    assertTrue(sc.activeSecondSkill().isPresent());
    assertEquals(skillB, sc.activeSecondSkill().get(), "Second skill should be active second");
  }

  @Test
  public void testActiveSecondSkillWithMultipleSkills() {
    sc = new SkillComponent(skillA, skillB, skillC);
    assertTrue(sc.activeSecondSkill().isPresent());
    assertEquals(skillB, sc.activeSecondSkill().get(), "Second skill should be active second");
  }

  @Test
  public void testActiveSecondSkillAfterRemoveAll() {
    sc = new SkillComponent(skillA, skillB);
    sc.removeAll();
    assertTrue(sc.activeSecondSkill().isEmpty(), "No active second after removeAll");
  }
}
