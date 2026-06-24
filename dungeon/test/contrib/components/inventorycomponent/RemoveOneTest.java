package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import org.junit.jupiter.api.Test;

/** Tests for Issue #86: InventoryComponent.removeOne(Item). */
public class RemoveOneTest extends InventoryComponentTestBase {

  @Test
  void testRemoveOneDecrementsStack() {
    Item stackItem = new Item("Stackable", "Desc", defaultAnimation, defaultAnimation, 3, 10);
    inventory.add(stackItem);

    assertTrue(inventory.removeOne(stackItem));
    assertEquals(2, stackItem.stackSize());
    assertTrue(inventory.hasItem(stackItem));
  }

  @Test
  void testRemoveOneClearsSlotAtZero() {
    item1.stackSize(1);
    inventory.add(item1);

    assertTrue(inventory.removeOne(item1));
    assertEquals(0, item1.stackSize());
    assertFalse(inventory.hasItem(item1));
    assertEquals(0, inventory.count());
  }

  @Test
  void testRemoveOneNotFoundReturnsFalse() {
    assertFalse(inventory.removeOne(item1));
  }
}
