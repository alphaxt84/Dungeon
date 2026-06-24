package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.removeSkill(Class) (Issue #123). */
public class RemoveSkillClassTest extends SkillComponentTestBase {

  /** A subclass of TestSkill to test class-based removal. */
  protected static class FireSkill extends TestSkill {
    public FireSkill(String name) {
      super(name);
    }
  }

  /** Another subclass to test selective removal. */
  protected static class IceSkill extends TestSkill {
    public IceSkill(String name) {
      super(name);
    }
  }

  @Test
  void testRemoveByClassRemovesMatchingSkills() {
    FireSkill fire1 = new FireSkill("Fire1");
    FireSkill fire2 = new FireSkill("Fire2");
    IceSkill ice1 = new IceSkill("Ice1");
    sc = new SkillComponent(fire1, ice1, fire2);
    sc.removeSkill(FireSkill.class);
    assertEquals(1, sc.getSkills().size(), "Only IceSkill should remain");
    assertTrue(sc.getSkills().contains(ice1), "IceSkill should still be in the list");
    assertFalse(sc.getSkills().contains(fire1), "Fire1 should be removed");
    assertFalse(sc.getSkills().contains(fire2), "Fire2 should be removed");
  }

  @Test
  void testRemoveByClassUpdatesActiveIndices() {
    FireSkill fire1 = new FireSkill("Fire1");
    IceSkill ice1 = new IceSkill("Ice1");
    IceSkill ice2 = new IceSkill("Ice2");
    sc = new SkillComponent(fire1, ice1, ice2);
    sc.removeSkill(FireSkill.class);
    assertEquals(2, sc.getSkills().size());
    assertTrue(sc.activeMainSkill().isPresent(), "Should have active main after removal");
    assertTrue(sc.activeSecondSkill().isPresent(), "Should have active second after removal");
  }

  @Test
  void testRemoveByClassRemovesAll() {
    FireSkill fire1 = new FireSkill("Fire1");
    FireSkill fire2 = new FireSkill("Fire2");
    sc = new SkillComponent(fire1, fire2);
    sc.removeSkill(FireSkill.class);
    assertTrue(sc.getSkills().isEmpty(), "All skills should be removed");
    assertTrue(sc.activeMainSkill().isEmpty());
    assertTrue(sc.activeSecondSkill().isEmpty());
  }

  @Test
  void testRemoveByClassNoMatch() {
    sc = new SkillComponent(skillA, skillB);
    sc.removeSkill(FireSkill.class);
    assertEquals(2, sc.getSkills().size(), "No skills removed when none match the class");
  }
}
