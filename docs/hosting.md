# Hosting and CI

[Otus Calendar on GitHub](https://github.com/otus-foss/calendar) hosts development, pull requests,
issues, and CI. Core's [hosting guide](https://github.com/otus-foss/core/blob/main/docs/hosting.md) describes the workflows, when they run,
the shared actions, and the runners for every Otus repository.

New clones go next to core, which Calendar builds from source:

```sh
git clone git@github.com:otus-foss/core.git
git clone git@github.com:otus-foss/calendar.git
cd calendar
git config --local core.hooksPath .githooks
```

CI checks Calendar out to `calendar` and core beside it, on the Core branch with this
branch's name when there is one, else `main`. core is public, so the checkout needs no key.
