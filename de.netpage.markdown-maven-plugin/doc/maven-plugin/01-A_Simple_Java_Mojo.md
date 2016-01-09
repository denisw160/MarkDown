# A Simple Java Mojo
_(Source from: http://books.sonatype.com/mvnref-book/reference/writing-plugins-sect-custom-plugin.html)_

In this chapter, we’re going to introduce a Maven Mojo written in Java. Each Mojo in your project is going to implement the org.apache.maven.plugin.Mojo interface, the Mojo implementation shown in the following example implements the Mojo interface by extending the org.apache.maven.plugin.AbstractMojo class. Before we dive into the code for this Mojo, let’s take some time to explore the methods on the Mojo interface. Mojo provides the following methods:

    void setLog( org.apache.maven.monitor.logging.Log log )

Every Mojo implementation has to provide a way for the plugin to communicate the progress of a particular goal. Did the goal succeed? Or, was there a problem during goal execution? When Maven loads and executes a Mojo, it is going to call the setLog() method and supply the Mojo instance with a suitable logging destination to be used in your custom plugin.

    protected Log getLog()

Maven is going to call setLog() before your Mojo is executed, and your Mojo can retrieve the logging object by calling getLog(). Instead of printing out status to Standard Output or the console, your Mojo is going to invoke methods on the Log object.

    void execute() throws org.apache.maven.plugin.MojoExecutionException

This method is called by Maven when it is time to execute your goal.

The Mojo interface is concerned with two things: logging the results of goal execution and executing a goal. When you are writing a custom plugin, you’ll be extending AbstractMojo. AbstractMojo takes care of handling the setLog() and getLog() implementations and contains an abstract execute() method. When you extend AbstractMojo, all you need to do is implement the execute() method. A Simple EchoMojo shows a trivial Mojo implement which simply prints out a message to the console.

**A Simple EchoMojo.** 

    package org.sonatype.mavenbook.plugins;
    
    import org.apache.maven.plugin.AbstractMojo;
    import org.apache.maven.plugin.MojoExecutionException;
    import org.apache.maven.plugin.MojoFailureException;
    
    /**
     * Echos an object string to the output screen.
     * @goal echo
     * @requiresProject false
     */
    public class EchoMojo extends AbstractMojo
    {
        /**
         * Any Object to print out.
         * @parameter expression="${echo.message}" default-value="Hello World..."
         */
        private Object message;
    
        public void execute()
            throws MojoExecutionException, MojoFailureException
        {
            getLog().info( message.toString() );
        }
    }

If you create this Mojo in ${basedir} under src/main/java in org/sonatype/mavenbook/mojo/EchoMojo.java in the project created in the previous section and run mvn install, you should be able to invoke this goal directly from the command-line with:

    $ mvn org.sonatype.mavenbook.plugins:first-maven-plugin:1.0-SNAPSHOT:echo
    
That large command-line is mvn followed by the groupId:artifactId:version:goal. When you run this command-line you should see output that contains the output of the echo goal with the default message: "Hello Maven World…". If you want to customize the message, you can pass the value of the message parameter with the following command-line:

    $ mvn org.sonatype.mavenbook.plugins:first-maven-plugin:1.0-SNAPSHOT:echo \
      -Decho.message="The Eagle has Landed"

The previous command-line is going to execute the EchoMojo and print out the message "The Eagle has Landed".
