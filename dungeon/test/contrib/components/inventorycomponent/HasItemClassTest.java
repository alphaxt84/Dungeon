package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import org.junit.jupiter.api.Test;

/** Tests for Issue #88: InventoryComponent.hasItem(Class). */
public class HasItemClassTest extends InventoryComponentTestBase {

  private static class CustomHasClassItem extends Item {
    public CustomHasClassItem() {
      super("Custom", "Desc", null);
    }
  }

  @Test
  void testHasItemClass() {
    assertFalse(inventory.hasItem(CustomHasClassItem.class));

    CustomHasClassItem custom = new CustomHasClassItem();
    inventory.add(custom);

    assertTrue(inventory.hasItem(CustomHasClassItem.class));
    assertTrue(inventory.hasItem(Item.class)); // Inheritance check
  }
}
