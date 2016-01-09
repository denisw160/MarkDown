# When a Mojo Fails
_(Source from: http://books.sonatype.com/mvnref-book/reference/writing-plugins-sect-custom-plugin.html)_

The execute() method in Mojo throws two exceptions MojoExecutionException and MojoFailureException. The difference 
between these two exception is both subtle and important, and it relates to what happens when a goal execution "fails". 
A MojoExecutionException is a fatal exception, something unrecoverable happened. You would throw a MojoExecutionException
if something happens that warrants a complete stop in a build; you re trying to write to disk, but there is no space 
left, or you were trying to publish to a remote repository, but you can’t connect to it. Throw a MojoExecutionException 
if there is no chance of a build continuing; something terrible has happened and you want the build to stop and the user 
to see a "BUILD ERROR" message.

A MojoFailureException is something less catastrophic, a goal can fail, but it might not be the end of the world for 
your Maven build. A unit test can fail, or a MD5 checksum can fail; both of these are potential problems, but you don’t 
want to return an exception that is going to kill the entire build. In this situation you would throw a 
MojoFailureException. Maven provides for different "resiliency" settings when it comes to project failure. 
Which are described below.

When you run a Maven build, it could involve a series of projects each of which can succeed or fail. You have the 
option of running Maven in three failure modes:

    mvn -ff

Fail-fast mode: Maven will fail (stop) at the first build failure.

    mvn -fae

Fail-at-end: Maven will fail at the end of the build. If a project in the Maven reactor fails, Maven will continue to 
build the rest of the builds and report a failure at the end of the build.

    mvn -fn

Fail never: Maven won’t stop for a failure and it won’t report a failure.

You might want to ignore failure if you are running a continuous integration build and you want to attempt a build 
regardless of the success of failure of an individual project build. As a plugin developer, you’ll have to make a call 
as to whether a particular failure condition is a MojoExecutionException or a MojoFailureExeception.
