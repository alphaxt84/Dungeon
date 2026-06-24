package contrib.systems;

import static org.junit.jupiter.api.Assertions.*;

import core.Entity;
import core.Game;
import core.components.DrawComponent;
import core.components.PlayerComponent;
import core.components.PositionComponent;
import core.level.DungeonLevel;
import core.level.Tile;
import core.level.utils.DesignLabel;
import core.level.utils.LevelElement;
import core.systems.LevelSystem;
import core.utils.Point;
import core.utils.components.draw.state.StateMachine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/** Unit tests for the {@link FogSystem} class. */
class FogSystemTest {

  private FogSystem system;
  private DungeonLevel level;
  private Entity player;
  private PositionComponent playerPosition;
  private int originalViewDistance;

  @BeforeEach
  void setUp() {
    Game.removeAllEntities();
    Game.removeAllSystems();

    Game.add(new LevelSystem());

    system = new FogSystem();
    Game.add(system);

    originalViewDistance = FogSystem.currentViewDistance();
    FogSystem.currentViewDistance(1);

    // Setup a 5x5 level layout
    // F F F F F
    // F F W F F
    // F F F F F
    // F F W F F
    // F F F F F
    LevelElement[][] elementsLayout =
        new LevelElement[][] {
          {
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR
          },
          {
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.WALL,
            LevelElement.FLOOR,
            LevelElement.FLOOR
          },
          {
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR
          },
          {
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.WALL,
            LevelElement.FLOOR,
            LevelElement.FLOOR
          },
          {
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR,
            LevelElement.FLOOR
          }
        };
    level = new DungeonLevel(elementsLayout, DesignLabel.DEFAULT);
    Game.currentLevel(level);

    // Setup a player entity at the center (2, 2)
    player = new Entity();
    playerPosition = new PositionComponent(new Point(2, 2));
    player.add(playerPosition);
    player.add(new PlayerComponent(true)); // local player
    Game.add(player);
  }

  @AfterEach
  void tearDown() {
    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
    FogSystem.currentViewDistance(originalViewDistance);
  }

  /** Verifies that the system is active by default and resets properly when toggled. */
  @Test
  void testActiveToggle() {
    assertTrue(system.active());

    // Run execute to darken tiles
    system.execute();

    Tile tile = level.layout()[0][0];
    // Verify it is darkened
    assertNotEquals(-1, tile.tintColor());

    // Set to inactive
    system.active(false);
    assertFalse(system.active());

    // Inactive system should reset/reveal all, resetting tint back to default (-1)
    assertEquals(-1, tile.tintColor());

    // Activating again
    system.active(true);
    assertTrue(system.active());
  }

  /** Verifies that setting view distance out of bounds throws IllegalArgumentException. */
  @Test
  void testViewDistanceBounds() {
    // Save current view distance to restore later
    int originalDistance = FogSystem.currentViewDistance();

    try {
      // Test negative value
      assertThrows(IllegalArgumentException.class, () -> FogSystem.currentViewDistance(-1));

      // Test value greater than maximum (25)
      assertThrows(IllegalArgumentException.class, () -> FogSystem.currentViewDistance(26));

      // Test valid boundary values
      assertDoesNotThrow(() -> FogSystem.currentViewDistance(0));
      assertEquals(0, FogSystem.currentViewDistance());

      assertDoesNotThrow(() -> FogSystem.currentViewDistance(25));
      assertEquals(25, FogSystem.currentViewDistance());
    } finally {
      // Restore original view distance
      FogSystem.currentViewDistance(originalDistance);
    }
  }

  /** Verifies that reset clears internal states and reverts tiles back to light. */
  @Test
  void testReset() {
    // Run execute to darken tiles
    system.execute();

    Tile tile = level.layout()[0][0];
    assertNotEquals(-1, tile.tintColor());

    // Calling reset should restore default tint color (-1)
    system.reset();
    assertEquals(-1, tile.tintColor());
  }

  /** Verifies that when there is no player, the execute method does not run. */
  @Test
  void testExecuteNoPlayer() {
    Game.remove(player);

    Tile tile = level.layout()[0][0];
    tile.tintColor(100);

    system.execute();

    // The tile should not have been updated/modified since there is no player
    assertEquals(100, tile.tintColor());
  }

  /** Verifies that tiles are darkened or revealed depending on distance and wall blockage. */
  @Test
  void testDarkenAndRevealTiles() {
    // Before execute, all tiles have default tint (-1)
    Tile centerTile = level.layout()[2][2];
    Tile farTile = level.layout()[0][0]; // distance from center (2, 2) is ~2.8 > 1
    Tile closeTile = level.layout()[2][3]; // distance is 1.0 <= 1

    assertEquals(-1, centerTile.tintColor());
    assertEquals(-1, farTile.tintColor());
    assertEquals(-1, closeTile.tintColor());

    system.execute();

    // Center tile and close tile should be fully visible/not darkened (tintColor remains default
    // or close to default)
    assertEquals(-1, centerTile.tintColor());
    assertEquals(-1, closeTile.tintColor());

    // Far tile should be darkened (tintColor is modified to indicate fog)
    assertNotEquals(-1, farTile.tintColor());
  }

  /** Verifies that entities in the fog are hidden and revealed correctly. */
  @Test
  void testHideAndRevealEntities() {
    // Create a test entity far away
    Entity targetEntity = new Entity();
    targetEntity.add(new PositionComponent(new Point(0, 0)));
    StateMachine mockSM = Mockito.mock(StateMachine.class);
    DrawComponent drawComponent = new DrawComponent(mockSM);
    targetEntity.add(drawComponent);
    Game.add(targetEntity);

    assertTrue(drawComponent.isVisible());

    // Execute fog system
    system.execute();

    // Entity should now be hidden because it is in the fog
    assertFalse(drawComponent.isVisible());

    // Move player close to the entity
    playerPosition.position(new Point(0, 0));
    system.execute();

    // Entity should be visible again
    assertTrue(drawComponent.isVisible());
  }

  /** Verifies that updateTile successfully transfers darkened state from one tile to another. */
  @Test
  void testUpdateTile() {
    Tile oldTile = level.layout()[0][0];
    Tile newTile = level.layout()[0][1];

    // Run execute to darken oldTile (which is far from player at 2, 2)
    system.execute();

    // Manually set oldTile as a key in darkenedTiles by executing system,
    // and check that it's darkened.
    assertNotEquals(-1, oldTile.tintColor());

    // Call updateTile to transfer status
    system.updateTile(oldTile, newTile);

    // Now we run revealAll, which reverts tiles back to light.
    // If the map updated correctly, newTile should have its tint reverted,
    // and oldTile shouldn't be affected by revealAll's reversion.
    oldTile.tintColor(999);
    newTile.tintColor(888);

    system.revealAll();

    // newTile was registered in the map, so it should be reverted to its original tint (-1)
    assertEquals(-1, newTile.tintColor());
    // oldTile was removed from the map, so its manual tint (999) remains
    assertEquals(999, oldTile.tintColor());
  }
}
