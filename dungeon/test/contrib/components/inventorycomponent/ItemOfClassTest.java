package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/** Tests for Issue #89: InventoryComponent.itemOfClass(). */
public class ItemOfClassTest extends InventoryComponentTestBase {

  private static class CustomOfClassItem extends Item {
    public CustomOfClassItem() {
      super("Custom", "Desc", null);
    }
  }

  @Test
  void testItemOfClassFound() {
    CustomOfClassItem custom = new CustomOfClassItem();
    inventory.add(custom);

    Optional<Item> found = inventory.itemOfClass(CustomOfClassItem.class);
    assertTrue(found.isPresent());
    assertEquals(custom, found.get());
  }

  @Test
  void testItemOfClassNotFound() {
    Optional<Item> found = inventory.itemOfClass(CustomOfClassItem.class);
    assertFalse(found.isPresent());
  }
}
