package contrib.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.InventoryComponent;
import core.Entity;
import core.Game;
import core.utils.Point;
import org.junit.jupiter.api.Test;

/** Tests for Issue #79: Item - collect(). */
public class CollectTest extends ItemTestBase {

  @Test
  void testCollectSuccess() {
    Entity worldItem = item.drop(new Point(1, 1)).get();
    Entity collector = new Entity();
    InventoryComponent inventory = new InventoryComponent(3);
    collector.add(inventory);

    assertTrue(item.collect(worldItem, collector));
    assertTrue(inventory.hasItem(item));
    // Verify world item is removed from Game
    assertFalse(Game.levelEntities().anyMatch(e -> e == worldItem));
  }

  @Test
  void testCollectNoInventory() {
    Entity worldItem = item.drop(new Point(1, 1)).get();
    Entity collector = new Entity(); // No InventoryComponent

    assertFalse(item.collect(worldItem, collector));
    assertTrue(Game.levelEntities().anyMatch(e -> e == worldItem)); // Still in world
  }

  @Test
  void testCollectFullInventory() {
    Entity worldItem = item.drop(new Point(1, 1)).get();
    Entity collector = new Entity();
    InventoryComponent inventory = new InventoryComponent(0); // Size 0 is full
    collector.add(inventory);

    assertFalse(item.collect(worldItem, collector));
    assertTrue(Game.levelEntities().anyMatch(e -> e == worldItem)); // Still in world
  }
}
