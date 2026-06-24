package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.InventoryComponent;
import contrib.entities.HeroController;
import contrib.item.Item;
import core.Entity;
import core.Game;
import core.components.PositionComponent;
import core.level.DungeonLevel;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.systems.LevelSystem;
import core.utils.Point;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.dropItem() (Issue #62). */
public class DropItemTest {
  private Entity hero;
  private InventoryComponent inventory;
  private Animation defaultAnimation;

  @BeforeEach
  public void setUp() {
    Game.removeAllEntities();
    Game.add(new LevelSystem());
    defaultAnimation = new Animation(new SimpleIPath("animation/missing_texture.png"));

    DungeonLevel level =
        new DungeonLevel(
            new LevelElement[][] {
              new LevelElement[] {
                LevelElement.FLOOR, LevelElement.FLOOR, LevelElement.FLOOR,
              },
              new LevelElement[] {
                LevelElement.FLOOR, LevelElement.FLOOR, LevelElement.FLOOR,
              },
              new LevelElement[] {
                LevelElement.FLOOR, LevelElement.FLOOR, LevelElement.FLOOR,
              },
            },
            DesignLabel.DEFAULT);
    Game.currentLevel(level);

    hero = new Entity();
    inventory = new InventoryComponent();
    hero.add(inventory);
    // Position the hero on a floor tile
    hero.add(new PositionComponent(new Point(1, 1)));
    Game.add(hero);
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  @Test
  public void testDropItemFromValidSlot() {
    Item item = new Item("Sword", "A weapon", defaultAnimation);
    inventory.add(item);
    assertDoesNotThrow(
        () -> HeroController.dropItem(hero, inventory, 0),
        "Should not throw when dropping a valid item");
  }

  @Test
  public void testDropItemRemovesFromInventory() {
    Item item = new Item("Sword", "A weapon", defaultAnimation);
    inventory.add(item);
    HeroController.dropItem(hero, inventory, 0);
    assertFalse(inventory.hasItem(item), "Item should be removed from inventory after drop");
  }

  @Test
  public void testDropItemFromEmptySlotThrows() {
    assertThrows(
        IllegalArgumentException.class,
        () -> HeroController.dropItem(hero, inventory, 0),
        "Should throw when trying to drop from an empty slot");
  }
}
