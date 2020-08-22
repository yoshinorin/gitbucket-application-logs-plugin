# gitbucket-application-logs-plugin

[![](https://travis-ci.org/YoshinoriN/gitbucket-application-logs-plugin.svg?branch=master)](https://travis-ci.org/YoshinoriN/gitbucket-application-logs-plugin) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/64706d3697aa4548b043bae8ea90cfb6)](https://www.codacy.com/app/YoshinoriN/gitbucket-application-logs-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YoshinoriN/gitbucket-application-logs-plugin&amp;utm_campaign=Badge_Grade) [![Coverage Status](https://coveralls.io/repos/github/YoshinoriN/gitbucket-application-logs-plugin/badge.svg?branch=master)](https://coveralls.io/github/YoshinoriN/gitbucket-application-logs-plugin?branch=master)

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

![](https://raw.githubusercontent.com/YoshinoriN/gitbucket-application-logs-plugin/master/doc/images/config.png)
![](https://raw.githubusercontent.com/YoshinoriN/gitbucket-application-logs-plugin/master/doc/images/logs.png)
![](https://raw.githubusercontent.com/YoshinoriN/gitbucket-application-logs-plugin/master/doc/images/viewer.png)

# Download & Installation

1. Download plugin jar file from [the release page](//github.com/YoshinoriN/gitbucket-application-logs-plugin/releases).
2. Put plugin jar file into `GITBUCKET_HOME/plugins` and restart GitBucket.

# UI Usage

Goto the `System Administration` menu, you can see `Application Logs` section.

# Compatibility with GitBucket

|Plugin version|GitBucket version|
|:-------------:|:-------:|
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
