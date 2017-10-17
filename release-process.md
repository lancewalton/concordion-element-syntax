Publish the scaladoc
----
Publish the scaladoc to gh-pages:

```
$ sbt "doc make-site ghpages-push-site"
```

SNAPSHOT versions have docs generated under /api/master, but release versions are under /api/treelog-x.y.z

Publish Snapshots to sonatype
----
  For snapshots make sure the version number has a -SNAPSHOT suffix. To release to staging, remove the suffix.
  In both cases use:
  
```
$ sbt publish-signed
```

Release to sonatype
----
  1. (See section 8a https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-7b.StageExistingArtifacts)
  1. Do a staging release by removing the -SNAPSHOT in the version number and publish-signed
  1. Login to the Nexus UI at https://oss.sonatype.org/
  1. Under 'Build Promotion' select Staging Repositories.
  1. Find our release item and click it
  1. Click the Close button (next to the Refresh button)
  1. Wait ...
  1. Keep checking the Activity tab to see if any rules failed.
  1. If that succeeds, select the release again and click 'Release'
  1. Wait ...
  1. Keep checking the Activity tab to see if there are any problems
  1. Look for the release in https://oss.sonatype.org/content/repositories/releases/com/casualmiracles/concordion-element-syntax_2.12
  1. Bump the release version up with -SNAPSHOT extension

