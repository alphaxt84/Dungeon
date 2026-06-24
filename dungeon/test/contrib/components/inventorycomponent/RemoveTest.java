package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.item.Item;
import java.util.Optional;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #83: InventoryComponent.remove(Item). */
public class RemoveTest extends InventoryComponentTestBase {

  @Test
  void testRemoveItemSuccess() {
    inventory.add(item1);
    inventory.add(item2);

    Optional<Item> removed = inventory.remove(item1);
    assertTrue(removed.isPresent());
    assertEquals(item1, removed.get());
    assertFalse(inventory.hasItem(item1));
    assertEquals(1, inventory.count());
  }

  @Test
  void testRemoveCallbackTriggered() {
    Consumer<Item> mockCallback = mock(Consumer.class);
    inventory.onItemRemoved(mockCallback);

    inventory.add(item1);
    inventory.remove(item1);

    verify(mockCallback, times(1)).accept(item1);
  }

  @Test
  void testRemoveItemNotFound() {
    inventory.add(item1);
    Optional<Item> removed = inventory.remove(item2);
    assertFalse(removed.isPresent());
  }
}
