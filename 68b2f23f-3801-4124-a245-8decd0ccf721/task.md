# Dungeon-Breaker Testentwicklung: Aufgabenstand

Hier ist unser aktueller Fortschritt bei der Implementierung von Unit- und Component-Tests für die Epics im Dungeon-Breaker-Projekt:

- [x] **Milestone/Epic: AttachmentSystem (#1)**
  - [x] Erstellung von `AttachmentSystemTest.java` (Kombination aus System- und Component-Tests)
  - [x] Behebung der `NullPointerException` bei unregistrierten Attachments in `AttachmentSystem.java`
- [x] **Milestone/Epic: AttributeBarSystem (#2)**
  - [x] Erstellung von `AttributeBarSystemTest.java` (Tests für Health/Mana/Stamina Balken im Headless-Modus)
- [x] **Milestone/Epic: DoorSystem (#6)**
  - [x] Erstellung von `DoorTileTest.java` zur Abdeckung der Tür-Mechaniken (isAccessible, canSeeThrough, dynamic texture paths)
- [ ] **Milestone/Epic: DecoTestSystem (#3)**
  - [ ] Erstellung von `DecoTestSystemTest.java` zur Validierung der Deko-Kacheln
- [ ] **Milestone/Epic: DebugDrawSystem (#5)**
  - [ ] Erstellung von `DebugDrawSystemTest.java` für HUD-Visualisierungen
- [ ] **Milestone/Epic: DynamicLightSystem (#7)**
  - [ ] Analyse der Lichtkacheln und Testentwicklung
- [ ] **Milestone/Epic: LevelUpSystem (#8)**
  - [ ] Testentwicklung für Charakter-Level-Aufstiege und XP-Systeme
