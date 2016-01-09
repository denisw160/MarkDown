# MarkDown
Die markdown.jar ist ein kleines Hilfsprogramm, das MarkDown-Dateien in HTML oder PDF umwandeln kann.
Diese Bibliothek kann als Maven Plugin integriert werden. Dazu ist Maven 3.2 oder zu verwenden.
Sie funktioniert mit JDK 1.7 oder 1.8.

## Usage
Dazu wird die pom.xml einfach um das Plugin "markdown-maven-plugin" erweitert.

    <plugin>
        <groupId>de.netpage</groupId>
        <artifactId>markdown-maven-plugin</artifactId>
        <version>x.x</version>
        <configuration>
            <directory>./doc</directory>
            <suffix>.md</suffix>
            <outputFormat>HTML</outputFormat>
            <recursive>true</recursive>
        </configuration>
    </plugin>

## Parameter
Mit folgenden Parameter kann das Plugin konfiguriert werden:

 - directory: Parameter für das zu verarbeitende Verzeichnis.
 - suffix: Parameter für die Endung der MarkDown-Dateien. (Standard = .md)
 - outputFormat: Parameter für das Ausgabeformat (HTML oder PDF). (Standard = HTML)
 - recursive: Parameter für die Verarbeitung von untergeordneten Verzeichnissen. (Standard = false)
              true = mit untergeordneten Verzeichnissen.
              false = nur angegebenes Verzeichnis.

## Ausführung
Optional kann noch die Phase der Ausführung konfiguriert werden. Standardmäßig reagiert das Plugin auf
die Package-Phase.

    <plugin>
        ...
        <executions>
            <execution>
                <id>compile-markdown</id>
                <phase>package</phase>
                <goals>
                    <goal>markdown</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

