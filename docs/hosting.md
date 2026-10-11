# Hosting and CI

[Otus Calendar on GitHub](https://github.com/reazndev/otus-calendar) hosts development, pull
requests, issues, and CI.

New clones use:

```sh
git clone git@github.com:reazndev/otus-calendar.git
cd otus-calendar
git config --local core.hooksPath .githooks
```

`.github/workflows` holds every workflow. Linux jobs run on Ubicloud's `ubicloud-standard-2`
runners. The `ios` job is paused with `if: false`; delete that condition to run it again. The
job needs macOS 15 with Xcode 26.3. Calendar does not consume Meta packages yet.
