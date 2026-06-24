package contrib.systems;

import static org.junit.jupiter.api.Assertions.assertEquals;

import contrib.components.AttachmentComponent;
import core.Entity;
import core.Game;
import core.components.PositionComponent;
import core.utils.Direction;
import core.utils.Point;
import core.utils.Vector2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for the {@link AttachmentSystem} and {@link AttachmentComponent}. */
class AttachmentSystemTest {

  private AttachmentSystem system;
  private Entity copyEntity;
  private Entity originEntity;
  private PositionComponent copyPosition;
  private PositionComponent originPosition;

  @BeforeEach
  void setUp() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    system = new AttachmentSystem();
    Game.add(system);

    copyEntity = new Entity();
    originEntity = new Entity();

    copyPosition = new PositionComponent(new Point(0, 0), Direction.DOWN);
    originPosition = new PositionComponent(new Point(5, 5), Direction.RIGHT);

    copyEntity.add(copyPosition);
    originEntity.add(originPosition);

    Game.add(copyEntity);
    Game.add(originEntity);
  }

  @AfterEach
  void tearDown() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  /**
   * Verifies that the AttachmentComponent registers the attachment in the static map and the
   * AttachmentSystem updates the copy position relative to the origin position and offset.
   */
  @Test
  void testExecuteWithOffset() {
    // Rotating with origin is false by default if offset is not Vector2.ZERO reference
    Vector2 offset = Vector2.of(1.5f, -2.5f);
    AttachmentComponent attachment = new AttachmentComponent(offset, copyPosition, originPosition);
    copyEntity.add(attachment);

    // Initial check: copy is at (0, 0)
    assertEquals(new Point(0, 0), copyPosition.position());

    // Execute system
    system.execute();

    // With scale = 1.0f: copy position = origin position (5, 5) + offset (1.5, -2.5) = (6.5, 2.5)
    assertEquals(new Point(6.5f, 2.5f), copyPosition.position());
  }

  /** Verifies that when the scale changes, the position calculation correctly scales the offset. */
  @Test
  void testExecuteWithOffsetAndScale() {
    Vector2 offset = Vector2.of(2f, 1f);
    AttachmentComponent attachment = new AttachmentComponent(offset, copyPosition, originPosition);
    attachment.setScale(3f);
    copyEntity.add(attachment);

    system.execute();

    // copy position = origin position (5, 5) + offset (2, 1) * scale (3) = (5 + 6, 5 + 3) = (11, 8)
    assertEquals(new Point(11f, 8f), copyPosition.position());
  }

  /**
   * Verifies that the attachment updates the position correctly when rotating with the origin is
   * enabled (i.e. offset = Vector2.ZERO).
   */
  @Test
  void testExecuteRotatingWithOrigin() {
    // Vector2.ZERO sets isRotatingWithOrigin = true inside the constructor
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.ZERO, copyPosition, originPosition);
    copyEntity.add(attachment);

    // Origin is at (5, 5) looking RIGHT (1, 0). Scale = 1f.
    // copy position = origin (5, 5) + RIGHT (1, 0) * scale (1) = (6, 5)
    system.execute();
    assertEquals(new Point(6f, 5f), copyPosition.position());

    // Change origin view direction to UP (0, 1)
    originPosition.viewDirection(Direction.UP);
    system.execute();
    assertEquals(new Point(5f, 6f), copyPosition.position());
  }

  /** Verifies texture rotation updates based on the origin's view direction. */
  @Test
  void testTextureRotationDirections() {
    // Pass true for isTextureRotating
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.ZERO, copyPosition, originPosition, true);
    copyEntity.add(attachment);

    // 1. Direction.RIGHT -> 0 degrees
    originPosition.viewDirection(Direction.RIGHT);
    system.execute();
    assertEquals(0f, copyPosition.rotation());

    // 2. Direction.UP -> 90 degrees
    originPosition.viewDirection(Direction.UP);
    system.execute();
    assertEquals(90f, copyPosition.rotation());

    // 3. Direction.LEFT -> 180 degrees
    originPosition.viewDirection(Direction.LEFT);
    system.execute();
    assertEquals(180f, copyPosition.rotation());

    // 4. Direction.DOWN -> 270 degrees
    originPosition.viewDirection(Direction.DOWN);
    system.execute();
    assertEquals(270f, copyPosition.rotation());
  }

  /** Verifies that texture rotation is not updated if isTextureRotating is false. */
  @Test
  void testTextureRotationDisabled() {
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.ZERO, copyPosition, originPosition, false);
    copyEntity.add(attachment);

    originPosition.viewDirection(Direction.UP);
    copyPosition.rotation(45f); // Set some initial rotation

    system.execute();
    // Rotation should remain unchanged
    assertEquals(45f, copyPosition.rotation());
  }

  /**
   * Verifies that the static unregisterAttachment method removes the attachment and prevents
   * further updates.
   */
  @Test
  void testUnregisterAttachment() {
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.of(1, 1), copyPosition, originPosition);
    copyEntity.add(attachment);

    // Unregister
    AttachmentSystem.unregisterAttachment(copyPosition);

    system.execute();
    // Position should still be (0, 0), no updates occurred
    assertEquals(new Point(0, 0), copyPosition.position());
  }

  /** Verifies that when the copy entity is removed, its attachment registration is cleaned up. */
  @Test
  void testOnEntityRemoveCopyCleared() {
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.of(1, 1), copyPosition, originPosition);
    copyEntity.add(attachment);

    // Remove the copy entity (triggering onEntityRemove listener)
    Game.remove(copyEntity);

    // Simulate Game removal logic by invoking the triggerOnRemove manually
    // because Game.remove is normally deferred or processed at the end of the frame
    system.triggerOnRemove(copyEntity);

    // Re-add copyEntity to check if updates still happen
    Game.add(copyEntity);
    system.execute();

    assertEquals(new Point(0, 0), copyPosition.position());
  }

  /** Verifies that when the origin entity is removed, its attachment registration is cleaned up. */
  @Test
  void testOnEntityRemoveOriginCleared() {
    AttachmentComponent attachment =
        new AttachmentComponent(Vector2.of(1, 1), copyPosition, originPosition);
    copyEntity.add(attachment);

    // Remove originEntity
    Game.remove(originEntity);
    system.triggerOnRemove(originEntity);

    system.execute();

    assertEquals(new Point(0, 0), copyPosition.position());
  }
}
