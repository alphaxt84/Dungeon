package contrib.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import contrib.modules.interaction.InteractionComponent;
import core.Entity;
import core.Game;
import core.components.DrawComponent;
import core.components.PositionComponent;
import core.utils.Point;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/** Tests for Issue #78: Item - drop(). */
public class DropTest extends ItemTestBase {

  @Test
  public void testDropOnFloorTile() {
    Point point = new Point(2, 2);
    Optional<Entity> droppedEntityOpt = item.drop(point);

    assertTrue(droppedEntityOpt.isPresent());
    Entity droppedEntity = droppedEntityOpt.get();

    assertTrue(droppedEntity.isPresent(PositionComponent.class));
    assertEquals(point, droppedEntity.fetch(PositionComponent.class).get().position());
    assertTrue(droppedEntity.isPresent(DrawComponent.class));
    assertTrue(droppedEntity.isPresent(InteractionComponent.class));

    // Verify it is registered in Game
    assertTrue(Game.levelEntities().anyMatch(e -> e == droppedEntity));
  }

  @Test
  public void testDropOnIllegalPosition() {
    Point illegalPoint = new Point(-1, -1);
    Optional<Entity> droppedEntityOpt = item.drop(illegalPoint);
    assertFalse(droppedEntityOpt.isPresent());
  }
}
