# Hosting and CI

[Otus Calendar on GitHub](https://github.com/otus-foss/otus-calendar) hosts development, pull requests,
issues, and CI. Core's [hosting guide](https://github.com/otus-foss/otus-core/blob/main/docs/hosting.md) describes the workflows, when they run,
the shared actions, and the runners for every Otus repository.

New clones go next to otus-core, which Calendar builds from source:

```sh
git clone git@github.com:otus-foss/otus-core.git
git clone git@github.com:otus-foss/otus-calendar.git
cd otus-calendar
git config --local core.hooksPath .githooks
```

CI checks Calendar out to `otus-calendar` and otus-core beside it, on the Core branch with this
branch's name when there is one, else `main`. otus-core is public, so the checkout needs no key.
