package contrib.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Tests for Issue #77: Item - Stackgrößen verwalten. */
public class StackgroessenTest extends ItemTestBase {

  @Test
  public void testSetAndGetStackSize() {
    item.stackSize(5);
    assertEquals(5, item.stackSize());
  }

  @Test
  public void testSetStackSizeThrowsOnOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> item.stackSize(-1));
    assertThrows(IllegalArgumentException.class, () -> item.stackSize(65));
  }

  @Test
  public void testSetAndGetMaxStackSize() {
    item.maxStackSize(10);
    assertEquals(10, item.maxStackSize());
  }

  @Test
  public void testSetMaxStackSizeThrowsOnOutOfBounds() {
    assertThrows(IllegalArgumentException.class, () -> item.maxStackSize(0));
    assertThrows(IllegalArgumentException.class, () -> item.maxStackSize(65));
  }

  @Test
  public void testAmountAlias() {
    item.amount(8);
    assertEquals(8, item.amount());
    assertEquals(8, item.stackSize());
  }
}
