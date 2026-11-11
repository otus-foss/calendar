![Otus Calendar](.github/banner.png)

Otus Calendar will be the calendar app of the Otus suite for Android and iOS, using JMAP for
Calendars on the server you choose. It shares its design system and JMAP client with
[Otus Mail](https://github.com/otus-foss/mail) through [Otus Core](https://github.com/otus-foss/core).

> [!WARNING]
> Otus Calendar is still in development. Expect bugs, missing features, and changes that break your
> settings between builds.

## Install

There are no builds yet. To try the current scaffold, [build it from source](#build-from-source).

## Build from source

Otus Calendar builds Otus Core from a checkout next to this one. With JDK 21 and the Android SDK
installed, run:

```sh
git clone https://github.com/otus-foss/core
git clone https://github.com/otus-foss/calendar
cd calendar
./gradlew :androidApp:assembleDebug
```

On a Mac, open `iosApp/iosApp.xcodeproj` in Xcode to run it on an iPhone.

## Contributing

Read the [developer guide](docs/README.md) before you change code.

## License

GPL-3.0-or-later, with an [additional permission](LICENSE-EXCEPTION.md) that allows app-store
distribution while the source stays public. See [LICENSE](LICENSE).
