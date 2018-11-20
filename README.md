

# MiniFx Workbench

We believe that organizing java applications in spring contexts is beneficial most of the times. 
Even when writing GUIs. Doing so makes such applications very modular. Further, we wanted to have an 
easy (!) way to organize our applications in a workbench manner (e.g. like eclipse does), 
but without a big overhead of osgi or similar. Based on these two premises, minifx-workbench was born.

## Gradle

To add a dependency on minifx-workbench in gradle, add the following to your ```build.gradle``` file:

```gradle
dependencies {
    testCompile "org.minifx:minifx-workbench:x.x.x"
}
```

```x.x.x``` corresponds to the latest version, which can be found at the top of this page.

## Maven

```xml
<dependency>
    <groupId>org.minifx</groupId>
    <artifactId>minifx-workbench</artifactId>
    <version>x.x.x</version>
</dependency>
```

```x.x.x``` corresponds to the latest version, which can be found at the top of this page.