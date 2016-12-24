#!/usr/bin/env ruby


if ARGV.size < 4
    puts "Usage: ./train_aws.rb <jar> <main class> <resources folder> <ec2 node>"
    exit(1)
end

jar = ARGV[0]
trainer_class = ARGV[1]
data_folder = ARGV[2]
ec2_host = ARGV[3]


puts "Uploading trainer + data..."
`scp #{jar} #{ec2_host}:~/trainer.jar`
`scp #{data_folder}/* #{ec2_host}:~`


puts "Running training..."

`ssh #{ec2_host} 'WORK_DIR=~ java -cp trainer.jar #{trainer_class}'`

puts "Downloading results..."

`scp #{ec2_host}:*.zip #{data_folder}`

puts "All done!"