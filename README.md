# gitbucket-application-logs-plugin

[![](https://travis-ci.org/YoshinoriN/gitbucket-application-logs-plugin.svg?branch=master)](https://travis-ci.org/YoshinoriN/gitbucket-monitoring-plugin) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/7b56c5e541a34493a9ab05c42fb3035a)](https://www.codacy.com/app/YoshinoriN/gitbucket-application-logs-plugin?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=YoshinoriN/gitbucket-application-logs-plugin&amp;utm_campaign=Badge_Grade)

# Features

This plugin provides LogBack's settings and GitBucket's log for [GitBucket](//github.com/gitbucket/gitbucket) Administrator.

* LogBack settings
* GitBucket's log

# Images

TODO

# Download & Installation

1. Download plugin jar file from [the release page](//github.com/YoshinoriN/gitbucket-application-logs-plugin/releases).
2. Put plugin jar file into `GITBUCKET_HOME/plugins` and restart GitBucket.

# UI Usage

Goto the `System Administration` menu, you can see `Application Logs` section.

# Compatibility with GitBucket

|Plugin version|GitBucket version|
|:-------------:|:-------:|
|0.1.0|4.25 - |

# Build from source

```sh
sbt package
```

The built package will be created at `/target/scala-2.12/gitbucket-application-logs-plugin_2.12-{plugin-version}.jar`

# License

This project is under the Apache License, Version 2.0 License. See the [LICENSE](./LICENSE) file for the full license text.
