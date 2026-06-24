package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/** Tests for Issue #90: InventoryComponent.smallestStackOfItemClass(). */
public class SmallestStackOfItemClassTest extends InventoryComponentTestBase {

  private static class CustomSmallestItem extends Item {
    public CustomSmallestItem(int size) {
      super("Custom", "Desc", null, null, size, 10);
    }
  }

  @Test
  void testSmallestStackFound() {
    CustomSmallestItem stack1 = new CustomSmallestItem(5);
    CustomSmallestItem stack2 = new CustomSmallestItem(2);
    CustomSmallestItem stack3 = new CustomSmallestItem(8);

    inventory.set(0, stack1);
    inventory.set(1, stack2);
    inventory.set(2, stack3);

    Optional<Item> smallest = inventory.smallestStackOfItemClass(CustomSmallestItem.class);
    assertTrue(smallest.isPresent());
    assertEquals(stack2, smallest.get());
  }

  @Test
  void testSmallestStackNotFound() {
    Optional<Item> smallest = inventory.smallestStackOfItemClass(CustomSmallestItem.class);
    assertFalse(smallest.isPresent());
  }
}
