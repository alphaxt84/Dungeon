package contrib.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.InventoryComponent;
import core.Entity;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/** Tests for Issue #81: Item - Allgemeine Hilfsmethoden. */
public class AllgemeineHilfsmethodenTest extends ItemTestBase {

  @Test
  void testDisplayNameAndSetter() {
    assertEquals("Test Item", item.displayName());

    item.displayName("New Name");
    assertEquals("New Name", item.displayName());

    item.stackSize(3);
    assertEquals("3 x New Name", item.displayName());
  }

  @Test
  void testDescriptionAndSetter() {
    assertEquals("Test Description", item.description());

    item.description("New Description");
    assertEquals("New Description", item.description());
  }

  @Test
  void testAnimationsSetters() {
    Animation otherAnim = new Animation(new SimpleIPath("other.png"));

    item.inventoryAnimation(otherAnim);
    assertEquals(otherAnim, item.inventoryAnimation());

    item.worldAnimation(otherAnim);
    assertEquals(otherAnim, item.worldAnimation());
  }

  @Test
  void testUseItem() {
    Entity entity = new Entity();
    InventoryComponent inventory = new InventoryComponent(2);
    entity.add(inventory);
    inventory.add(item);

    assertTrue(Arrays.asList(inventory.items()).contains(item));
    item.use(entity);
    assertFalse(Arrays.asList(inventory.items()).contains(item));
  }

  @Test
  void testEqualsAndHashCode() {
    Item item2 = new Item("Test Item", "Test Description", defaultAnimation);
    assertTrue(item.equals(item2));

    item2.displayName("Different");
    assertFalse(item.equals(item2));
  }
}
