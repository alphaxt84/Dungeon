package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.item.Item;
import java.util.Set;
import org.junit.jupiter.api.Test;

/** Tests for Issue #94: InventoryComponent.items(Class). */
public class ItemsClassTest extends InventoryComponentTestBase {

  private static class CustomItemsClassItem extends Item {
    public CustomItemsClassItem() {
      super("Custom", "Desc", null);
    }
  }

  @Test
  public void testItemsClassSet() {
    CustomItemsClassItem custom1 = new CustomItemsClassItem();
    CustomItemsClassItem custom2 = new CustomItemsClassItem();

    inventory.set(0, custom1);
    inventory.set(1, custom2);
    inventory.set(2, item1);

    Set<Item> set = inventory.items(CustomItemsClassItem.class);
    assertEquals(2, set.size());
    assertTrue(set.contains(custom1));
    assertTrue(set.contains(custom2));
  }
}
