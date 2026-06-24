package contrib.item;

import core.Game;
import core.level.DungeonLevel;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.systems.LevelSystem;
import core.utils.components.draw.animation.Animation;
import core.utils.components.path.SimpleIPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/** Base class for granular Item tests. */
public class ItemTestBase {
  protected Animation defaultAnimation;
  protected Item item;

  @BeforeEach
  public void setUp() {
    Game.add(new LevelSystem());
    defaultAnimation = new Animation(new SimpleIPath("animation/missing_texture.png"));

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

    Game.currentLevel(level);
    item = new Item("Test Item", "Test Description", defaultAnimation);
  }

  @AfterEach
  public void tearDown() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }
}
