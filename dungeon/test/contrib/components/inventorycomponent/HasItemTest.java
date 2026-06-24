package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for Issue #87: InventoryComponent.hasItem(Item). */
public class HasItemTest extends InventoryComponentTestBase {

  @Test
  public void testHasItemInstance() {
    assertFalse(inventory.hasItem(item1));

    inventory.add(item1);
    assertTrue(inventory.hasItem(item1));
    assertFalse(inventory.hasItem(item2));
  }
}
