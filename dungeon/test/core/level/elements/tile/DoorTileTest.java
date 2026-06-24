package core.level.elements.tile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.level.utils.Coordinate;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.utils.components.path.IPath;
import core.utils.components.path.SimpleIPath;
import org.junit.jupiter.api.Test;

/** Unit tests for the {@link DoorTile} class. */
class DoorTileTest {

  private final Coordinate dummyCoordinate = new Coordinate(0, 0);
  private final IPath dummyTexturePath = new SimpleIPath("textures/door.png");

  /** Verifies that the constructor sets all fields correctly and defaults the door to open. */
  @Test
  void testConstructor() {
    DoorTile door = new DoorTile(dummyTexturePath, dummyCoordinate, DesignLabel.DEFAULT);
    assertEquals(LevelElement.DOOR, door.levelElement());
    assertTrue(door.isOpen());
    assertTrue(door.isAccessible());
    assertTrue(door.canSeeThrough());
    assertEquals(dummyTexturePath, door.texturePath());
  }

  /** Verifies that close() and open() correctly modify the door state. */
  @Test
  void testOpenClose() {
    DoorTile door = new DoorTile(dummyTexturePath, dummyCoordinate, DesignLabel.DEFAULT);

    door.close();
    assertFalse(door.isOpen());
    assertFalse(door.isAccessible());
    assertFalse(door.canSeeThrough());

    door.open();
    assertTrue(door.isOpen());
    assertTrue(door.isAccessible());
    assertTrue(door.canSeeThrough());
  }

  /** Verifies that texturePath() returns the correct textures based on the door state. */
  @Test
  void testTexturePath() {
    DoorTile door = new DoorTile(dummyTexturePath, dummyCoordinate, DesignLabel.DEFAULT);

    // Open door: should return standard texture path
    assertEquals(dummyTexturePath, door.texturePath());

    // Closed door: should append "_closed" to the filename before the extension
    door.close();
    IPath expectedClosedPath = new SimpleIPath("textures/door_closed.png");
    assertEquals(expectedClosedPath.pathString(), door.texturePath().pathString());
  }

  /** Verifies texturePath() behavior when texturePath is null. */
  @Test
  void testTexturePathNull() {
    DoorTile door = new DoorTile(null, dummyCoordinate, DesignLabel.DEFAULT);

    // Open door
    assertNull(door.texturePath());

    // Closed door
    door.close();
    assertNull(door.texturePath());
  }

  /** Verifies that toString() returns a string containing correct metadata. */
  @Test
  void testToString() {
    DoorTile door = new DoorTile(dummyTexturePath, dummyCoordinate, DesignLabel.DEFAULT);

    String openStr = door.toString();
    assertTrue(openStr.contains("DoorTile"));
    assertTrue(openStr.contains("open=true"));
    assertTrue(openStr.contains("closedTexturePath=textures/door_closed.png"));

    door.close();
    String closedStr = door.toString();
    assertTrue(closedStr.contains("open=false"));
  }
}
