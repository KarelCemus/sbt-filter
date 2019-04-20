<h1 align="center">SBT Filter plugin filters out files not to be published</h1>

<p align="center">
  <a href='https://travis-ci.org/KarelCemus/sbt-filter'><img src='https://travis-ci.org/KarelCemus/sbt-filter.svg?branch=master'></a>
</p>


Member of [SbtWeb](https://github.com/sbt/sbt-web) plugin pipeline to filter out assets. The intended use is **to remove** assets
from the output package, e.g., `*.map`, original `*.less`, unminified files, etc.

This plugin may be used to remove any intermediate or unnecessary assets from the product build of your project.
Only assets directly owned by the project can be filtered.

Add the plugin to the `project/plugins.sbt` of your project:

```scala
addSbtPlugin("com.github.karelcemus" % "sbt-filter" % "1.1.0")
```

Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

```scala
lazy val root = (project in file(".")).enablePlugins(SbtWeb)
```

The plugin must then be added as a new stage in the asset pipeline.

```scala
import com.github.karelcemus.filter.Import._

// either
pipelineStages := Seq( filter )

// or
pipelineStages :=  Seq( rjs, filter, digest, gzip ) 
```

## File Filters

By default, the plugin allows all assets to pass through the pipeline, i.e., `includeFilter` is set to `AllPassFilter`
and `excludeFilter` to `NothingFilter`. The assets must be included in `includeFilter` **and not** in `excludeFilter`,
otherwise they are dropped. The plugin is implemented as this: `assets.filter( includeFilter ).filterNot( excludeFilter )`.
This gives you wide range of opportunities to configure, which assets to let through.

For example, to remove the original CoffeeScript and LESS sources from the assets build:

```scala
excludeFilter in filter := "*.coffee" || "*.less"
```

Alternatively, you may wish to remove all JavaScript files except the concatenated and minified main.js produce by
the RequireJS plugin:

```scala
excludeFilter in filter := "*.js" - "main.min.js"
```

Another approach is to **whitelist** all assets to be passed through, but the whitelist must **be exhaustive**.
To do this use `includeFilter in filter`. You may whitelist more files and then some of them blacklist through 
`excludeFilter`. In consequence, the filter plugin lets pass through all whitelisted assets, which are not blacklisted.

## PatternFileFilter

SBT comes with several file filter, but does not provide convenient implicits for filtering files by regular expressions.
This plugin also provides a filter converting any regex into `FileFilter`. The filter matches the pattern against file's
**absolute path**.

```scala
import com.github.karelcemus.filter.Import._

excludeFilter in filter := ".*/images/sprites/.*".r
```

## Acknowledgement

This implementation is inspired by [sbt-filter](https://github.com/rgcottrell/sbt-filter) by @rgcottrell, 
whose implementation did not work for me.
