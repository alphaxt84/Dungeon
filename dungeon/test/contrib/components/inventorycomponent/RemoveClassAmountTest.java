package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import org.junit.jupiter.api.Test;

/** Tests for Issue #85: InventoryComponent.remove(Class, int). */
public class RemoveClassAmountTest extends InventoryComponentTestBase {

  private static class CustomRemoveItem extends Item {
    public CustomRemoveItem(int size) {
      super("Custom", "Desc", null, null, size, 10);
    }
  }

  @Test
  public void testRemoveClassAndAmount() {
    CustomRemoveItem stack1 = new CustomRemoveItem(5);
    CustomRemoveItem stack2 = new CustomRemoveItem(3);

    // Add them to different slots manually to prevent automatic stacking
    inventory.set(0, stack1);
    inventory.set(1, stack2);

    assertEquals(8, inventory.count(CustomRemoveItem.class));

    inventory.remove(CustomRemoveItem.class, 6);

    // 6 units removed. Total remaining = 2 units.
    assertEquals(2, inventory.count(CustomRemoveItem.class));

    boolean slot0Present = inventory.get(0).isPresent();
    boolean slot1Present = inventory.get(1).isPresent();
    assertTrue(slot0Present ^ slot1Present); // exactly one of the slots is empty

    if (slot0Present) {
      assertEquals(2, inventory.get(0).get().stackSize());
    } else {
      assertEquals(2, inventory.get(1).get().stackSize());
    }
  }
}
