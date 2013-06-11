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

CLASSPATH=${base_dir}/build/classes/main/
for jar in ${base_dir}/lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$jar
done

runcompss $*

