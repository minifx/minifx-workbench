[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fc1500db4cb34e398616c2085a509612)](https://app.codacy.com/app/minifx-developers/minifx-workbench?utm_source=github.com&utm_medium=referral&utm_content=minifx/minifx-workbench&utm_campaign=Badge_Grade_Dashboard)
[![GitHub release](https://img.shields.io/github/release/minifx/minifx-workbench.svg)](https://github.com/minifx/minifx-workbench/releases/)
[![Build Status](https://travis-ci.com/minifx/minifx-workbench.svg?branch=master)](https://travis-ci.com/minifx/minifx-workbench)
[![License](https://img.shields.io/github/license/minifx/minifx-workbench.svg)](https://opensource.org/licenses/Apache-2.0) 
[![codecov](https://codecov.io/gh/minifx/minifx-workbench/branch/master/graph/badge.svg)](https://codecov.io/gh/minifx/minifx-workbench)


# MiniFx Workbench

We believe that organizing java applications inside a dependency-injection container (like spring) is (almost) always beneficial. 
Even when writing GUIs. Doing so makes such applications very modular. Further, we wanted to organize our applications 
in a workbench manner (e.g. like eclipse does), but without a big overhead of osgi or similar. Based on these two premises, minifx-workbench was born: 
It is based on spring and additional custom annotations.

## Gradle

To add a dependency on minifx-workbench in gradle, add the following to your ```build.gradle``` file:

```gradle
dependencies {
    compile "org.minifx:minifx-workbench:x.x.x"
}
```

```x.x.x``` corresponds to the latest version, which can be found at the top of this page.

## Maven

To add a dependency on minifx-workbench in maven, add the following to your ```pom.xml``` file:

```xml
<dependency>
    <groupId>org.minifx</groupId>
    <artifactId>minifx-workbench</artifactId>
    <version>x.x.x</version>
</dependency>
```

```x.x.x``` corresponds to the latest version, which can be found at the top of this page.

Further descriptions and examples can be found on the [minifx web page](https://minifx.org).

