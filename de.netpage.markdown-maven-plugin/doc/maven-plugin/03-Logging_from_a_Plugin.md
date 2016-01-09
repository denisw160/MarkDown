# Logging from a Plugin
_(Source from: http://books.sonatype.com/mvnref-book/reference/writing-plugins-sect-custom-plugin.html)_

Maven takes care of connecting your Mojo to a logging provider by calling setLog() prior to the execution of your Mojo. 
It supplies an implementation of org.apache.maven.monitor.logging.Log. This class exposes methods that you can use to 
communicate information back to the user. This Log class provides multiple levels of logging similar to that API 
provided by Log4J. Those levels are captured by a series of methods available for each level: debug, info, error and 
warn. To save trees, we’ve only listed the methods for a single logging level: debug.

    void debug( CharSequence message)

Prints a message to the debug logging level.

    void debug( CharSequence message, Throwable t)

Prints a message to the debug logging level which includes the stack trace from the Throwable (either Exception or Error)

    void debug( Throwable t )

Prints out the stack trace of the Throwable (either Exception or Error)

Each of the four levels exposes the same three methods. The four logging levels serve different purposes. The debug 
level exists for debugging purposes and for people who want to see a very detailed picture of the execution of a Mojo. 
You should use the debug logging level to provide as much detail on the execution of a Mojo, but you should never 
assume that a user is going to see the debug level. The info level is for general informational messages that should 
be printed as a normal course of operation. If you were building a plugin that compiled code using a compiler, you 
might want to print the output of the compiler to the screen.

The warn logging level is used for messages about unexpected events and errors that your Mojo can cope with. If you 
were trying to run a plugin that compiled Ruby source code, and there was no Ruby source code available, you might 
want to just print a warning message and move on. Warnings are not fatal, but errors are usually build-stopping 
conditions. For the completely unexpected error condition, there is the error logging level. You would use error if 
you couldn’t continue executing a Mojo. If you were writing a Mojo to compile some Java code and the compiler wasn’t 
available, you’d print a message to the error level and possibly pass along an Exception that Maven could print out 
for the user. You should assume that a user is going to see most of the messages in info and all of the messages 
in error.
