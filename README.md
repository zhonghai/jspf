
Click to star project Starred by 134 usersimport from https://code.google.com/p/jspf/
Project feeds
Code license
New BSD License
Labels
java, ioc, container, plugin, manager, simple, framework, rmi, xmlrpc, zeroconf
Members
r.biedert, nicolas.delsaux
2 committers
Featured
Downloads
 jspf-1.0.2.bin.zip
Show all »
Links
External links
API Documentation
jCores
RipeRMI (RMI for Java)
Groups
Help, Support and Discussions
What JSPF does ...

The Java Simple Plugin Framework was built to reduce development time while increasing code maintainability of small to medium sized projects.

Completely hides implementation details of components. Only use their interfaces.
Components may be loaded with only two(!) lines of code
Heavily annotation based: @PluginImplementation, @InjectPlugin, @PluginLoaded, @Timer and @Thread, ...
Through usage of generics it is usually type safe.
Additional plugins to export other plugins by JavaScript, JSON, LipeRMI, XMLRPC, XMLRPC Delight or ERMI. Plugins may be discovered on the local net using ZeroConf.
(See the Usage Guide, API , FAQ, Introduction-Video (see video below) for more information). A new discussion group for help and support has also been created.



The only things that changed since the creation of the video are that .toURL() is not required anymore and that instead of File("bin/"), you could start with ClassURI.CLASSPATH right away; see the short example below). A high quality version of the video can be downloaded here, or watched on youtube directly (click video).



What is new in this version (1.0.2, May 2011)

Updated RC1 -> RC3: Workaround for large classpaths. See Discussion Groups. 

Updated RC3 -> RC4: Added warnings for some common programming mistakes. 

Updated RC4 -> Final: Minor bug fixes. Added ClassURI.CLASSPATH

Updated Final -> 1.0.1: Fixed doubled InformationBroker bug

Updated 1.0.1 -> 1.0.2: ClassURI.CLASSPATH now also works when JSPF is loaded dynamically (like in application servers)

Complete overhaul of the InformationBroker (looks good now :-)
Added support for multiplugins (plugins consisting of several JARs sharing the same classloader, see the FAQ)
jCores added (see http://jcores.net)
Added initial version of 'classpath://x.*' ... patterns (see Issue #21)
Improved debugging facilities (see OptionReportAfter)
Dropped some less frequently used features (e.g., supervision, pluglets, ...)
Autodiscovery is an abomination ... redone the fast, local lookup again.
Reworked plugin manager internals (still not finished)
Changed JSON URL generation again (and it will have to be replaced once more in the future, sorry for that)
Fixed another LipeRMI bug concerning methods of superclasses
Added warning when getPlugin() is being misused.
Fixed console output
Some minor bug fixes
See the version history for changes in the past.



A short example

The following lines demonstrate how easy it is to load existing plugins. All .JAR files inside the given directory will be examined for contained plugins which will be loaded afterwards and are automatically started. No configuration files are required, nothing else has to be done.

PluginManager pm = PluginManagerFactory.createPluginManager();

// and then one or more of these ...

pm.addPluginsFrom(ClassURI.CLASSPATH);
pm.addPluginsFrom(ClassURI.PLUGIN(PluginImpl.class));
pm.addPluginsFrom(new File("plugins/").toURI());
pm.addPluginsFrom(new File("plugins/plugin.jar").toURI());
pm.addPluginsFrom(new URI("http://jspf.googlecode.com/files/coolplugin.jar"));
Creating a new plugin is equally straightforward. After an interface has been designed (which only has to extend Plugin) the rest can be done by a simple annotation. The next example shows a plugin implementation that could be loaded by JSPF, and also here: no XML- or whatsoever-files have to be created to make this work.

/**
 * CoolPlugin may be an (almost) arbitrary interface, in only has to **extend Plugin**.
 */
@PluginImplementation
public class CoolPluginImpl implements CoolPlugin {

    public String provideData() {
        return "Hello World";
    }

}
Our last two snippets show how plugins can be obtained from outside, and the inside of plugins. Notice the type-safety:

CoolPlugin cool = pm.getPlugin(CoolPlugin.class);
Or, from inside of plugins:

@InjectPlugin
public CoolPlugin cool;


When to use it

If you

are, for example, a researcher and want to develop a prototype quickly
intend to change implementations frequently but want to keep your code clean
are coding some software where you expect plugins to be loaded using some kind of easy IoC
think about reusing components in other prototypes
then you might want to give it a try.



Functionality Checklist

(aka the unbiased JSPF-is-great feature matrix)

Below you find a brief overview of the most important features JSPF has to offer. This list is not complete, and the framework offers various other goodies here and there, like flexible options, easy configuration support and some more.

Feature	JSPF
Can load ... plugins from JAR files	Yes
... multiple plugins from a directory	Yes
... automatically all plugins in classpath	Yes
... plugins over HTTP	Yes (1)
Threadsafe	Yes
Typesafe	Yes
Dependency Injection	Yes
Simple, XML free configuration	Yes
Heavily annotation based	Yes
Supports caching	Yes
Plugins can be isolated using a separate ClassLoader	Yes (2)
Official support to export plugins over ... ERMI	Yes
... LipeRMI	Yes (3)
... JSON (easily export Plugins to web pages!)	Yes
... XMLRPC	Yes
Transparent and easy network callbacks	Yes (w. LipeRMI)
Remote plugins may be discovered automatically	Yes
Requires Java version	>=6.0
Supported Platforms	Windows, Mac, Linux
Works in applets	Yes (4)
Time to get started	5 minutes (6)
License	Free (beer and speech)
Proper end user documentation	It's improving :-)
(1) Works, but unsafe and not recommended.

(2) Only if the plugins are packed into a self contained JAR.

(3) We even use a greatly enhanced version of LipeRMI with fewer bugs and more featues.

(4) Applets have to be signed, does not work with classpath autodetection 

(6) If you watch the introduction video and have profound Java experience.



Known Users

German Research Center for Artificial Intelligence (various projects)
Text 2.0 Project (official website)


Help & Feedback

We know the documentation is in a bad shape. To receive help, please use the discussion group. Thanks.



History

This project was created by Ralf Biedert at the DFKI. Nicolas Delsaux started the hosting on code.google.com and added some features. Thomas Lottermann contributed to various plugins.
