package contrib.utils.components.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.components.InventoryComponent;
import contrib.item.Item;
import contrib.modules.interaction.InteractionComponent;
import core.Entity;
import core.Game;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.level.DungeonLevel;
import core.level.Tile;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.systems.LevelSystem;
import core.utils.Point;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** WTF? . */
public class ItemTest {

  Animation defaultAnimation = new Animation(new SimpleIPath("animation/missing_texture.png"));
  Animation worldAnimation = new Animation(new SimpleIPath("item/key/gold_key.png"));
  Animation inventoryAnimation = new Animation(new SimpleIPath("item/key/red_key.png"));

  /** WTF? . */
  @BeforeEach
  void before() {
    Game.add(new LevelSystem());

    DungeonLevel level =
        new DungeonLevel(
            new LevelElement[][] {
              new LevelElement[] {
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR
              },
              new LevelElement[] {
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR
              },
              new LevelElement[] {
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR
              },
              new LevelElement[] {
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR
              },
              new LevelElement[] {
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR,
                LevelElement.FLOOR
              }
            },
            DesignLabel.DEFAULT);

    for (Tile t : new ArrayList<>(level.exitTiles())) {
      level.changeTileElementType(t, LevelElement.FLOOR);
    }
    Game.currentLevel(level);
  }

  /** WTF? . */
  @AfterEach
  void cleanup() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  /** WTF? . */
  @Test
  void testConstructorThreeParameter() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    assertEquals(item.displayName(), "Test item");
    assertEquals(item.description(), "Test description");
    assertEquals(item.inventoryAnimation(), defaultAnimation);
    assertEquals(item.worldAnimation(), defaultAnimation);
    assertEquals(1, item.stackSize());
    assertEquals(1, item.maxStackSize());
  }

  /** WTF? . */
  @Test
  void testConstructorFourParameter() {
    Item item =
        new Item("Test item 2", "Another test description", inventoryAnimation, worldAnimation);
    assertEquals(item.displayName(), "Test item 2");
    assertEquals(item.description(), "Another test description");
    assertEquals(item.inventoryAnimation(), inventoryAnimation);
    assertEquals(item.worldAnimation(), worldAnimation);
    assertEquals(1, item.stackSize());
    assertEquals(1, item.maxStackSize());
  }

  /** WTF? . */
  @Test
  void testConstructorSixParameter() {
    Item item =
        new Item("Test item 3", "More description", inventoryAnimation, worldAnimation, 2, 6);
    assertEquals(item.displayName(), "2 x Test item 3");
    assertEquals(item.description(), "More description");
    assertEquals(item.inventoryAnimation(), inventoryAnimation);
    assertEquals(item.worldAnimation(), worldAnimation);
    assertEquals(2, item.stackSize());
    assertEquals(6, item.maxStackSize());
  }

  /** WTF? . */
  @Test
  void testDisplayName() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.displayName("New Display Name");

    assertEquals(item.displayName(), "New Display Name");
  }

  /** WTF? . */
  @Test
  void testDescription() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.description("New Description");

    assertEquals(item.description(), "New Description");
  }

  /** WTF? . */
  @Test
  void testInventoryAnimation() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.inventoryAnimation(inventoryAnimation);

    assertEquals(item.inventoryAnimation(), inventoryAnimation);
  }

  /** WTF? . */
  @Test
  void testWorldAnimation() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.worldAnimation(worldAnimation);

    assertEquals(item.worldAnimation(), worldAnimation);
  }

  /** WTF? . */
  @Test
  void testStackSize() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.stackSize(2);

    assertEquals(2, item.stackSize());
  }

  /** WTF? . */
  @Test
  void testMaxStackSize() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.maxStackSize(8);

    assertEquals(8, item.maxStackSize());
  }

  /** Tests if item is removed from inventory and present in Game world after drop. */
  @Test
  void testDrop() {
    Item item = new Item("Test item", "Test description", defaultAnimation);

    Point point = new Point(3, 3);
    item.drop(point);
    assertEquals(1, Game.levelEntities().count());
    Entity worldItem = Game.levelEntities().findFirst().get();
    assertTrue(worldItem.isPresent(PositionComponent.class));
    assertTrue(worldItem.fetch(PositionComponent.class).get().position().equals(point));
    assertTrue(worldItem.isPresent(DrawComponent.class));
    assertTrue(worldItem.isPresent(InteractionComponent.class));
  }

  /** Tests if item is present in inventory and removed from Game world after collect. */
  @Test
  void testCollect() {
    assertEquals(0, Game.levelEntities().count());

    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.drop(new Point(0, 0));
    assertEquals(1, Game.levelEntities().count());
    Entity collector = new Entity();
    collector.add(new InventoryComponent(3));
    Entity worldItem = Game.levelEntities().findFirst().get();

    assertTrue(item.collect(worldItem, collector));

    assertTrue(
        collector
            .fetch(InventoryComponent.class)
            .map(inventoryComponent -> inventoryComponent.hasItem(item))
            .get());

    assertEquals(0, Game.levelEntities().count());
  }

  /** Tests if item can be collected from entity with no InventoryComponent. */
  @Test
  void testCollectNoInventory() {
    assertEquals(0, Game.levelEntities().count());

    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.drop(new Point(0, 0));
    assertEquals(1, Game.levelEntities().count());
    Entity collector = new Entity();
    Entity worldItem = Game.levelEntities().findFirst().get();

    assertFalse(item.collect(worldItem, collector));
    assertEquals(1, Game.levelEntities().count());
  }

  /** Tests if item can be collected from entity with full inventory. */
  @Test
  void testCollectFullInventory() {
    assertEquals(0, Game.levelEntities().count());

    Item item = new Item("Test item", "Test description", defaultAnimation);
    item.drop(new Point(0, 0));
    assertEquals(1, Game.levelEntities().count());
    Entity collector = new Entity();
    collector.add(new InventoryComponent(0));
    Entity worldItem = Game.levelEntities().findFirst().get();

    assertFalse(item.collect(worldItem, collector));
    assertEquals(1, Game.levelEntities().count());
  }

  /** Tests if item is removed from inventory after use. */
  @Test
  void testUse() {
    Item item = new Item("Test item", "Test description", defaultAnimation);
    Entity entity = new Entity();
    InventoryComponent inventoryComponent = new InventoryComponent(2);
    entity.add(inventoryComponent);
    inventoryComponent.add(item);
    assertTrue(Arrays.asList(inventoryComponent.items()).contains(item));
    item.use(entity);
    assertFalse(Arrays.asList(inventoryComponent.items()).contains(item));
  }
}
