# Hosting and CI

[Otus Calendar on GitHub](https://github.com/reazndev/otus-calendar) hosts development, pull requests,
issues, and CI. Meta's [hosting guide](https://github.com/reazndev/otus-meta/blob/main/docs/hosting.md) describes the workflows, when they run,
the shared actions, and the runners for every Otus repository.

New clones go next to otus-meta, which Calendar builds from source:

```sh
git clone git@github.com:reazndev/otus-meta.git
git clone git@github.com:reazndev/otus-calendar.git
cd otus-calendar
git config --local core.hooksPath .githooks
```

CI checks Calendar out to `otus-calendar` and otus-meta beside it, on the Meta branch with this
branch's name when there is one, else `main`. It reads otus-meta with the read-only
`OTUS_META_DEPLOY_KEY` deploy key, stored as an Actions secret in this repository.
