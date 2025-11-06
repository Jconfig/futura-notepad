# Futura Notepad
Advanced notepad with Compose, Room+FTS, OCR, audio notes, drawing pad, local encryption, widget. Built and released via GitHub Actions. Package: `xyz.joydeb.futura_notepad`.

## CI
- Every push builds Debug APK and Release AAB as artifacts.

## Release
- Tag `vX.Y.Z` to build and upload to Google Play internal track using Gradle Play Publisher.

## Secrets required for release
- SIGNING_KEYSTORE_BASE64
- SIGNING_STORE_PASSWORD
- SIGNING_KEY_ALIAS
- SIGNING_KEY_PASSWORD
- PLAY_SERVICE_JSON
