package contrib.systems;

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import contrib.components.CollideComponent;
import contrib.entities.deco.Deco;
import contrib.entities.deco.DecoFactory;
import contrib.utils.components.skill.SkillTools;
import core.Entity;
import core.Game;
import core.components.PositionComponent;
import core.systems.input.InputManager;
import core.utils.ClipboardUtil;
import core.utils.FontHelper;
import core.utils.Point;
import core.utils.Rectangle;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class DecoTestSystemTest {

  private DecoTestSystem system;
  private MockedStatic<FontHelper> fontHelperMock;
  private MockedStatic<InputManager> inputManagerMock;
  private MockedStatic<SkillTools> skillToolsMock;
  private MockedStatic<ClipboardUtil> clipboardUtilMock;
  private MockedStatic<DecoFactory> decoFactoryMock;
  private MockedConstruction<SpriteBatch> spriteBatchMock;
  private MockedConstruction<ShapeRenderer> shapeRendererMock;

  @BeforeEach
  void setUp() {
    Gdx.gl = Mockito.mock(GL20.class);
    Gdx.gl20 = Gdx.gl;

    // Mock construction of SpriteBatch and ShapeRenderer to avoid GL errors
    spriteBatchMock = Mockito.mockConstruction(SpriteBatch.class);
    shapeRendererMock = Mockito.mockConstruction(ShapeRenderer.class);

    // Mock FontHelper static calls
    fontHelperMock = Mockito.mockStatic(FontHelper.class);
    fontHelperMock.when(FontHelper::getDefaultFont).thenReturn(Mockito.mock(BitmapFont.class));
    fontHelperMock
        .when(() -> FontHelper.getFont(Mockito.anyString(), Mockito.anyInt()))
        .thenReturn(Mockito.mock(BitmapFont.class));

    // Mock InputManager static calls
    inputManagerMock = Mockito.mockStatic(InputManager.class);

    // Mock SkillTools static calls
    skillToolsMock = Mockito.mockStatic(SkillTools.class);
    skillToolsMock.when(SkillTools::cursorPositionAsPoint).thenReturn(new Point(3f, 4f));

    // Mock ClipboardUtil static calls
    clipboardUtilMock = Mockito.mockStatic(ClipboardUtil.class);

    // Mock DecoFactory static calls to return a dummy entity with required components
    decoFactoryMock = Mockito.mockStatic(DecoFactory.class);
    decoFactoryMock
        .when(() -> DecoFactory.createDeco(Mockito.any(Point.class), Mockito.any(Deco.class)))
        .thenAnswer(
            invocation -> {
              Point pos = invocation.getArgument(0);
              Entity entity = new Entity();
              entity.add(new PositionComponent(pos));
              entity.add(new CollideComponent(new Rectangle(0, 0, 1.0f, 1.0f)));
              return entity;
            });

    Game.removeAllEntities();
    Game.removeAllSystems();
    system = new DecoTestSystem();
    Game.add(system);
  }

  @AfterEach
  void tearDown() {
    if (fontHelperMock != null) fontHelperMock.close();
    if (inputManagerMock != null) inputManagerMock.close();
    if (skillToolsMock != null) skillToolsMock.close();
    if (clipboardUtilMock != null) clipboardUtilMock.close();
    if (decoFactoryMock != null) decoFactoryMock.close();
    if (spriteBatchMock != null) spriteBatchMock.close();
    if (shapeRendererMock != null) shapeRendererMock.close();

    Game.removeAllEntities();
    Game.removeAllSystems();
    Game.currentLevel(null);
  }

  // Helper methods using reflection to access private fields

  private String getCurrentModeName() throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("currentMode");
    field.setAccessible(true);
    Object mode = field.get(system);
    return mode.toString();
  }

  private void setCurrentMode(String modeName) throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("currentMode");
    field.setAccessible(true);
    for (Object val : field.getType().getEnumConstants()) {
      if (val.toString().equals(modeName)) {
        field.set(system, val);
        break;
      }
    }
  }

  private Entity getTestEntity() throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("testEntity");
    field.setAccessible(true);
    return (Entity) field.get(system);
  }

  private void setTestEntity(Entity entity) throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("testEntity");
    field.setAccessible(true);
    field.set(system, entity);
  }

  private Deco getCurrentDeco() throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("currentDeco");
    field.setAccessible(true);
    return (Deco) field.get(system);
  }

  private void setCurrentDeco(Deco deco) throws Exception {
    Field field = DecoTestSystem.class.getDeclaredField("currentDeco");
    field.setAccessible(true);
    field.set(system, deco);
  }

  @Test
  void testConstructor() {
    assertNotNull(system);
  }

  @Test
  void testExecuteNoInput() throws Exception {
    system.execute();
    assertEquals("ChangeDeco", getCurrentModeName());
    assertNull(getTestEntity());
  }

  @Test
  void testExecuteChangeMode() throws Exception {
    // Mode cycles: ChangeDeco -> ModifyOffsetX -> ModifyOffsetY -> ModifySizeWidth ->
    // ModifySizeHeight -> ChangeDeco
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.UP)).thenReturn(true);

    assertEquals("ChangeDeco", getCurrentModeName());

    system.execute();
    assertEquals("ModifyOffsetX", getCurrentModeName());

    system.execute();
    assertEquals("ModifyOffsetY", getCurrentModeName());

    system.execute();
    assertEquals("ModifySizeWidth", getCurrentModeName());

    system.execute();
    assertEquals("ModifySizeHeight", getCurrentModeName());

    system.execute();
    assertEquals("ChangeDeco", getCurrentModeName());
  }

  @Test
  void testExecuteMoveDeco() throws Exception {
    inputManagerMock.when(() -> InputManager.isKeyPressed(Input.Keys.DOWN)).thenReturn(true);

    assertNull(getTestEntity());

    // Execute will create a testEntity at (3, 4) because of mocked mouse position
    system.execute();

    Entity entity = getTestEntity();
    assertNotNull(entity);
    PositionComponent pc = entity.fetch(PositionComponent.class).orElseThrow();
    assertEquals(new Point(3f, 4f), pc.position());
  }

  @Test
  void testExecuteChangeDeco() throws Exception {
    setCurrentMode("ChangeDeco");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    // Set testEntity so it doesn't try to create a new one from scratch at cursor position
    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    // Simulate pressing RIGHT (MODE_MODIFY_PLUS)
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);

    system.execute();

    Deco newDeco = getCurrentDeco();
    assertNotEquals(initialDeco, newDeco);
    assertEquals(Deco.values()[1], newDeco);
  }

  @Test
  void testExecuteModifyOffsetX() throws Exception {
    setCurrentMode("ModifyOffsetX");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    CollideComponent cc = dummy.fetch(CollideComponent.class).orElseThrow();
    float initialOffsetX = cc.collider().offset().x();

    // Press RIGHT (MODE_MODIFY_PLUS) to increase offset by 0.05
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);
    system.execute();
    assertEquals(initialOffsetX + 0.05f, cc.collider().offset().x(), 0.001f);

    // Press LEFT (MODE_MODIFY_MINUS) to decrease offset by 0.05
    inputManagerMock.reset();
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.LEFT)).thenReturn(true);
    system.execute();
    assertEquals(initialOffsetX, cc.collider().offset().x(), 0.001f);
  }

  @Test
  void testExecuteModifyOffsetY() throws Exception {
    setCurrentMode("ModifyOffsetY");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    CollideComponent cc = dummy.fetch(CollideComponent.class).orElseThrow();
    float initialOffsetY = cc.collider().offset().y();

    // Press RIGHT (MODE_MODIFY_PLUS) to increase offset by 0.05
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);
    system.execute();
    assertEquals(initialOffsetY + 0.05f, cc.collider().offset().y(), 0.001f);
  }

  @Test
  void testExecuteModifySizeWidth() throws Exception {
    setCurrentMode("ModifySizeWidth");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    CollideComponent cc = dummy.fetch(CollideComponent.class).orElseThrow();
    float initialWidth = cc.collider().width();

    // Press RIGHT (MODE_MODIFY_PLUS) to increase width by 0.05
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);
    system.execute();
    assertEquals(initialWidth + 0.05f, cc.collider().width(), 0.001f);
  }

  @Test
  void testExecuteModifySizeHeight() throws Exception {
    setCurrentMode("ModifySizeHeight");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    CollideComponent cc = dummy.fetch(CollideComponent.class).orElseThrow();
    float initialHeight = cc.collider().height();

    // Press RIGHT (MODE_MODIFY_PLUS) to increase height by 0.05
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);
    system.execute();
    assertEquals(initialHeight + 0.05f, cc.collider().height(), 0.001f);
  }

  @Test
  void testClipboardCopy() throws Exception {
    setCurrentMode("ModifyOffsetX");
    Deco initialDeco = Deco.values()[0];
    setCurrentDeco(initialDeco);

    Entity dummy = DecoFactory.createDeco(new Point(1f, 1f), initialDeco);
    setTestEntity(dummy);

    CollideComponent cc = dummy.fetch(CollideComponent.class).orElseThrow();

    // Press RIGHT (MODE_MODIFY_PLUS) which triggers copyColliderInfoToClipboard
    inputManagerMock.when(() -> InputManager.isKeyJustPressed(Input.Keys.RIGHT)).thenReturn(true);
    system.execute();

    String expectedString =
        String.format(
            "new Rectangle(%.2ff, %.2ff, %.2ff, %.2ff)",
            cc.collider().width(),
            cc.collider().height(),
            cc.collider().offset().x(),
            cc.collider().offset().y());

    // Verify ClipboardUtil.copyToClipboard was called with the expected format
    clipboardUtilMock.verify(() -> ClipboardUtil.copyToClipboard(expectedString), Mockito.times(1));
  }
}
