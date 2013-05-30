#!/bin/bash
#set -e
#set -u
#set -o errexit

# the application 'base' folder
bin_dir=`dirname "$0"`
bin_dir=`cd "$bin_dir"; pwd`
base_dir=$bin_dir

echo $bin_dir
echo $base_dir

export CLASSPATH=${base_dir}/lib/IT.jar:${base_dir}/lib/groovy-all-2.1.3.jar:${base_dir}/classes

runcompss $*

