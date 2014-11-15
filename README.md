spring-project-skeleton
=======================
This is a blank project to get started with Spring 4. It's oriented towards a REST-ish backend, but not necessarily limited to that.  You'll want to use
Eclipse with STS and Groovy Eclipse plugin to run this one.  It was built on Tomcat 7.0.50 and Java 7_45.

It uses mostly Spring Java Config to set things up, with some XML-config where it was either easier or I just knew how to do it with XML already and didn't
feel like learning the Java Config way.  :-) Config begins in the config package with WebConfig, AppConfig (root application context), and the 
DispatcherServletInitializer.

The project makes heavy use of Groovy, because Groovy just makes things easier, and it integrates seamlessly in Eclipse (through the Groovy Eclipse Plugin) and
and build time (through the Groovy Eclipse Compiler Maven Plugin).  What could be easier?

## External Configuration
The environment-specific configuration (database connection info, etc) goes into a file in CATALINA_HOME/conf called myspringapp.properties.  You can see the 
configuration for this file in the application-context.xml file.  There is a development version of this file in misc/tomcat.  To install the file, just
run the configTomcat task in the Ant file in the root of the project.  (My philosophy is that Maven rocks at building a WAR, but sucks at everything else.  So,
until I finally learn Gradle, I use Ant for moving files around and any other tasks that are outside the actual building of the WAR).

## Db Migration
I'm using FlywayDb to migrate the database.  Migration scripts are in src/main/resources/db/migration.  The Flyway maven plugin is also installed in the POM
and pointing at your local database (MySQL) so you can clean and migrate from the command line.

## RESTish Web Services
The services are sort of REST, posting and returning JSON.  They are not pure REST because I only used GET and POST, and there are some actions (/user/create) in
the URLs.  I realize this will cause the local economy to collapse and band of roving mutants to scorch the earth, but some http clients get really
awkward once you stray beyond GET and POST.

There is a GlobalErrorHandler installed with Spring's @ControllerAdvice that catches all the exceptions and translates them into error codes.  The actual
HTTP return codes used are a little haphazard, it wasn't always clear what to use.  A simple WebServiceResponse wraps all responses in a common JSON envelope
with status and error code messages for uniform parsing.

JSR-303 validation is used, with validation groups used to turn validators on and off for updates vs creates (that way I can use the same beans and not have to
duplicate validation logic).  Thank you Spring for @Validated annotation!

## Other Web Tier Stuff
A configuration controller will display the build time and scm revision.  It pulls these from the build.properties file in src/main/resources/META-INF/build.
That property file is read in in mvc.xml in META-INF/spring, and is installed in the WebConfig.java file. A static resource (js, image, css) handler is installed as the 
WebassetHandlingFilter, to intercept the request before they get routed into the Dispatcher Servlet or Spring Security (although I'm noticing I seem to have
the filters in the wrong order in the DispatcherServletInitializer; I'll have to investigate).

## Security
Based on Spring Security, the main security here for the web services is based on a custom HTTP header.  In my experience, this is sometimes easier than
cookies for non-browser HTTP clients.  I adapted the RememberMe functionality in Spring Security, because it seemed like the token was just what I needed.
However, I changed it to use the User's id instead of username (email) for a little tighter security.  Details are in the service.security package and in 
security.xml.

## Monitoring
Only a clown puts something in production with no visibility into its internals.  In the monitoring package, a performance monitor interceptor gathers detailed
information on method-level execution statistics. These are exposed through JMX as see near the bottom of application-context.xml.  One thing that makes me
uneasy is that all the performance monitors are stored in a single map in the MonitoringAspect; this single data structure could become a point of 
contention if load becomes really high (even though it's read-only after the startup transient, and ConcurrentHashMap is a totally solid class).  You can 
see a nice HTML report in JMX (you can use my JMX client, referenced in the POM, and also found on my GitHub account).

## Persistence
Almost an afterthought with Spring Data JPA.  Hibernate is the provider.  There's a BaseEntity for persistent objects to inherit from for basic auditing
fields.

## JRebel
There's a JRebel XML file.  If you don't use JRebel you should.  Imagine programming for hours without restarting your server.  It can be yours!

## Test Framework
I've been converted from a worshipper of unit tests to a true believer in functional tests.  There's a test suite that tests all the web services.  Just like
you have the Page Object pattern in Selenium testing a web page (a Page Object to represent the page's data and operations, and a test to test that page),
I have a Service Object pattern.  There should be a test for every service endpoint, and a corresponding Service Object.  The Service Object knows how
to access the service and understand the result.  I'm using the wonderful Groovy Httpbuilder library, it's a easy as jQuery. There's
a BaseTestClass for the tests to inherit from, which has some nice convenience helpers.  There also a BaseWebService that has the Httpbuilder code. You send
a map of values and the builder turns it into a JSON POST, and then parses the JSON returned.  Each test has one closure with prettyPrint() in it, so
that in a successful run you get a printout in the console of the JSON returned by all the services.  This is copied into misc/webservices/jsonOutput.txt as
documentation.

## Branches
- master: simple Spring project skeleton
- stability-example: project adapted to use the Stability Utils project https://github.com/kenwdelong/stability-utils