package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.*;

import contrib.components.InventoryComponent;
import contrib.entities.HeroController;
import contrib.item.Item;
import core.Entity;
import core.Game;
import core.level.DungeonLevel;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.systems.LevelSystem;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.useItem() (Issue #64). */
public class UseItemTest {
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
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  @Test
  public void testUseItemSuccessfully() {
    Item item = new Item("Potion", "Heals", defaultAnimation);
    inventory.add(item);
    boolean result = HeroController.useItem(hero, 0);
    assertTrue(result, "useItem should return true when item exists in slot");
  }

  @Test
  public void testUseItemEmptySlotReturnsFalse() {
    boolean result = HeroController.useItem(hero, 0);
    assertFalse(result, "useItem should return false when slot is empty");
  }

  @Test
  public void testUseItemCallsItemUse() {
    // After use(), the default Item.use() removes the item from inventory
    Item item = new Item("Potion", "Heals", defaultAnimation);
    inventory.add(item);

    assertTrue(inventory.hasItem(item), "Item should be in inventory before use");
    HeroController.useItem(hero, 0);
    assertFalse(inventory.hasItem(item), "Item should be removed from inventory after use");
  }

  @Test
  public void testUseItemNoInventoryComponentThrows() {
    hero.remove(InventoryComponent.class);
    assertThrows(
        Exception.class,
        () -> HeroController.useItem(hero, 0),
        "Should throw when entity has no InventoryComponent");
  }
}
