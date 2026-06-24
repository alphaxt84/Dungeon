package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/** Tests for Issue #96: InventoryComponent.get(). */
public class GetItemTest extends InventoryComponentTestBase {

  @Test
  public void testGetItemFound() {
    inventory.set(1, item1);

    Optional<Item> found = inventory.get(1);
    assertTrue(found.isPresent());
    assertEquals(item1, found.get());
  }

  @Test
  public void testGetItemEmptySlot() {
    Optional<Item> found = inventory.get(1);
    assertFalse(found.isPresent());
  }

  @Test
  public void testGetItemOutOfBounds() {
    Optional<Item> foundUpper = inventory.get(10); // inventory size is 5
    assertFalse(foundUpper.isPresent());

    Optional<Item> foundLower = inventory.get(-1);
    assertFalse(foundLower.isPresent());
  }
}
