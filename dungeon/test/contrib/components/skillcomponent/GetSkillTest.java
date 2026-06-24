package contrib.components.skillcomponent;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.SkillComponent;
import org.junit.jupiter.api.Test;

/** Tests for SkillComponent.getSkill(Class) (Issue #124). */
public class GetSkillTest extends SkillComponentTestBase {

  /** A subclass of TestSkill for class-based lookup. */
  protected static class HealSkill extends TestSkill {
    public HealSkill(String name) {
      super(name);
    }
  }

  @Test
  public void testGetSkillFindsMatchingSkill() {
    HealSkill heal = new HealSkill("Heal");
    sc = new SkillComponent(skillA, heal, skillB);
    var result = sc.getSkill(HealSkill.class);
    assertTrue(result.isPresent(), "Should find HealSkill");
    assertEquals(heal, result.get(), "Found skill should be the HealSkill instance");
  }

  @Test
  public void testGetSkillReturnsFirstMatch() {
    HealSkill heal1 = new HealSkill("Heal1");
    HealSkill heal2 = new HealSkill("Heal2");
    sc = new SkillComponent(heal1, skillA, heal2);
    var result = sc.getSkill(HealSkill.class);
    assertTrue(result.isPresent());
    assertEquals(heal1, result.get(), "Should return the first matching HealSkill");
  }

  @Test
  public void testGetSkillReturnsEmptyWhenNoMatch() {
    sc = new SkillComponent(skillA, skillB);
    var result = sc.getSkill(HealSkill.class);
    assertTrue(result.isEmpty(), "Should return empty when no skill of class exists");
  }

  @Test
  public void testGetSkillReturnsEmptyOnEmptyComponent() {
    sc = new SkillComponent();
    var result = sc.getSkill(HealSkill.class);
    assertTrue(result.isEmpty(), "Should return empty on empty SkillComponent");
  }
}
