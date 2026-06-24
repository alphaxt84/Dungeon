package contrib.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests for Issue #8: Item constructors. */
public class ItemKonstruktorTest extends ItemTestBase {

  @Test
  void testConstructorThreeParameter() {
    Item it = new Item("Name", "Desc", defaultAnimation);
    assertEquals("Name", it.displayName());
    assertEquals("Desc", it.description());
    assertEquals(defaultAnimation, it.inventoryAnimation());
    assertEquals(defaultAnimation, it.worldAnimation());
    assertEquals(1, it.stackSize());
    assertEquals(1, it.maxStackSize());
  }

  @Test
  void testConstructorFourParameter() {
    Item it = new Item("Name", "Desc", defaultAnimation, defaultAnimation);
    assertEquals("Name", it.displayName());
    assertEquals(defaultAnimation, it.inventoryAnimation());
    assertEquals(defaultAnimation, it.worldAnimation());
  }

  @Test
  void testConstructorSixParameter() {
    Item it = new Item("Name", "Desc", defaultAnimation, defaultAnimation, 5, 10);
    assertEquals("5 x Name", it.displayName()); // prefix is added when stack size > 1
    assertEquals(5, it.stackSize());
    assertEquals(10, it.maxStackSize());
  }

  @Test
  void testConstructorThrowsOnTooLargeStack() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new Item("Name", "Desc", defaultAnimation, defaultAnimation, 65, 10));
    assertThrows(
        IllegalArgumentException.class,
        () -> new Item("Name", "Desc", defaultAnimation, defaultAnimation, 5, 65));
  }
}
