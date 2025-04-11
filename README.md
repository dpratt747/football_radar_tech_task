# To run the application:

### To run the application:
```bash
sbt "runMain runner.Main"
```

```bash
sbt "runMain runner.Main <argument 1> <argument 2>" 
```

### To run the tests:
```bash
sbt test
```

```bash
sbt 'integration / test'
```

---
## Related to the following warnings:
```text
WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::arrayBaseOffset has been called by net.openhft.hashing.UnsafeAccess (file:/Users/davidpratt/.sbt/boot/scala-2.12.20/org.scala-sbt/sbt/1.10.10/zero-allocation-hashing-0.16.jar)
WARNING: Please consider reporting this to the maintainers of class net.openhft.hashing.UnsafeAccess
WARNING: sun.misc.Unsafe::arrayBaseOffset will be removed in a future release
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::load has been called by org.scalasbt.ipcsocket.NativeLoader in an unnamed module (file:/Users/davidpratt/.sbt/boot/scala-2.12.20/org.scala-sbt/sbt/1.10.10/ipcsocket-1.6.3.jar)
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled
```

https://github.com/sbt/sbt/issues/8073
https://github.com/sbt/sbt/issues/7634