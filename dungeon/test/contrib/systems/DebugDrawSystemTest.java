package contrib.systems;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import core.Entity;
import core.Game;
import core.network.NetworkUtils;
import core.utils.FontHelper;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class DebugDrawSystemTest {

  private DebugDrawSystem system;
  private MockedStatic<FontHelper> fontHelperMock;
  private MockedStatic<NetworkUtils> networkUtilsMock;
  private MockedConstruction<SpriteBatch> spriteBatchMock;
  private MockedConstruction<ShapeRenderer> shapeRendererMock;

  @BeforeEach
  void setUp() {
    Gdx.gl = Mockito.mock(GL20.class);
    Gdx.gl20 = Gdx.gl;

    // Mock construction to avoid OpenGL errors in headless environment
    spriteBatchMock = Mockito.mockConstruction(SpriteBatch.class);
    shapeRendererMock = Mockito.mockConstruction(ShapeRenderer.class);

    // Mock FontHelper static calls
    fontHelperMock = Mockito.mockStatic(FontHelper.class);
    fontHelperMock.when(FontHelper::getDefaultFont).thenReturn(Mockito.mock(BitmapFont.class));
    fontHelperMock
        .when(() -> FontHelper.getFont(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(Mockito.mock(BitmapFont.class));

    // Mock NetworkUtils to return false for isNetworkClient to prevent initializing Game.network
    networkUtilsMock = Mockito.mockStatic(NetworkUtils.class);
    networkUtilsMock.when(NetworkUtils::isNetworkClient).thenReturn(false);

    Game.removeAllEntities();
    Game.removeAllSystems();
    system = new DebugDrawSystem();
    Game.add(system);
  }

  @AfterEach
  void tearDown() {
    if (fontHelperMock != null) fontHelperMock.close();
    if (networkUtilsMock != null) networkUtilsMock.close();
    if (spriteBatchMock != null) spriteBatchMock.close();
    if (shapeRendererMock != null) shapeRendererMock.close();

    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  // Helper methods using reflection to access private fields

  private boolean getRenderField() throws Exception {
    Field field = DebugDrawSystem.class.getDeclaredField("render");
    field.setAccessible(true);
    return field.getBoolean(system);
  }

  private boolean getRenderSystemListField() throws Exception {
    Field field = DebugDrawSystem.class.getDeclaredField("renderSystemList");
    field.setAccessible(true);
    return field.getBoolean(system);
  }

  private boolean getRenderNetworkTelemetryField() throws Exception {
    Field field = DebugDrawSystem.class.getDeclaredField("renderNetworkTelemetry");
    field.setAccessible(true);
    return field.getBoolean(system);
  }

  private Map<Entity, String> getQuickInfoCache() throws Exception {
    Field field = DebugDrawSystem.class.getDeclaredField("quickInfoCache");
    field.setAccessible(true);
    return (Map<Entity, String>) field.get(null);
  }

  @Test
  void testConstructor() {
    assertNotNull(system);
  }

  @Test
  void testToggleHUD() throws Exception {
    boolean initial = getRenderField();
    system.toggleHUD();
    assertNotEquals(initial, getRenderField());
    system.toggleHUD();
    assertEquals(initial, getRenderField());
  }

  @Test
  void testToggleSystemList() throws Exception {
    boolean initial = getRenderSystemListField();
    system.toggleSystemList();
    assertNotEquals(initial, getRenderSystemListField());
    system.toggleSystemList();
    assertEquals(initial, getRenderSystemListField());
  }

  @Test
  void testToggleNetworkTelemetry() throws Exception {
    boolean initial = getRenderNetworkTelemetryField();
    system.toggleNetworkTelemetry();
    assertNotEquals(initial, getRenderNetworkTelemetryField());
    system.toggleNetworkTelemetry();
    assertEquals(initial, getRenderNetworkTelemetryField());
  }

  @Test
  void testSetEntityQuickInfo() throws Exception {
    Entity entity = new Entity();
    String info = "Test Info";
    DebugDrawSystem.setEntityQuickInfo(entity, info);
    Map<Entity, String> cache = getQuickInfoCache();
    assertEquals(info, cache.get(entity));
    cache.remove(entity); // clean up
  }

  @Test
  void testStopAndRun() {
    // This system cannot be stopped, verify stop() sets/keeps run as true
    system.stop();
    assertTrue(system.isRunning());
    system.run();
    assertTrue(system.isRunning());
  }

  @Test
  void testRenderNoDraw() {
    // Should not crash and should return immediately when all render options are false
    assertDoesNotThrow(() -> system.render(0.1f));
  }
}
