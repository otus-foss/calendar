# Hosting and CI

[Otus Calendar on GitHub](https://github.com/reazndev/otus-calendar) hosts development, pull
requests, issues, and CI. The repository used Forgejo from 2026-10-08 to 2026-10-11; every
Forgejo branch was pushed to GitHub before the switch. Do not push new work to Forgejo.

New clones use:

```sh
git clone git@github.com:reazndev/otus-calendar.git
cd otus-calendar
git config --local core.hooksPath .githooks
```

`.github/workflows` holds every workflow. Linux jobs run on Ubicloud's `ubicloud-standard-2`
runners. The `ios` job is paused with `if: false`; delete that condition to run it again. The
job needs macOS 15 with Xcode 26.3. Calendar does not consume Meta packages yet.

See the [suite hosting guide](https://github.com/reazndev/otus-meta/blob/main/docs/hosting.md)
for the switch from Forgejo.
