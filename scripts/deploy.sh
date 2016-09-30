#!/usr/bin/env bash
echo "Checking the current branch..."
if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    echo "The current branch is: master"
    echo "Run maven deploy parameter using sign and build-extras profiles..."
    mvn deploy -P sign,build-extras --settings setting-maven.xml -DskipTests=true
fi
