package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.item.Item;
import org.junit.jupiter.api.Test;

/** Tests for Issue #93: InventoryComponent.count(Class). */
public class CountClassTest extends InventoryComponentTestBase {

  private static class CustomCountItem extends Item {
    public CustomCountItem(int size) {
      super("Custom", "Desc", null, null, size, 10);
    }
  }

  @Test
  public void testCountClassSum() {
    CustomCountItem stack1 = new CustomCountItem(3);
    CustomCountItem stack2 = new CustomCountItem(4);

    inventory.set(0, stack1);
    inventory.set(1, stack2);
    inventory.set(2, item1); // item1 is base Item

    assertEquals(7, inventory.count(CustomCountItem.class));
    assertEquals(8, inventory.count(Item.class)); // 3 + 4 + 1 = 8 because of isInstance check
  }
}
