# gitbucket-application-logs-plugin

[![build](https://github.com/yoshinorin/gitbucket-application-logs-plugin/workflows/build/badge.svg?branch=master)](https://github.com/yoshinorin/gitbucket-application-logs-plugin/actions) [![Coverage Status](https://coveralls.io/repos/github/yoshinorin/gitbucket-application-logs-plugin/badge.svg?branch=master)](https://coveralls.io/github/yoshinorin/gitbucket-application-logs-plugin?branch=master)

# Precondition

Please enable [logging using by Logback](https://github.com/gitbucket/gitbucket/wiki/Tracing-and-logging) before use this plugin.

# Features

[GitBucket log feature](https://github.com/gitbucket/gitbucket/wiki/Tracing-and-logging) assistance plugin for Administrator.

* Display LogBack configuration
* Reload LogBack configuration
* GitBucket logs viewer
    * Selectable Sort type (ascending or descending from tail of the log)
    * Specify the number of display less than 10000
* Download GitBucket log

# Images

![](https://raw.githubusercontent.com/yoshinorin/gitbucket-application-logs-plugin/master/doc/images/config.png)
![](https://raw.githubusercontent.com/yoshinorin/gitbucket-application-logs-plugin/master/doc/images/logs.png)
![](https://raw.githubusercontent.com/yoshinorin/gitbucket-application-logs-plugin/master/doc/images/viewer.png)

# Download & Installation

1. Download plugin jar file from [the release page](//github.com/yoshinorin/gitbucket-application-logs-plugin/releases).
2. Put plugin jar file into `GITBUCKET_HOME/plugins` and restart GitBucket.

# UI Usage

Goto the `System Administration` menu, you can see `Application Logs` section.

# Compatibility with GitBucket

|Plugin version|GitBucket version|
|:-------------:|:-------:|
|3.2.x|4.37.x - |
|3.1.x|4.35.0 - 4.36.0|
|3.0.x|4.35.0 - 4.36.0|
|2.0.0|4.32.0 - 4.34.0|
|1.0.0|4.25.0 - 4.31.x|
|0.2.0|4.25.0 - 4.27.0|
|0.1.0|4.25.0 - 4.27.0|

# Build from source

```sh
$ sbt assembly
```

The built package will be created at `/target/scala-2.13/gitbucket-application-logs-{plugin-version}.jar`

# Test

Run test.

```
$ sbt test
```

Generate coverage report.

```
$ sbt coverageReport

or

$ sbt clean coverage test coverageReport
```

# License

This project is under the Apache License, Version 2.0 License. See the [LICENSE](./LICENSE) file for the full license text.
