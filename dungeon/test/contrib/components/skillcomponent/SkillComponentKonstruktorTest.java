package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent constructor (Issue #119). */
public class SkillComponentKonstruktorTest extends SkillComponentTestBase {

  @Test
  void testDefaultConstructorNoSkills() {
    sc = new SkillComponent();
    assertTrue(sc.getSkills().isEmpty(), "SkillComponent without args should have no skills");
    assertTrue(sc.activeMainSkill().isEmpty(), "No active main skill when no skills provided");
    assertTrue(sc.activeSecondSkill().isEmpty(), "No active second skill when no skills provided");
  }

  @Test
  void testConstructorWithOneSkill() {
    sc = new SkillComponent(skillA);
    assertEquals(1, sc.getSkills().size(), "Should have one skill");
    assertTrue(sc.activeMainSkill().isPresent(), "First skill should become active main skill");
    assertEquals(skillA, sc.activeMainSkill().get(), "Active main skill should be skillA");
    assertTrue(sc.activeSecondSkill().isEmpty(), "No second skill with only one skill");
  }

  @Test
  void testConstructorWithTwoSkills() {
    sc = new SkillComponent(skillA, skillB);
    assertEquals(2, sc.getSkills().size(), "Should have two skills");
    assertTrue(sc.activeMainSkill().isPresent(), "Main skill should be present");
    assertEquals(skillA, sc.activeMainSkill().get(), "Active main skill should be first (skillA)");
    assertTrue(sc.activeSecondSkill().isPresent(), "Second skill should be present");
    assertEquals(
        skillB, sc.activeSecondSkill().get(), "Active second skill should be second (skillB)");
  }

  @Test
  void testConstructorWithMultipleSkills() {
    sc = new SkillComponent(skillA, skillB, skillC);
    assertEquals(3, sc.getSkills().size(), "Should have three skills");
    assertEquals(skillA, sc.activeMainSkill().get(), "Active main should be first skill");
    assertEquals(skillB, sc.activeSecondSkill().get(), "Active second should be second skill");
  }

  @Test
  void testGetSkillsReturnsImmutableCopy() {
    sc = new SkillComponent(skillA);
    assertThrows(
        UnsupportedOperationException.class,
        () -> sc.getSkills().add(skillB),
        "getSkills() should return an unmodifiable list");
  }
}
