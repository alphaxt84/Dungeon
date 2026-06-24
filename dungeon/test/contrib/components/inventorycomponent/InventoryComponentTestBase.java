package contrib.components.inventorycomponent;

import contrib.components.InventoryComponent;
import contrib.item.Item;
import core.Game;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Base class for granular InventoryComponent tests. */
public class InventoryComponentTestBase {
  protected Animation defaultAnimation;
  protected Item item1;
  protected Item item2;
  protected Item item3;
  protected InventoryComponent inventory;

  @BeforeEach
  void setUp() {
    Game.removeAllEntities();
    defaultAnimation = new Animation(new SimpleIPath("animation/missing_texture.png"));

    item1 = new Item("Item 1", "Desc 1", defaultAnimation);
    item2 = new Item("Item 2", "Desc 2", defaultAnimation);
    item3 = new Item("Item 3", "Desc 3", defaultAnimation);

    inventory = new InventoryComponent(5);
  }

  @AfterEach
  void tearDown() {
    Game.removeAllEntities();
  }
}
