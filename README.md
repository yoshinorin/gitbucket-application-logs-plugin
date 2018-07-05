# gitbucket-logs-plugin

# Features

This plugin provides GitBucket's log for [GitBucket](//github.com/gitbucket/gitbucket) Administrator.

* LogBack settings
* GitBucket's log

# Images

TODO

# Download & Installation

1. Download plugin jar file from [the release page](//github.com/YoshinoriN/gitbucket-logs-plugin/releases).
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

The built package will be created at `/target/scala-2.12/gitbucket-logs-plugin_2.12-{plugin-version}.jar`

# License

This project is under the Apache License, Version 2.0 License. See the [LICENSE](./LICENSE) file for the full license text.
