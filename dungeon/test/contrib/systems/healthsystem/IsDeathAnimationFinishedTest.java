package contrib.systems.healthsystem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import contrib.systems.HealthSystem;
import core.utils.components.draw.state.StateMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Tests for Issue #72: HealthSystem.isDeathAnimationFinished(). */
public class IsDeathAnimationFinishedTest extends HealthSystemTestBase {

  private StateMachine mockStateMachine;

  @BeforeEach
  void localSetUp() {
    mockStateMachine = mock(StateMachine.class);
    when(dc.stateMachine()).thenReturn(mockStateMachine);
  }

  @Test
  void testNoDeathAnimation() {
    when(dc.hasState(HealthSystem.DEATH_STATE)).thenReturn(false);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertTrue(system.isDeathAnimationFinished(hsd));
  }

  @Test
  void testDeathAnimationNotActive() {
    when(dc.hasState(HealthSystem.DEATH_STATE)).thenReturn(true);
    when(mockStateMachine.getCurrentStateName()).thenReturn("idle");

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertFalse(system.isDeathAnimationFinished(hsd));
  }

  @Test
  void testDeathAnimationActiveAndLooping() {
    when(dc.hasState(HealthSystem.DEATH_STATE)).thenReturn(true);
    when(mockStateMachine.getCurrentStateName()).thenReturn(HealthSystem.DEATH_STATE);
    when(dc.isCurrentAnimationLooping()).thenReturn(true);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertTrue(system.isDeathAnimationFinished(hsd));
  }

  @Test
  void testDeathAnimationActiveNotLoopingFinished() {
    when(dc.hasState(HealthSystem.DEATH_STATE)).thenReturn(true);
    when(mockStateMachine.getCurrentStateName()).thenReturn(HealthSystem.DEATH_STATE);
    when(dc.isCurrentAnimationLooping()).thenReturn(false);
    when(dc.isCurrentAnimationFinished()).thenReturn(true);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertTrue(system.isDeathAnimationFinished(hsd));
  }

  @Test
  void testDeathAnimationActiveNotLoopingNotFinished() {
    when(dc.hasState(HealthSystem.DEATH_STATE)).thenReturn(true);
    when(mockStateMachine.getCurrentStateName()).thenReturn(HealthSystem.DEATH_STATE);
    when(dc.isCurrentAnimationLooping()).thenReturn(false);
    when(dc.isCurrentAnimationFinished()).thenReturn(false);

    HealthSystem.HSData hsd = new HealthSystem.HSData(entity, hc, dc);
    assertFalse(system.isDeathAnimationFinished(hsd));
  }
}
