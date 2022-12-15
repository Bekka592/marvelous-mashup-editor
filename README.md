# Marvelous Mashup Editor

Editor to configure a match of "Marvelous Mashup".
The game was created as part of a group software project at Ulm University and in total consists of a server, a client, an AI-client and an editor.
I focussed on creating the editor component, where I was supported by the "util" and "ui-util" packages by Constanze and Simon.
The editor was finally promoted and "sold" very successfully to the other teams.
The following is the component's manual in German:

## Installation und Bedienung

### Systemanforderungen
- Java 15
- Die Anwendung läuft sicher unter Windows 10. Wir haben keine Windows spezifischen Features genutzt, weshalb
  sie auf Linux eigentlich auch laufen sollte, aber das können wir leider nicht zu 100% garantieren.
- Gradle 7 wird mitgeliefert, jedoch kommt es gelegentlich zu Problemen, wenn man lokal noch zusätzlich eine ältere Gradle Version installiert hat.
Falls sich Gradle also beschwert, kann es helfen die lokale Version zu updaten oder einfach zu entfernen.

### Anwendung ausführen
Klont euch dafür das Repository und öffnet den Ordner [SoftwareProjektTeam29](../SoftwareProjektTeam29) mit einer IDE eurer Wahl.
Dann könnt ihr über das Gradle Menü und  _launcher > tasks > execution > startEditor_ beziehungsweise über das Terminal
unter Windows mittels `gradlew.bat launcher:startEditor` die Anwendung ausführen.

### Unit Tests laufen lassen
Die JUnit Tests können mittels `gradlew.bat test` bzw `gradlew.bat cleanTest test` ausgeführt werden, oder
aber über die Gradle Task `SoftwareProjektTeam29 > Tasks > verification > test` im Gradle Menü.
Zusätzlich haben wir auch Jacoco integriert. Das könnt ihr gerne ebenfalls nutzen, oder einfach raus tun, falls ihr es nicht benötigt.

### Sonstiges
- Zum Video: die FileChooser wurden leider von OBS nicht richtig aufgenommen, aber stellt euch einfach vor da wären FileChooser
  zu sehen gewesen :D
- Ihr könnt euch jederzeit selbst JARs erstellen: Dazu müsst ihr den Gradle-Task _SoftwareProjektTeam29 > Tasks > shadow > shadownJar_
ausführen. Die Jar ist dann unter _SoftwareProjektTeam29/launcher/build/libs/marvelousmashup.jar_ zu finden.
- Es kann gut sein, dass es zu Bugs in der UI kommt, wenn ihr bei Windows eine Skalierung von über 100% eingestellt habt. Da das aber unseres Wissens
nach kaum jemand nutzt, haben wir uns diesem Problem bislang noch nicht gewidmet. Wenn das doch für mehrere Leute
  ein Problem sein sollte, können wir gerne noch einen Bugfix hinterher schicken.

## Anwendung und Personalisierung
- In der Klasse [ParameterSpecifications](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/model/ParameterSpecifications.java)
  können die zugelassenen Parameterwerte, sowie die Richtwerte für zufallsgenerierten Konfigurationen angepasst werden.
- Weitere Charaktere, die euer Editor zusätzlich zu den 24 Standard-Charakteren unterstützen soll, können dem
  [SupportedCharactersEnum](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/model/SupportedCharactersEnum.java)
  hinzugefügt werden.
- In der Klasse [AbstractGameWithMusic](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/AbstractGameWithMusic.java)
  können die Basis-Werte für die Lautstärke der Sounds angepasst werden.
- In den Klassen [ColorEnum](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/options/ColorEnum.java) und
  [EditorDefaults](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/styling/EditorDefaults.java)
  können die Farben der Anwendung angepasst werden. Gerne könnt ihr auch weitere Defaults erstellen. Diese müssen dann ebenfalls von
  [IContainDefaults](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/styling/IContainDefaults.java)
  erben.
- Die generellen Styling-Defaults können durch das Anpassen der Konstanten in
  [ComponentsSizeEnum](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/options/ComponentsSizeEnum.java),
  [TextStyleEnum](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/options/TextStyleEnum.java),
  [TextSizeEnum](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/options/TextSizeEnum.java) und
  [UIConstants](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/UIConstants.java) geändert werden.
- Das Styling von UI-Komponenten (Buttons etc) kann nach Belieben in [ComponentCreator](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/styling/ComponentCreator.java)
genutzt und angepasst werden.

## Code Struktur

Generell nutzen wir modul-übergreifend überall LibGdx in Version LWJGL3.

### [Asset Modul](../SoftwareProjektTeam29/assets)
Hier sind all unsere Texturen, Sounds und co gespeichert. Diese könnt ihr nach Belieben zu euren eigenen abändern.
Vielen Dank an Vanessa Güntzel für die Grafiken der Charaktere.

### [Launcher Modul](../SoftwareProjektTeam29/launcher/src/main/java/marvelous_mashup/team29/launcher)
Dieses Modul hat lediglich die Aufgabe die LibGdx Anwendung zu starten und dabei die Startparameter angemessen zu konfigurieren.

### [Editor Modul](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor)
- Greift auf die Funktionalitäten von UI-Util und Util zurück. Nutzt außerdem Gson.
- Hier ist die wesentliche Funktionalität des Editors gelagert.
- Die [Editor](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/Editor.java) Klasse kümmert sich um
das generelle Management der Screens.
- Die Klassen [CharacterConfigScreen](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/gui/config_screens/CharacterConfigScreen.java),
  [MatchConfigScreen](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/gui/config_screens/MatchConfigScreen.java)
  und [ScenarioConfigScreen](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/gui/config_screens/ScenarioConfigScreen.java)
  bilden zusammen im Wechselspiel mit [CharacterConfigLogic](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/logic/CharacterConfigLogic.java),
  [MatchConfigLogic](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/logic/MatchConfigLogic.java)
  und [ScenarioConfigLogic](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/logic/ScenarioConfigLogic.java)
  die zentralen Punkte der Konfigurationsbildschirme.
- Um das Parsing nachzuvollziehen, lohnt es sich vor allen, sich die Klasse [FileIOLogic](../SoftwareProjektTeam29/editor/src/main/java/marvelous_mashup/team29/editor/logic/parsing/FileIOLogic.java)
anzuschauen.

### [UI-Util Modul](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util)
- Nutzt die Funktionen des Util Moduls.
- Dieses Modul enthält alle UI-Funktionalitäten, die der Editor benötigt. Hier wird unser eigener Skin,
  der Marvel Style, erstellt und es werden abstrakte Screens und Komponenten bereitgestellt, damit alle darauf
  basierenden Anwendungen den Style unkompliziert nutzen können.
- Mittels dem [ComponentCreator](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/styling/ComponentCreator.java)
können verschiedenste UI-Elemente erstellt werden.
- All unsere Screens erben von [AbstractScreen](../SoftwareProjektTeam29/ui-util/src/main/java/marvelous_mashup/team29/ui_util/screens/AbstractScreen.java),
welcher das Design vereinheitlicht und die Komplexität eines LibGdx Screens verbirgt.

### [Util Modul](../SoftwareProjektTeam29/util/src/main/java/marvelous_mashup/team29/util)
- Nutzt Justify zur Validierung von JSON Dateien mit einem Schema.
- Dieses Modul enthält alle, nicht GUI bezogenen, Hilfsfunktionen für den Editor.
- So befindet sich hier zum Beispiel unser [AssetLoader](../SoftwareProjektTeam29/util/src/main/java/marvelous_mashup/team29/util/asset_loader/AssetFinder.java),
der nach Belieben um zusätzliche Dateien erweitert werden kann.
- Auch die Logik der Map und grundlegende JSON/File-Funktionalitäten sind hier zu finden.