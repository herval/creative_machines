# Haikuzao

## TO DO

Currently trained network generates reasonably structured Haikus, but still invents a lot of words.


## Running the trainer

```
gradle haikuzao:shadowJar
WORK_DIR=haikuzao/src/main/resources java -cp haikuzao/build/libs/haikuzao-1.0-SNAPSHOT-all.jar hervalicious.haikuzao.BotTrainer
```


## Running the bot

```
gradle haikuzao:shadowJar
TWITTER_CONSUMER_TOKEN=token TWITTER_CONSUMER_SECRET=secret TWITTER_ACCESS_TOKEN=token TWITTER_ACCESS_TOKEN_SECRET=secret WORK_DIR=haikuzao/src/main/resources java -cp haikuzao/build/libs/haikuzao-1.0-SNAPSHOT-all.jar hervalicious.haikuzao.BotRunner
```


## Training on AWS

We can take advantage of AWS [GPU instances](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/accelerated-computing-instances.html) to train the networks faster:


- Uncomment the CUDA dependencies on `build.gradle`

- Train on EC2

```
./train_aws.rb haikuzao/build/libs/haikuzao-1.0-SNAPSHOT-all.jar hervalicious.haikuzao.BotTrainer haikuzao/src/main/resources ec2_host
```