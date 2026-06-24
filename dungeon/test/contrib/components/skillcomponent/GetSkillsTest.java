package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.getSkills() (Issue #131). */
public class GetSkillsTest extends SkillComponentTestBase {

  @Test
  void testGetSkillsReturnsAllSkills() {
    sc = new SkillComponent(skillA, skillB, skillC);
    List<?> skills = sc.getSkills();
    assertEquals(3, skills.size());
    assertTrue(skills.contains(skillA));
    assertTrue(skills.contains(skillB));
    assertTrue(skills.contains(skillC));
  }

  @Test
  void testGetSkillsReturnsEmptyForEmptyComponent() {
    sc = new SkillComponent();
    assertTrue(sc.getSkills().isEmpty());
  }

  @Test
  void testGetSkillsReturnsImmutableList() {
    sc = new SkillComponent(skillA);
    assertThrows(
        UnsupportedOperationException.class,
        () -> sc.getSkills().add(skillB),
        "List should be immutable (List.copyOf)");
  }

  @Test
  void testGetSkillsPreservesOrder() {
    sc = new SkillComponent(skillA, skillB, skillC);
    var skills = sc.getSkills();
    assertEquals(skillA, skills.get(0), "First should be skillA");
    assertEquals(skillB, skills.get(1), "Second should be skillB");
    assertEquals(skillC, skills.get(2), "Third should be skillC");
  }

  @Test
  void testGetSkillsReflectsChanges() {
    sc = new SkillComponent(skillA);
    assertEquals(1, sc.getSkills().size());
    sc.addSkill(skillB);
    assertEquals(2, sc.getSkills().size(), "getSkills should reflect added skills");
    sc.removeSkill(skillA);
    assertEquals(1, sc.getSkills().size(), "getSkills should reflect removed skills");
    assertTrue(sc.getSkills().contains(skillB));
  }
}
