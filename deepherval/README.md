# DeepHerval

A RNN generating Tweets based on everything @herval said on Twitter since 2007.


## Seeding the network

- Download your [Tweet archive](https://support.twitter.com/articles/20170160) and put the tweets.csv file on resources


## Training on AWS w/ GPUs

```
./train_aws.rb deepherval/build/libs/deepherval-1.0-SNAPSHOT-all.jar hervalicious.deepherval.BotTrainer deepherval/src/main/resources ec2_host your_pem.pem
```