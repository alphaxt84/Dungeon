package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import contrib.item.Item;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;

/** Tests for Issue #95: InventoryComponent.set(). */
public class SetItemTest extends InventoryComponentTestBase {

  @Test
  void testSetItemSuccess() {
    assertTrue(inventory.set(2, item1));
    assertEquals(item1, inventory.get(2).get());
  }

  @Test
  void testSetItemOutOfBoundsReturnsFalse() {
    assertFalse(inventory.set(10, item1)); // inventory size is 5
    assertFalse(inventory.set(-1, item1));
  }

  @Test
  void testSetItemCallback() {
    Consumer<Item> mockCallback = mock(Consumer.class);
    inventory.onItemAdded(mockCallback);

    assertTrue(inventory.set(2, item1));
    verify(mockCallback, times(1)).accept(item1);
  }
}
