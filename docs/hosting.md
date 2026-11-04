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
runners. The `ios` job runs on GitHub's `macos-15` runners with Xcode 26.3, because Ubicloud has
no macOS runners. Like the Linux jobs, it runs once per pull request that is ready for review.

`actionlint` lints workflows in pull requests that change `.github/`. `issue link` warns when a
pull request closes no issue, and `issue report` lists each Monday the open issues that merged pull
requests name, in an issue called "Open issues with merged work". Dependabot opens one grouped
pull request a week for GitHub Actions and one for Gradle, leaving the pinned Otus libraries alone.

Calendar downloads its pinned Meta release from GitHub Packages at
`https://maven.pkg.github.com/reazndev/otus-meta`. CI reads a GitHub token with `read:packages`
from the `OTUS_META_PACKAGES_TOKEN` secret. See [shared libraries](shared-libraries.md) for local
credentials, and the
[suite hosting guide](https://github.com/reazndev/otus-meta/blob/main/docs/hosting.md) for package
publishing.
