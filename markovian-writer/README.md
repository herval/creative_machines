# Markovian Writer

## Configuring the bot locally

Create a .env file with the following fields:
```
TWITTER_CONSUMER_KEY=<your consumer key>
TWITTER_OAUTH_TOKEN=<your auth token>
TWITTER_OAUTH_TOKEN_SECRET=<your auth secret>
TWITTER_CONSUMER_SECRET=<your consumer secret>
```

## Running with Gradle

`source .env && gradle markovian-writer:run`


## Running as a Docker container

```
gradle markovian-writer:distDocker
docker run <image id>
```

## Running on a resin.io device

[Setup the device first](https://resinos.io/docs/raspberry-pi2/gettingstarted/)

You can check if it's working properly by SSH'ing into it:

```
ssh root@resin.local -p22222
rdt ssh --host
```

Deploy the bot with:
```
gradle markovian-writer:distDocker
rdt push --source markovian-writer/build/docker/
```