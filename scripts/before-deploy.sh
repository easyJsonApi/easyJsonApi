#!/usr/bin/env bash
echo "Checking the current branch..."
#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	echo "The current branch is: master"
	echo "Run the openssl to decrypt the key..."
	openssl aes-256-cbc -K $encrypted_79f5965e4616_key -iv $encrypted_79f5965e4616_iv -in codesigning.asc.enc -out signingkey.asc -d
	gpg --fast-import signingkey.asc
	echo "Finish run the openssl command"
#fi
