package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.InventoryComponent;
import org.junit.jupiter.api.Test;

/** Tests for Issue #92: InventoryComponent.transferAll(). */
public class TransferAllTest extends InventoryComponentTestBase {

  @Test
  public void testTransferAllSuccess() {
    inventory.add(item1);
    inventory.add(item2);

    InventoryComponent other = new InventoryComponent(5);

    assertTrue(inventory.transferAll(other));
    assertEquals(0, inventory.count());
    assertEquals(2, other.count());
  }

  @Test
  public void testTransferAllPartialFails() {
    inventory.add(item1);
    inventory.add(item2);

    InventoryComponent other = new InventoryComponent(1); // Only room for 1 item

    assertFalse(inventory.transferAll(other));
    assertEquals(1, inventory.count()); // 1 item remains in source
    assertEquals(1, other.count()); // 1 item transferred
  }
}
