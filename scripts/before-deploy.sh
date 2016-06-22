#!/usr/bin/env bash
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_bde7063f2b13_key -iv $encrypted_bde7063f2b13_iv -in codesigning.asc.enc -out codesigning.asc -d
	gpg --fast-import signingkey.asc
fi
