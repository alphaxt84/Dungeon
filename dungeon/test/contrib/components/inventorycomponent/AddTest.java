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

/** Tests for Issue #82: InventoryComponent.add(). */
public class AddTest extends InventoryComponentTestBase {

  @Test
  public void testAddItemSuccess() {
    assertTrue(inventory.add(item1));
    assertEquals(1, inventory.count());
    assertEquals(item1, inventory.get(0).get());
  }

  @Test
  public void testAddCallbackTriggered() {
    Consumer<Item> mockCallback = mock(Consumer.class);
    inventory.onItemAdded(mockCallback);

    assertTrue(inventory.add(item1));
    verify(mockCallback, times(1)).accept(item1);
  }

  @Test
  public void testAddFullInventoryReturnsFalse() {
    // Inventory size is 5 in setup
    assertTrue(inventory.add(new Item("I1", "D1", defaultAnimation)));
    assertTrue(inventory.add(new Item("I2", "D2", defaultAnimation)));
    assertTrue(inventory.add(new Item("I3", "D3", defaultAnimation)));
    assertTrue(inventory.add(new Item("I4", "D4", defaultAnimation)));
    assertTrue(inventory.add(new Item("I5", "D5", defaultAnimation)));

    // 6th item should fail
    assertFalse(inventory.add(item1));
  }

  @Test
  public void testAddToExistingStack() {
    Item stack1 = new Item("Stackable", "Desc", defaultAnimation, defaultAnimation, 3, 10);
    Item stack2 = new Item("Stackable", "Desc", defaultAnimation, defaultAnimation, 5, 10);

    assertTrue(inventory.add(stack1));
    assertTrue(inventory.add(stack2));

    // Stacks should merge: stack1 stack size becomes 8, stack2 stack size becomes 0
    assertEquals(8, stack1.stackSize());
    assertEquals(0, stack2.stackSize());
    assertEquals(1, inventory.count()); // Only 1 stack slot used
  }
}
