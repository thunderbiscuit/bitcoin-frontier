# Readme
This repository aims to offer a testbed for the development of Rust language bindings on mobile for new or experimental Rust libraries. It is not a goal of this project to provide a production-ready library or wallet. Rather, it is a place to experiment with new ideas and to provide an easy way to test new ideas developed in Rust onto mobile devices.

## bitcoin-frontier
The main conduit for this goal is a library called bitcoin-frontier. We use the [uniffi-rs](https://github.com/mozilla/uniffi-rs) library to create language bindings for [Android](./bitcoin-frontier-android).

The library is not available on Maven Central; if you want to use it, you'll need to build it yourself.

## When will it be ready for prod?
It won't. If you find something in bitcoin-frontier you like and think should make it into a production-ready application, you should try to find (or build) a long-term home for it! Different things belong in different layers of the Rust bitcoin ecosystem (and it's bindings), and there might be good places for them to get the proper review and maintenance such features might require.

## Frontier Wallet
The [Frontier Wallet](./frontier-app) is a sample Android app that uses the bitcoin-frontier library. It is a fork of [Padawan Wallet](https://github.com/thunderbiscuit/padawan-wallet). I don't have time to build a brand new, fresh UI, and this is a good starting point.

Similar to the bitcoin-frontier library, the app is not available on the Play Store.
