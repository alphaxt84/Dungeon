package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.InventoryComponent;
import org.junit.jupiter.api.Test;

/** Tests for Issue #91: InventoryComponent.transfer(). */
public class TransferTest extends InventoryComponentTestBase {

  @Test
  public void testTransferSuccess() {
    inventory.add(item1);
    InventoryComponent other = new InventoryComponent(3);

    assertTrue(inventory.transfer(item1, other));
    assertFalse(inventory.hasItem(item1));
    assertTrue(other.hasItem(item1));
  }

  @Test
  public void testTransferSelfReturnsFalse() {
    inventory.add(item1);
    assertFalse(inventory.transfer(item1, inventory));
  }

  @Test
  public void testTransferItemNotFoundReturnsFalse() {
    InventoryComponent other = new InventoryComponent(3);
    assertFalse(inventory.transfer(item1, other));
  }

  @Test
  public void testTransferToFullInventoryReturnsFalse() {
    inventory.add(item1);
    InventoryComponent other = new InventoryComponent(0); // Size 0 is full

    assertFalse(inventory.transfer(item1, other));
    assertTrue(inventory.hasItem(item1));
  }
}
