package contrib.entities.herocontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import contrib.entities.CharacterClass;
import contrib.entities.HeroController;
import core.network.messages.c2s.InputMessage;
import core.network.server.ClientState;
import core.utils.Point;
import core.utils.Tuple;
import java.lang.reflect.Field;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for HeroController.enqueueInput() (Issue #61). */
public class EnqueueInputTest extends HeroControllerTestBase {

  private ClientState clientState;

  @SuppressWarnings("unchecked")
  private Queue<Tuple<ClientState, InputMessage>> getInputQueue() throws Exception {
    Field field = HeroController.class.getDeclaredField("inputQueue");
    field.setAccessible(true);
    return (Queue<Tuple<ClientState, InputMessage>>) field.get(null);
  }

  @Override
  @BeforeEach
  void setUp() {
    super.setUp();
    clientState =
        new ClientState((short) 1, "testUser", 1, new byte[] {1, 2, 3}, CharacterClass.WIZARD);
    try {
      getInputQueue().clear();
    } catch (Exception e) {
      // Ignore - queue may not be accessible in all environments
    }
  }

  @Test
  void testEnqueueInputAddsToQueue() throws Exception {
    InputMessage msg =
        new InputMessage(
            0,
            0,
            (short) 1,
            InputMessage.Action.INTERACT,
            new InputMessage.Interact(new Point(0, 0)));

    HeroController.enqueueInput(clientState, msg);

    Queue<Tuple<ClientState, InputMessage>> queue = getInputQueue();
    assertFalse(queue.isEmpty(), "Queue should not be empty after enqueue");
    assertEquals(1, queue.size(), "Queue should have exactly one entry");
  }

  @Test
  void testEnqueueMultipleInputs() throws Exception {
    InputMessage msg1 =
        new InputMessage(
            0,
            0,
            (short) 1,
            InputMessage.Action.INTERACT,
            new InputMessage.Interact(new Point(0, 0)));
    InputMessage msg2 =
        new InputMessage(
            0,
            0,
            (short) 2,
            InputMessage.Action.INTERACT,
            new InputMessage.Interact(new Point(1, 1)));

    HeroController.enqueueInput(clientState, msg1);
    HeroController.enqueueInput(clientState, msg2);

    Queue<Tuple<ClientState, InputMessage>> queue = getInputQueue();
    assertEquals(2, queue.size(), "Queue should have two entries");
  }

  @Test
  void testEnqueueInputFIFOOrder() throws Exception {
    InputMessage msg1 =
        new InputMessage(
            0,
            0,
            (short) 1,
            InputMessage.Action.INTERACT,
            new InputMessage.Interact(new Point(0, 0)));
    InputMessage msg2 =
        new InputMessage(
            0,
            0,
            (short) 2,
            InputMessage.Action.INTERACT,
            new InputMessage.Interact(new Point(1, 1)));

    HeroController.enqueueInput(clientState, msg1);
    HeroController.enqueueInput(clientState, msg2);

    Queue<Tuple<ClientState, InputMessage>> queue = getInputQueue();
    Tuple<ClientState, InputMessage> first = queue.poll();
    assertNotNull(first);
    assertEquals(msg1, first.b(), "First message should be msg1 (FIFO)");
  }
}
