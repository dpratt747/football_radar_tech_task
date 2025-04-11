# Unit Tests

To run Unit tests: ```sbt test```
```bash
sbt 'testOnly *<class name> -- -z "<test name>"'
```
e.g.

```bash
sbt 'testOnly *ExampleSpec -- -z "should generate an apple string"'
```