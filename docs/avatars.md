# Attendee avatars

Calendar uses the `com.otus:avatar` library from otus-meta for attendee avatars. The service, its
caching, website-icon rules, and privacy limits are documented in
[otus-meta's avatar guide](https://github.com/reazndev/otus-meta/blob/main/docs/avatar.md). The
design system's `Avatar` element draws the result, and `rememberAvatarImage` decodes image bytes;
both come from `com.otus:designsystem`. This guide covers what Calendar supplies.

## Calendar integration

Calendar accounts and attendee screens are not implemented yet, so Calendar does not create an
`AvatarService`. When attendee screens are added, app wiring creates one service per process and
passes Calendar's own app-private login IDs as `accountKey`. Calendar's contact source, avatar
store, and allowed remote requests are chosen in that wiring; presentation resolves avatars before
passing bitmap or initial values to the design-system `Avatar`. The shared module keeps the
OkHttp and Darwin Ktor engines that `HttpAvatarImageLoader` uses when it is created without an
explicit engine.

## Public Suffix List

`PublicSuffixDomainResolver` takes an injected suspend loader for the rules, so each app bundles
the list. Calendar ships `files/public_suffix_list.dat` in its Compose resources, with the MPL 2.0
notice in `public_suffix_list_MPL-2.0.txt`. No Calendar code reads it yet; the avatar wiring that
attendee screens need will read it the same way Mail does:

```kotlin
val domains = PublicSuffixDomainResolver {
    Res.readBytes("files/public_suffix_list.dat").decodeToString()
}
```

The list snapshot is `2026-10-01_23-02-52_UTC`, upstream commit
`6cd82aff889e3d64e5e03bc5c1f43da1934a960a`, from
[publicsuffix.org](https://publicsuffix.org/list/public_suffix_list.dat). Source SHA256 is
`e0fe072d26b0536525badea237953ff451c9f8e64c9d02c6daa81a4491d2fc66`.
Rules are lowercased and converted label by label to ASCII IDNA while retaining wildcard and
exception markers. Original comments and the MPL 2.0 notice remain. The bundled files match
Mail's; refresh both apps' copies together when updating this data.

## Validation

The service, website-icon, loader, and Public Suffix List tests run in otus-meta, and the design
system's decoding tests run there too. Calendar has no attendee integration to test yet.
