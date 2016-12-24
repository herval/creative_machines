#!/usr/bin/env ruby
#
# The AWS instance must have GPUs
#
# Setup it with:
#
# > cd ~
# > wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jre-8u60-linux-x64.rpm"
# > sudo yum localinstall jre-8u60-linux-x64.rpm -y
#

if ARGV.size < 4
    puts "Usage: ./train_aws.rb <jar> <main class> <resources folder> <ec2 node> <aws pem>"
    exit(1)
end

jar = ARGV[0]
trainer_class = ARGV[1]
data_folder = ARGV[2]
ec2_host = ARGV[3]
aws_pem = ARGV[4]

project = jar.split("/")[0]

puts "Building..."
`gradle #{project}:shadowJar -Pdriver=cuda`


puts "Uploading trainer + data..."
`scp -i #{aws_pem} #{jar} ec2-user@#{ec2_host}:~/trainer.jar`
`scp -i #{aws_pem} #{data_folder}/* ec2-user@#{ec2_host}:~`


puts "Running training..."

cmd = "LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/cuda/lib64 PATH=$PATH:/usr/local/cuda/bin/ WORK_DIR=~ java -cp trainer.jar #{trainer_class}"

`ssh -tt -i #{aws_pem} ec2-user@#{ec2_host} '#{cmd}'`

puts "Downloading results..."

`scp -i #{aws_pem} ec2-user@#{ec2_host}:*.zip #{data_folder}`

puts "All done!"