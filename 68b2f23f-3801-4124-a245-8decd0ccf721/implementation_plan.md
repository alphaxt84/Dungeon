# Implementation Plan – Granulare Test-Entwicklung (Alle Phasen)

Wir implementieren granulare, pro-Methode Testdateien für alle Issues auf dem Projektboard. Jeder Test erbt von einer gemeinsamen Base-Klasse. Fortschritt wird phasenweise verfolgt.

---

## Fortschrittsübersicht

| Phase | Komponente | Testdateien | Tests | Status |
|-------|-----------|------------|-------|--------|
| 1 | HealthComponent + HealthSystem | 19 | ~55 | ✅ Abgeschlossen |
| 2 | Item + InventoryComponent | 24 | ~70 | ✅ Abgeschlossen |
| 3 | SkillComponent + HeroController | 22 | 86 | ✅ Abgeschlossen |
| 4 | Systems & Factories | ~15 | ~45 | 🔜 Nächste Phase |
| 5 | Backlog (Concrete Items, AI, Utils) | ~25 | ~60 | 📋 Geplant |
| **Gesamt** | | **~105** | **~316** | |

---

## ✅ Phase 1: HealthComponent & HealthSystem (Abgeschlossen)

Alle Tests unter `dungeon/test/contrib/components/healthcomponent/` und `dungeon/test/contrib/systems/healthsystem/`.

| Issue | Test-Datei | Status |
|-------|-----------|--------|
| #105 | `KonstruktorTest.java` | ✅ |
| #107 | `TriggerOnDeathTest.java` | ✅ |
| #108 | `OnDeathTest.java` | ✅ |
| #109 | `OnHitTest.java` | ✅ |
| #110 | `CalculateDamageOfTest.java` | ✅ |
| #111 | `ClearDamageTest.java` | ✅ |
| #112 | `CurrentHealthpointsTest.java` | ✅ |
| #113 | `MaximalHealthpointsTest.java` | ✅ |
| #114 | `RestoreHealthpointsTest.java` | ✅ |
| #115 | `LastDamageCauseTest.java` | ✅ |
| #116 | `IsDeadTest.java` | ✅ |
| #117 | `GodModeTest.java` | ✅ |
| #118 | `AlreadyDeadTest.java` | ✅ |
| #106 | `ReceiveHitTest.java` | ✅ |
| #68-#74 | HealthSystem-Tests (execute, applyDamage, etc.) | ✅ |

---

## ✅ Phase 2: Item & InventoryComponent (Abgeschlossen)

Alle Tests unter `dungeon/test/contrib/item/` und `dungeon/test/contrib/components/inventorycomponent/`.

| Issue | Test-Datei | Status |
|-------|-----------|--------|
| #8 | `ItemKonstruktorTest.java` | ✅ |
| #77 | `StackgroessenTest.java` | ✅ |
| #78 | `DropTest.java` | ✅ |
| #79 | `CollectTest.java` | ✅ |
| #80 | `MatchTest.java` | ✅ |
| #81 | `AllgemeineHilfsmethodenTest.java` | ✅ |
| #98 | `InventoryComponentKonstruktorTest.java` | ✅ |
| #82 | `AddTest.java` | ✅ |
| #83 | `RemoveTest.java` | ✅ |
| #84 | `RemoveAtIndexTest.java` | ✅ |
| #85 | `RemoveClassAmountTest.java` | ✅ |
| #86 | `RemoveOneTest.java` | ✅ |
| #87 | `HasItemTest.java` | ✅ |
| #88 | `HasItemClassTest.java` | ✅ |
| #89 | `ItemOfClassTest.java` | ✅ |
| #90 | `SmallestStackOfItemClassTest.java` | ✅ |
| #91 | `TransferTest.java` | ✅ |
| #92 | `TransferAllTest.java` | ✅ |
| #93 | `CountClassTest.java` | ✅ |
| #94 | `ItemsClassTest.java` | ✅ |
| #95 | `SetItemTest.java` | ✅ |
| #96 | `GetItemTest.java` | ✅ |
| #97 | `FindNextAvailableSlotTest.java` | ✅ |

---

## ✅ Phase 3: SkillComponent & HeroController (Abgeschlossen)

Alle Tests unter `dungeon/test/contrib/components/skillcomponent/` und `dungeon/test/contrib/entities/herocontroller/`.

| Issue | Test-Datei | Status |
|-------|-----------|--------|
| #119 | `SkillComponentKonstruktorTest.java` | ✅ |
| #120 | `AddSkillTest.java` | ✅ |
| #121 | `RemoveSkillInstanceTest.java` | ✅ |
| #122 | `RemoveAllTest.java` | ✅ |
| #123 | `RemoveSkillClassTest.java` | ✅ |
| #124 | `GetSkillTest.java` | ✅ |
| #125 | `ActiveMainSkillTest.java` | ✅ |
| #126 | `ActiveSecondSkillTest.java` | ✅ |
| #127 | `NextMainSkillTest.java` | ✅ |
| #128 | `PrevMainSkillTest.java` | ✅ |
| #129 | `NextSecondSkillTest.java` | ✅ |
| #130 | `PrevSecondSkillTest.java` | ✅ |
| #131 | `GetSkillsTest.java` | ✅ |
| #50 | `UseMainSkillTest.java` | ✅ |
| #51 | `UseSecondSkillTest.java` | ✅ |
| #55 | `ChangeMainSkillTest.java` | ✅ |
| #56 | `ChangeSecondSkillTest.java` | ✅ |
| #61 | `EnqueueInputTest.java` | ✅ |
| #62 | `DropItemTest.java` | ✅ |
| #64 | `UseItemTest.java` | ✅ |

---

## 🔜 Phase 4: Systems & Factories (Nächste Phase)

### 4a) LeverFactory Tests (Issues #28, #75, #76)

Tests unter `dungeon/test/contrib/entities/leverfactory/`.

- **[NEW] LeverFactoryTestBase.java**: Base-Klasse mit Level-Setup und Animations-Mocking.
- **[NEW] CreateLeverTest.java** (Issue #75): Tests für `createLever()` – Entity-Erstellung, Komponenten-Prüfung, Lever-State.
- **[NEW] PressurePlateTest.java** (Issue #76): Tests für `pressurePlate()` – Entity-Erstellung, PressurePlateComponent-Prüfung.

### 4b) CollisionSystem Tests (Issue #4)

Tests unter `dungeon/test/contrib/systems/collisionsystem/`.

> [!IMPORTANT]
> Die bestehende `CollisionComponentTest.java` deckt bereits CollideComponent ab. Für Issue #4 testen wir die `CollisionSystem`-Logik (Kollisionserkennung, Entity-Paare, Callbacks).

- **[NEW] CollisionSystemTestBase.java**: Base-Klasse mit Entity-Setup und CollideComponent-Konfiguration.
- **[NEW] CollisionDetectionTest.java** (Issue #4): Tests für Kollisionserkennung zwischen Entities.

### 4c) Restliche HeroController-Methoden (Issues #49, #52-#54, #57-#60, #63, #65)

Tests unter `dungeon/test/contrib/entities/herocontroller/` (existierendes Verzeichnis).

| Issue | Test-Datei | Beschreibung |
|-------|-----------|-------------|
| #49 | `MoveHeroTest.java` | Bewegung in alle Richtungen, deaktivierte Controls, VelocityComponent-Force |
| #52 | `InteractTest.java` | Interaktion mit Entities über Cursor-Modell |
| #53 | `FindInteractableTest.java` | Entity unter Cursor finden, Range-Check |
| #54 | `FindInteractablesInRangeTest.java` | Alle Entities in Reichweite finden |
| #57 | `IsInventoryOpenTest.java` | Prüfung ob Inventar-UI offen ist |
| #58 | `OpenInventoryTest.java` | Inventar öffnen, DialogFactory-Aufruf |
| #59 | `CloseInventoryTest.java` | Inventar schließen, UIComponent-Cleanup |
| #60 | `ToggleInventoryTest.java` | Inventar ein/ausschalten |
| #63 | `MoveItemTest.java` | Item zwischen Slots verschieben, negative Indizes |
| #65 | `DrainAndApplyInputsTest.java` | Input-Queue abarbeiten, Sequence-Validierung |

> [!WARNING]
> Einige dieser Methoden (interact, openInventory, closeInventory) erfordern umfangreiches Mocking von `UIUtils`, `DialogFactory` und Level-Infrastruktur. Diese könnten komplexer sein und mehr Setup benötigen.

### 4d) Weitere Systems (Issues #1-#3, #5-#6)

| Issue | Klasse | Test-Datei |
|-------|--------|-----------|
| #1 | [AttachmentSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/AttachmentSystem.java) | `AttachmentSystemTest.java` |
| #2 | [AttributeBarSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/AttributeBarSystem.java) | `AttributeBarSystemTest.java` |
| #3 | [DecoTestSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/DecoTestSystem.java) | `DecoTestSystemTest.java` |
| #5 | [DebugDrawSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/DebugDrawSystem.java) | `DebugDrawSystemTest.java` |
| #6 | [FogSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/FogSystem.java) | `FogSystemTest.java` |

> [!WARNING]
> Systems wie DebugDrawSystem (33KB), FogSystem (12KB) und CollisionSystem (13KB) sind sehr umfangreich. Tests könnten in mehrere Dateien aufgeteilt werden müssen. Einige Systems benötigen Rendering-Infrastruktur (LibGDX SpriteBatch, ShapeRenderer), was das Testen ohne GUI erschwert.

---

## 📋 Phase 5: Backlog Items (Detailplanung nach Phase 4)

### 5a) AIFactory (Issues #25, #99-#104)

Tests unter `dungeon/test/contrib/entities/aifactory/`.

| Issue | Test-Datei | Methode |
|-------|-----------|---------|
| #99 | `RandomMonsterTest.java` | `randomMonster()` |
| #100 | `RandomMonsterOrMeTest.java` | `randomMonsterOrMe(Entity)` |
| #101 | `RandomTransitionTest.java` | `randomTransition(Entity)` |
| #102 | `RandomIdleAITest.java` | `randomIdleAI()` |
| #103 | `RandomFightAITest.java` | `randomFightAI()` |
| #104 | `RandomAITest.java` | `randomAI(Entity)` |

### 5b) Concrete Items (Issues #20, #31-#33, #36, #40-#48)

Tests unter `dungeon/test/contrib/item/concreteitem/`.

| Issue | Klasse | Test-Datei |
|-------|--------|-----------|
| #31 | HintItem | `HintItemTest.java` |
| #32 | ItemBigKey | `ItemBigKeyTest.java` |
| #33 | ItemFairy | `ItemFairyTest.java` |
| #36 | ItemHammer | `ItemHammerTest.java` |
| #40 | ItemHeart | `ItemHeartTest.java` |
| #41 | ItemKey | `ItemKeyTest.java` |
| #42 | ItemPotionHealth | `ItemPotionHealthTest.java` |
| #43 | ItemPotionWater | `ItemPotionWaterTest.java` |
| #44 | ItemResourceBerry | `ItemResourceBerryTest.java` |
| #45 | ItemResourceEgg | `ItemResourceEggTest.java` |
| #46 | ItemResourceMushroomRed | `ItemResourceMushroomRedTest.java` |
| #47 | ItemWoodenArrow | `ItemWoodenArrowTest.java` |
| #48 | ItemWoodenBow | `ItemWoodenBowTest.java` |

### 5c) Components & Utilities (Issues #9-#11, #14-#15, #17, #19, #34-#35)

| Issue | Klasse | Beschreibung |
|-------|--------|-------------|
| #9 | ItemRegistry | Item-Registrierung und Lookup |
| #10 | EntityUtils | Entity-Hilfsmethoden |
| #11 | LevelUtils | Level-Hilfsmethoden |
| #14 | CollideComponent | Kollisions-Konfiguration |
| #15 | DamageComponent | Schadens-Verwaltung |
| #17 | ItemComponent | Item-Entity-Verknüpfung |
| #19 | AIComponent | KI-Steuerung |
| #34 | AIComponent (detail) | KI-Zustände und Transitions |
| #35 | AttachmentComponent | Attachment-Logik |

### 5d) Builders & Factories (Issues #26, #29-#30)

| Issue | Klasse | Beschreibung |
|-------|--------|-------------|
| #26 | HeroBuilder | Hero-Entity-Erstellung |
| #29 | MiscFactory | Diverse Entity-Erstellung (37KB – sehr umfangreich!) |
| #30 | MonsterBuilder | Monster-Entity-Erstellung |

### 5e) Sonstige (Issues #12-#13, #21-#24)

| Issue | Klasse | Beschreibung |
|-------|--------|-------------|
| #12 | HealthPotionType | Trank-Typen |
| #13 | DynamicCompiler | Dynamische Kompilierung |
| #21 | Keypad | Tastatureingabe-Rätsel |
| #22 | LevelHide | Level-Sichtbarkeit |
| #23 | Puzzle | Rätsel-Logik |
| #24 | WorldTimer | Spiel-Timer |

---

## Offene Fragen / User Review

> [!IMPORTANT]
> **Phase 4 Priorisierung**: Sollen wir mit Phase 4a (LeverFactory, einfachste Tests) beginnen, oder direkt die restlichen HeroController-Methoden (4c) abschließen?

> [!WARNING]
> **Rendering-abhängige Systems**: Die Systems `DebugDrawSystem`, `FogSystem`, `AttributeBarSystem` und `ShowImageSystem` nutzen LibGDX-Rendering (SpriteBatch, ShapeRenderer). Diese können ohne laufende OpenGL-Umgebung möglicherweise nicht getestet werden. Sollen wir diese überspringen oder nur die Logik-Teile testen?

> [!NOTE]
> **UI-abhängige HeroController-Methoden**: `openInventory()`, `closeInventory()`, `toggleInventory()` und `isInventoryOpen()` hängen von `UIUtils`, `DialogFactory` und `UIComponent` ab. Diese erfordern ein komplexes Test-Setup. Sollen wir diese dennoch implementieren oder als "UI-Tests" markieren und überspringen?

---

## Verification Plan

### Automated Tests
```powershell
# Alle Tests ausführen
.\gradlew :dungeon:test

# Einzelne Phasen testen
.\gradlew :dungeon:test --tests "contrib.components.skillcomponent.*"
.\gradlew :dungeon:test --tests "contrib.entities.herocontroller.*"
.\gradlew :dungeon:test --tests "contrib.entities.leverfactory.*"
.\gradlew :dungeon:test --tests "contrib.systems.collisionsystem.*"
```

### Formatting
```powershell
.\gradlew spotlessApply
```
