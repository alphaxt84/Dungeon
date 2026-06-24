package contrib.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests for Issue #80: Item - match(). */
public class MatchTest extends ItemTestBase {

  private static class CustomTestItem extends Item {
    public CustomTestItem(String name, String desc) {
      super(name, desc, null);
    }
  }

  @Test
  void testMatchSameClassLessOrEqualStack() {
    item.stackSize(5);

    Item input = new Item("Other", "Other desc", defaultAnimation);
    input.stackSize(3);

    assertTrue(item.match(input));
  }

  @Test
  void testMatchSameClassGreaterStack() {
    item.stackSize(3);

    Item input = new Item("Other", "Other desc", defaultAnimation);
    input.stackSize(5);

    assertFalse(item.match(input));
  }

  @Test
  void testMatchDifferentClasses() {
    item.stackSize(5);

    CustomTestItem subItem = new CustomTestItem("Sub", "Sub desc");
    subItem.stackSize(3);

    // subItem is a CustomTestItem, item is a base Item.
    // this.getClass().isInstance(input) check inside Item.match:
    // item.match(subItem) -> item.getClass().isInstance(subItem) -> Item.class.isInstance(subItem)
    // -> true (since CustomTestItem extends Item)
    assertTrue(item.match(subItem));

    // subItem.match(item) -> subItem.getClass().isInstance(item) ->
    // CustomTestItem.class.isInstance(item) -> false (base Item is not a CustomTestItem)
    assertFalse(subItem.match(item));
  }
}
