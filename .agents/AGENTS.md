# CI Checklist / Programmierregeln

Folgende Prüfungen (CI Checks) müssen beim Programmieren von Code beachtet und vor einem Commit lokal verifiziert werden:

1. **Gradle Build (Cross-Platform Compilation)**
   - Befehl: `./gradlew assemble`
   - Plattform-Matrix: `ubuntu-latest`, `windows-latest`, `macos-latest`
   - Zweck: Stellt sicher, dass das Projekt auf allen drei Betriebssystemen fehlerfrei kompiliert.

2. **Lizenz-Kompatibilität**
   - Befehle: `./gradlew generateLicenseReport` und `./gradlew checkLicense`
   - Plattform: `ubuntu-latest`
   - Zweck: Überprüft die Einhaltung und Deklaration der Lizenzen aller genutzten Bibliotheken.

3. **Backend-Tests (JUnit - Linux & Matrix Build)**
   - Befehle:
     - **JUnit (Linux only)**: `./gradlew test` (läuft immer bei PRs)
     - **JUnit (matrix build)**: `./gradlew test` auf `windows-latest` und `macos-latest` (läuft, wenn Test-Dateien modifiziert wurden)
   - Zweck: Führt alle JUnit-Tests aus. Alle Tests müssen auf allen Plattformen (Linux, macOS, Windows) fehlerfrei durchlaufen.

4. **Formatierung (Spotless)**
   - Befehl: `./gradlew spotlessJavaCheck` (oder `./gradlew spotlessApply` zur automatischen Formatierung)
   - Plattform: `ubuntu-latest`
   - Zweck: Stellt sicher, dass der Java-Code den einheitlichen Formatierungsrichtlinien entspricht.

5. **Statische Codeanalyse (Checkstyle)**
   - Befehl: `./gradlew checkstyleMain checkstyleTest`
   - Plattform: `ubuntu-latest`
   - Zweck: Prüft den Programmierstil und deckt potenzielle Fehler oder Richtlinienverstöße im Code auf.

6. **Frontend-Checks (Frontend - matrix build)**
   - Befehle (nur wenn Dateien unter `blockly/frontend/` modifiziert wurden): 
     - `npm ci` (Abhängigkeiten sauber installieren)
     - `npm run build` (Frontend bauen)
     - `npm run lint` (Statische Codeanalyse für Frontend)
   - Plattform-Matrix: `ubuntu-latest`, `windows-latest`, `macos-latest`
   - Zweck: Garantiert die Funktionsfähigkeit und Codequalität des Blockly-Frontends auf allen Betriebssystemen.

7. **Keine Änderungen am Original-Code durch Tests**
   - Zweck: Tests müssen so geschrieben werden, dass keine Änderungen am Original-Code erforderlich sind.


