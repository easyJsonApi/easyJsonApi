#!/usr/bin/env bash
echo "Checking the current branch..."
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	echo "The current branch is: master"
	echo "Run the openssl to decrypt the key..."
	openssl aes-256-cbc -K $encrypted_bde7063f2b13_key -iv $encrypted_bde7063f2b13_iv -in scripts/codesigning.asc.enc -out scripts/codesigning.asc -d
	gpg --fast-import signingkey.asc
	echo "Finish run the openssl command"
fi
