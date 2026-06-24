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

/** Tests for Issue #84: InventoryComponent.remove(int). */
public class RemoveAtIndexTest extends InventoryComponentTestBase {

  @Test
  public void testRemoveAtIndexSuccess() {
    inventory.set(2, item1);

    Optional<Item> removed = inventory.remove(2);
    assertTrue(removed.isPresent());
    assertEquals(item1, removed.get());
    assertFalse(inventory.get(2).isPresent());
  }

  @Test
  public void testRemoveAtIndexOutOfBounds() {
    Optional<Item> removed = inventory.remove(10); // inventory size is 5
    assertFalse(removed.isPresent());

    Optional<Item> removedNegative = inventory.remove(-1);
    assertFalse(removedNegative.isPresent());
  }

  @Test
  public void testRemoveAtIndexEmpty() {
    Optional<Item> removed = inventory.remove(1);
    assertFalse(removed.isPresent());
  }

  @Test
  public void testRemoveAtIndexCallback() {
    Consumer<Item> mockCallback = mock(Consumer.class);
    inventory.onItemRemoved(mockCallback);

    inventory.set(1, item1);
    inventory.remove(1);

    verify(mockCallback, times(1)).accept(item1);
  }
}
