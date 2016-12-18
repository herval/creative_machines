# Android Things Bot

This module runs a bot of your choice as an [Android Things](https://developer.android.com/things/sdk/index.html) bot. It's basically BYOIOT.

# Pre-requisites

- Android Things compatible board
- Android Studio 2.2+
- Android SDK


# Build and install

```bash
./gradlew installDebug
adb shell am start us.hervalicio.iot/.MainActivity
```