package contrib.components.inventorycomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.components.InventoryComponent;
import org.junit.jupiter.api.Test;

/** Tests for Issue #98: InventoryComponent constructors. */
public class InventoryComponentKonstruktorTest {

  @Test
  void testDefaultConstructor() {
    InventoryComponent inv = new InventoryComponent();
    assertEquals(24, inv.items().length);
  }

  @Test
  void testCustomSizeConstructor() {
    InventoryComponent inv = new InventoryComponent(8);
    assertEquals(8, inv.items().length);
  }
}
