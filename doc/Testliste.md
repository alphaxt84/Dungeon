# Dungeon-Breaker: Übersicht aller entwickelten Testreihen

Hier ist eine detaillierte Übersicht über alle Testklassen, die heute für das `:dungeon`-Subprojekt implementiert und verifiziert wurden.

---

### 1. [AttachmentSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/AttachmentSystemTest.java) (Epic: `AttachmentSystem`)
Dieses System steuert die Positionierung und Darstellung von Ausrüstungsgegenständen und visuellen Effekten (Attachments), die an Entitäten angeheftet sind.

* **Wie getestet wurde**:
  * **Heften und Lösen**: Prüft, dass Attachments korrekt hinzugefügt, aktualisiert und wieder entfernt werden können.
  * **Positionierung**: Verifiziert, dass sich das Offset des Attachments relativ zur Position der Träger-Entität korrekt berechnet.
  * **NPE-Behebung**: Behebt und testet ein Fehlverhalten (`NullPointerException`), das auftrat, wenn versucht wurde, ein unregistriertes Attachment zu verarbeiten.
* **Ergebnis**: **Erfolgreich (PASSED)**.

---

### 2. [AttributeBarSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/AttributeBarSystemTest.java) (Epic: `AttributeBarSystem`)
Dieses System rendert Lebens-, Mana- und Ausdauer-Balken über den Köpfen von Charakteren im Spiel.

* **Wie getestet wurde**:
  * **Dynamische Erstellung**: Prüft, dass Balken automatisch erstellt werden, sobald `HealthComponent`, `ManaComponent` oder `StaminaComponent` zu einer Entität hinzugefügt werden.
  * **Bereinigung**: Verifiziert, dass beim Entfernen von Entitäten auch deren Balken rückstandsfrei gelöscht werden.
  * **Headless-Kompatibilität**: Stellt sicher, dass das System in Testumgebungen ohne Grafikkarte (Headless-Modus) keine OpenGL-Fehler wirft.
* **Ergebnis**: **Erfolgreich (PASSED)**.

---

### 3. [DoorTileTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/core/level/elements/tile/DoorTileTest.java) (Epic: `DoorSystem`)
Testet die Logik und Funktionalität der Kacheln für Türen im Dungeon.

* **Wie getestet wurde**:
  * **Zustandsprüfungen**: Verifiziert, ob Türen im geöffneten Zustand begehbar (`isAccessible() == true`) und durchsichtig (`canSeeThrough() == true`) sind, und im geschlossenen Zustand beides blockieren.
  * **Texturen-Zuweisung**: Validiert, dass geschlossene Türen automatisch die geänderten geschlossenen Texturpfade laden (z. B. Anhängen von `_closed` an den Pfad).
* **Ergebnis**: **Erfolgreich (PASSED)**.

---

### 4. [DecoTestSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/DecoTestSystemTest.java) (Epic: `DecoTestSystem`)
System zum interaktiven Bearbeiten, Verschieben und Anpassen von Dekorationsobjekten im Level-Editor.

* **Wie getestet wurde**:
  * **Editiermodi**: Simuliert Tastenanschläge und prüft, ob das System zyklisch zwischen Versatz- (X/Y-Offset) und Größeneinstellungsmodi wechselt.
  * **Clipboard-Export**: Testet, ob die generierte Quelltext-Zeile (z. B. `new Rectangle(...)`) im korrekten Format an die System-Zwischenablage übergeben wird.
  * **FontHelper-Mocking**: Fängt Dateizugriffe auf TTF-Schriften ab, damit Shading-/Ladefehler in headless-Tests verhindert werden.
* **Ergebnis**: **Erfolgreich (PASSED)**.

---

### 5. [DebugDrawSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/DebugDrawSystemTest.java) (Epic: `DebugDrawSystem`)
Das Debug-Zeichnungssystem kümmert sich um HUD-Kamera-Skalierungen, Telemetriedaten-Caching sowie das Ein- und Ausblenden von Entwickler-Overlays.

* **Wie getestet wurde**:
  * **Kamera-Steuerung**: Verifiziert das Togglen von HUD-Elementen sowie die Anpassung der Kameraschritte/Zoom-Stufen.
  * **Telemetrie**: Validiert das Caching und die Bereitstellung von Netzwerkschnittstellen-Daten und Systemtelemetrie.
  * **Headless-Sicherheit**: Stellt sicher, dass das System in einer Headless-Umgebung (ohne Grafikkontext) sicher läuft, ohne OpenGL-Shader-Fehler auszulösen.
* **Ergebnis**: **Erfolgreich (PASSED)**.

---

### 6. [FogSystemTest.java](file:///c:/Github%20Repos/Dungeon/dungeon/test/contrib/systems/FogSystemTest.java) (Epic: `DynamicLightSystem`)
Steuert das Nebelsystem (Fog-of-War) und die Sichtlinie (Line-of-Sight) des Spielers, verdunkelt Kacheln im Nebel und verbirgt gegnerische Entitäten.

* **Wie getestet wurde**:
  * **Reichweiten-Prüfung**: Ein 5x5-Layout wurde geladen. Kacheln in direkter Nähe des Spielers (Sichtweite 1) bleiben hell, während weiter entfernte Kacheln abgedunkelt werden.
  * **Entitäten verbergen**: Platziert eine Entität weit entfernt im Nebel und prüft, ob ihre Sichtbarkeit (`DrawComponent.isVisible()`) auf `false` gesetzt wird. Nähert sich der Spieler, wird sie wieder sichtbar.
  * **Bugfix-Überprüfung**: Verifiziert, dass das Deaktivieren des Nebelsystems (`active(false)`) oder ein System-`reset()` alle abgedunkelten Kacheln wieder ordnungsgemäß aufhellt (was durch einen Fehler in `FogSystem.java` zuvor blockiert war).
* **Ergebnis**: **Erfolgreich (PASSED)**.
