package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Tests for Issue #97: InventoryComponent.findNextAvailableSlot(). */
public class FindNextAvailableSlotTest extends InventoryComponentTestBase {

  @Test
  public void testFindNextAvailableSlotEmpty() {
    assertEquals(0, inventory.findNextAvailableSlot());
  }

  @Test
  public void testFindNextAvailableSlotPartial() {
    inventory.set(0, item1);
    inventory.set(1, item2);
    assertEquals(2, inventory.findNextAvailableSlot());
  }

  @Test
  public void testFindNextAvailableSlotFull() {
    inventory.set(0, item1);
    inventory.set(1, item2);
    inventory.set(2, item3);
    inventory.set(3, item1);
    inventory.set(4, item2);
    assertEquals(-1, inventory.findNextAvailableSlot());
  }
}
