# MarkDown
Die markdown.jar ist ein kleines Hilfsprogramm, das MarkDown-Dateien in HTML oder PDF umwandeln kann.

## Usage
Das Programm kann einfach über Java aufgerufen werden:

    usage: java -jar markdown.jar -d <arg> [-f <arg>] [-h] [-r] [-s <arg>]

Als Prgrammparameter ist nur "-d" zwingend erforderlich.

    MARKDOWN HELP
        -d,--directory <arg>     Directory for processing
        -f,--format <arg>        Format for output [html|pdf]
        -h,--help                Display help
        -r,--recursive           Process with subdirectories
        -s,--suffix <arg>        Suffix for files to processed

## Einbindung in Maven
Dazu wird die pom.xml einfach um das Plugin "exec-maven-plugin" erweitert.

    <!-- Compile MarkDown as HTML -->
    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.3.2</version>
        <configuration>
            <includeProjectDependencies>true</includeProjectDependencies>
            <mainClass>de.netpage.markdown.Main</mainClass>
            <arguments>
                <argument>-d</argument>
                <argument>${basedir}</argument>
                <argument>-f</argument>
                <argument>PDF</argument>
            </arguments>
        </configuration>
        <executions>
            <execution>
                <id>compile-markdown</id>
                <phase>package</phase>
                <goals>
                    <goal>java</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

## Aufgaben
 - Format-Darstellung für einzelne Dateiname (z.B. lange Dateinamen) optimieren. 
 - Einfache Zeilenumbrüche bei der PDF Generation
 - andere Implementierung: A pure-Java Markdown processor based on a parboiled PEG parser supporting a
number of extensions http://pegdown.org (siehe auch https://github.com/nicoulaj/idea-markdown)
