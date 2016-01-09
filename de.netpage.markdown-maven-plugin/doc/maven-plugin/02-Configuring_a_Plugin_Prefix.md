# Configure a Plugin Prefix
_(Source from: http://books.sonatype.com/mvnref-book/reference/writing-plugins-sect-custom-plugin.html)_

Specifying the groupId, artifactId, version, and goal on the command-line is cumbersome. To address this, Maven assigns
a plugin a prefix. Instead of typing:

    $ mvn org.apache.maven.plugins:maven-jar-plugin:2.2:jar

You can use the plugin prefix jar and turn that command-line into mvn jar:jar. How does Maven resolve something like
jar:jar to org.apache.mven.plugins:maven-jar:2.3? Maven looks at a file in the Maven repository to obtain a list of
plugins for a specific groupId. By default, Maven is configured to look for plugins in two groups:
org.apache.maven.plugins and org.codehaus.mojo. When you specify a new plugin prefix like mvn hibernate3:hbm2ddl, Maven
is going to scan the repository metadata for the appropriate plugin prefix. First, Maven is going to scan the
org.apache.maven.plugins group for the plugin prefix hibernate3. If it doesn’t find the plugin prefix hibernate3 in the
org.apache.maven.plugins group it will scan the metadata for the org.codehaus.mojo group.

When Maven scans the metadata for a particular groupId, it is retrieving an XML file from the Maven repository which
captures metadata about the artifacts contained in a group. This XML file is specific for each repository referenced,
if you are not using a custom Maven repository, you will be able to see the Maven metadata for the
org.apache.maven.plugins group in your local Maven repository (~/.m2/repository) under
org/apache/maven/plugins/maven-metadata-central.xml. Maven Metadata for the Maven Plugin Group shows a snippet of the
maven-metadata-central.xml file from the org.apache.maven.plugin group.

**Maven Metadata for the Maven Plugin Group.** 

    <?xml version="1.0" encoding="UTF-8"?>
    <metadata>
        <plugins>
            <plugin>
                <name>Maven Clean Plugin</name>
                <prefix>clean</prefix>
                <artifactId>maven-clean-plugin</artifactId>
            </plugin>
            <plugin>
                <name>Maven Compiler Plugin</name>
                <prefix>compiler</prefix>
                <artifactId>maven-compiler-plugin</artifactId>
            </plugin>
            <plugin>
                <name>Maven Surefire Plugin</name>
                <prefix>surefire</prefix>
                <artifactId>maven-surefire-plugin</artifactId>
            </plugin>
            ...
        </plugins>
    </metadata>

As you can see in Maven Metadata for the Maven Plugin Group, this maven-metadata-central.xml file in your local repository is what makes it possible for you to execute mvn surefire:test. Maven scans org.apache.maven.plugins and org.codehaus.mojo: plugins from org.apache.maven.plugins are considered core Maven plugins and plugins from org.codehaus.mojo are considered extra plugins. The Apache Maven project manages the org.apache.maven.plugins group, and a separate independent open source community manages the Codehaus Mojo project. If you would like to start publishing plugins to your own groupId, and you would like Maven to automatically scan your own groupId for plugin prefixes, you can customize the groups that Maven scans for plugins in your Maven Settings.

If you wanted to be able to run the first-maven-plugins echo goal by running first:echo, add the org.sonatype.mavenbook.plugins groupId to your '~/.m2/settings.xml as shown in Customizing the Plugin Groups in Maven Settings. This will prepend the org.sonatype.mavenbook.plugins to the list of groups which Maven scans for Maven plugins.

Customizing the Plugin Groups in Maven Settings. 

    <settings>
        ...
        <pluginGroups>
            <pluginGroup>org.sonatype.mavenbook.plugins</pluginGroup>
        </pluginGroups>
    </settings>

You can now run mvn first:echo from any directory and see that Maven will properly resolve the goal prefix to the appropriate plugin identifiers. This worked because our project adhered to a naming convention for Maven plugins. If your plugin project has an artifactId which follows the pattern maven-first-plugin or first-maven-plugin. Maven will automatically assign a plugin goal prefix of first to your plugin. In other words, when the Maven Plugin Plugin is generating the Plugin descriptor for your plugin and you have not explicitly set the goalPrefix in your project, the plugin:descriptor goal will extract the prefix from your plugin’s artifactId when it matches the following patterns:

    ${prefix}-maven-plugin, OR
    maven-${prefix}-plugin

If you would like to set an explicit plugin prefix, you’ll need to configure the Maven Plugin Plugin. The Maven Plugin Plugin is a plugin that is responsible for building the Plugin descriptor and performing plugin specific tasks during the package and load phases. The Maven Plugin Plugin can be configured just like any other plugin in the build element. 
To set the plugin prefix for your plugin, add the following build element to the first-maven-plugin project’s pom.xml.

**Configuring a Plugin Prefix.** 

    <?xml version="1.0" encoding="UTF-8"?><project>
        <modelVersion>4.0.0</modelVersion>
        <groupId>org.sonatype.mavenbook.plugins</groupId>
        <artifactId>first-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <packaging>maven-plugin</packaging>
        <name>first-maven-plugin Maven Mojo</name>
        <url>http://maven.apache.org</url>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-plugin-plugin</artifactId>
                    <version>2.3</version>
                    <configuration>
                        <goalPrefix>blah</goalPrefix>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        <dependencies>
            <dependency>
                <groupId>org.apache.maven</groupId>
                <artifactId>maven-plugin-api</artifactId>
                <version>2.0</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>3.8.1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </project>

Configuring a Plugin Prefix sets the plugin prefix to blah. If you’ve added the org.sonatype.mavenbook.plugins to the pluginGroups in your ~/.m2/settings.xml, you should be able to execute the EchoMojo by running mvn blah:echo from any directory.
