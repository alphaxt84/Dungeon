# Walkthrough: Unit Tests für AttachmentSystem, AttributeBarSystem & DoorTile

Wir haben erfolgreich Test-Sets für das `AttachmentSystem` (inkl. Bugfix), das `AttributeBarSystem` und das `DoorTile` (repräsentiert das Door-System) erstellt.

## Gemachte Änderungen

### 1. [NEW] [AttachmentSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/AttachmentSystemTest.java)
Ein umfassendes JUnit-5-Testset, welches folgende Funktionalitäten verifiziert:
* **Positionsberechnung mit Offset**: Überprüfung, ob die Kopie korrekt an den Koordinaten `Origin + Offset * Scale` positioniert wird.
* **Positionsberechnung mit Skalierung**: Überprüfung, ob Distanzen und Offsets korrekt skaliert werden.
* **Rotation mit Origin**: Überprüfung, ob die Kopie der Sichtrichtung des Originals folgt (UP, DOWN, LEFT, RIGHT).
* **Textur-Rotation**: Verifikation, ob die Textur-Rotation bei Richtungswechseln korrekt angepasst wird (0°, 90°, 180°, 270°).
* **De-Registrierung**: Überprüfung, ob ein unregistriertes Attachment nicht weiter aktualisiert wird.
* **Bereinigung bei Entfernen von Entities**: Testet den `onEntityRemove`-Listener, wenn entweder die Kopie- oder die Origin-Entity aus dem Spiel gelöscht wird.

### 2. [MODIFY] [AttachmentSystem.java](file:///c:/Github%20Repos/Dungeon/dungeon/src/contrib/systems/AttachmentSystem.java)
Während der Testentwicklung stießen wir auf eine **NullPointerException** im Originalcode:
* **Ursache**: Wenn ein Attachment de-registriert wird oder eine beteiligte Entity (z. B. der Origin) aus dem Spiel entfernt wird, liefert `attachmentMap.get(copypc)` den Wert `null` zurück. In `applyAttachment` wurde jedoch versucht, auf die Position des gelöschten Origins zuzugreifen, was zum Absturz des gesamten Spiels führte.
* **Behebung**: Es wurde eine Null-Prüfung am Anfang der Methode `applyAttachment` eingefügt:
  ```java
  private void applyAttachment(ASData asData) {
    if (asData.originpc == null) {
      return;
    }
    ...
  }
  ```

### 3. [NEW] [AttributeBarSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/AttributeBarSystemTest.java)
Ein Unit-Testset zur Überprüfung des `AttributeBarSystem`:
* **Fehlende UI-Balken**: Verifikation, dass das System nicht abstürzt, wenn Entities keine anzeigbaren Attribute (Health, Mana, Stamina) besitzen.
* **Verarbeitung von BarDisplayable-Komponenten**: Verifikation, dass das Hinzufügen von `HealthComponent` fehlerfrei verarbeitet wird.
* **Mehrere Komponenten**: Testet das gleichzeitige Vorhandensein von `HealthComponent`, `ManaComponent` und `StaminaComponent`.
* **Bereinigung bei Entfernen**: Verifiziert das ordnungsgemäße Aufräumen von UI-Ressourcen über `onEntityRemove` beim Löschen von Entities.

### 4. [NEW] [DoorTileTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/core/level/elements/tile/DoorTileTest.java)
Ein Unit-Testset zur Abdeckung der Tür-Mechaniken (`DoorTile`) im Dungeon:
* **Initialer Zustand**: Überprüfung der korrekten Zuweisung von `LevelElement.DOOR` sowie der Standardeinstellung (Tür ist anfangs offen und passierbar).
* **Zustandswechsel (Open/Close)**: Testet, ob die Methoden `open()` und `close()` die Eigenschaften `isOpen()`, `isAccessible()` und `canSeeThrough()` korrekt umschalten.
* **Dynamischer Texturwechsel**: Verifiziert, dass beim Schließen der Tür die Textur mit dem Suffix `_closed` ermittelt wird und bei einer leeren Textur keine Abstürze auftreten.
* **toString-Metadaten**: Stellt sicher, dass die Kachelinformationen inklusive Status korrekt formatiert ausgegeben werden.

---

## Testergebnisse & Validierung

Die neuen Tests wurden lokal mittels Gradle ausgeführt und verifiziert:
```powershell
.\gradlew :dungeon:test --tests "contrib.systems.AttachmentSystemTest"
.\gradlew :dungeon:test --tests "contrib.systems.AttributeBarSystemTest"
.\gradlew :dungeon:test --tests "core.level.elements.tile.DoorTileTest"
```

Alle Unit-Tests laufen fehlerfrei durch und bestätigen die korrekte Logik der implementierten Systeme und Kacheln.


