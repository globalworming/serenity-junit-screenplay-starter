run image comparison actions once with `ashot.image.comparison.create.snapshots=true` e.g. in your `serenity.properties` file

this creates snapshots in your `target/test-classes/com/example/screenplay/action/image/snapshots`

move snapshots contents to `scr/test/resources/com/example/screenplay/action/image/snapshots

set `ashot.image.comparison.create.snapshots=false` to compare to the snapshots

failing tests will create diff files in  `target/test-classes/com/example/screenplay/question/image/diffs` and will also be attached to the serenity report as additional step evidence
