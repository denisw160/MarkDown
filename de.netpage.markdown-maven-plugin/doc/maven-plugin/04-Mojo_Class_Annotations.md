# Mojo Class Annotations
_(Source from: http://books.sonatype.com/mvnref-book/reference/writing-plugins-sect-custom-plugin.html)_

In first-maven-plugin, you didn’t write the plugin descriptor yourself, you relied on Maven to generate the plugin 
descriptor from your source code. The descriptor was generated using your plugin project’s POM information and a set of 
annotations on your EchoMojo class. EchoMojo only specifies the @goal annotation, here is a list of other annotations 
you can place on your Mojo implementation.

    @goal <goalName>

This is the only required annotation which gives a name to this goal unique to this plugin.

    @requiresDependencyResolution <requireScope>

Flags this mojo as requiring the dependencies in the specified scope (or an implied scope) to be resolved before it 
can execute. Supports compile, runtime, and test. If this annotation had a value of test, it would tell Maven that the 
Mojo cannot be executed until the dependencies in the test scope had been resolved.

    @requiresProject (true|false)

Marks that this goal must be run inside of a project, default is true. This is opposed to plugins like archetypes, 
which do not.

    @requiresReports (true|false)

If you were creating a plugin that relies on the presence of reports, you would need to set requiresReports to true. 
The default value of this annotation is false.

    @aggregator (true|false)

A Mojo with aggregator set to true is supposed to only run once during the execution of Maven. It was created to give 
plugin developers the ability to summarize the output of a series of builds; for example, to create a plugin that 
summarizes a report across all projects included in a build. A goal with aggregator set to true should only be run 
against the top-level project in a Maven build. The default value of aggregator is false.

    @requiresOnline (true|false)

When set to true, Maven must not be running in offline mode when this goal is executed. Maven will throw an error if 
one attempts to execute this goal offline. Default: false.

    @requiresDirectInvocation

When set to true, the goal can only be executed if it is explicitly executed from the command-line by the user. Maven 
will throw an error if someone tries to bind this goal to a lifecycle phase. The default for this annotation is false.

    @phase <phaseName>

This annotation specifies the default phase for this goal. If you add an execution for this goal to a pom.xml and do 
not specify the phase, Maven will bind the goal to the phase specified in this annotation by default.

    @execute [goal=goalName|phase=phaseName [lifecycle=lifecycleId]]

This annotation can be used in a number of ways. If a phase is supplied, Maven will execute a parallel lifecycle ending
in the specified phase. The results of this separate execution will be made available in the Maven property 
${executedProperty}.

The second way of using this annotation is to specify an explicit goal using the prefix:goal notation. When you specify
just a goal, Maven will execute this goal in a parallel environment that will not affect the current Maven build.

The third way of using this annotation would be to specify a phase in an alternate lifecycle using the identifier of 
a lifecycle.

    @execute phase="package" lifecycle="zip"
    @execute phase="compile"
    @execute goal="zip:zip"

If you look at the source for EchoMojo, you’ll notice that Maven is not using the standard annotations available in 
Java 5. Instead, it is using Commons Attributes. Commons Attributes provided a way for Java programmers to use 
annotations before annotations were a part of the Java language specification. Why doesn’t Maven use Java 5
annotations? Maven doesn’t use Java 5 annotations because it is designed to target pre-Java 5 JVMs. Because Maven has 
to support older versions of Java, it cannot use any of the newer features available in Java 5.
