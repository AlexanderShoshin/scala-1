## Description

scala-1 is a spark application that process server log file (https://drive.google.com/file/d/0B4eU5TenoBPjZllmdTVfRS1xSE0/view?usp=sharing) and execute the operations below.

1. Count bytes.
    1. Count average bytes per request by IP and total bytes by IP.
    2. Sort output by total amount of bytes.
    3. Print top-5 in console and save other output to CSV file with rows as next: IP,175.5,109854.

2. Count browsers.
    1. Use accumulators to get stats how many users of IE, Mozzila or Other were detected.
    2. Print them in STDOUT of the driver.

## Testing environment

Program was tested on HDP 2.4 sandbox.

## How to deploy

1. Make scala-1-assembly-1.0.jar by running sbt command from project root:
```
sbt clean assembly
```
2. Copy scala-1-assembly-1.0.jar from target/scala-2.10/ to machine with Spark installed.
3. Download dataset(https://drive.google.com/file/d/0B4eU5TenoBPjZllmdTVfRS1xSE0/view) and put it to hdfs.
4. Run Spark job, defining cluster manager, hdfs path to dataset file and hdfs path to output directory:
```
spark-submit \
--class logstat.App \
--master <cluster_manager> \
<path_to_jar>/scala-1-assembly-1.0.jar \
--input <hdfs_path_to_dataset_file> \
--output <hdfs_path_to_output_directory>
```
4.1. Local mode task submitting may look like this:
```
spark-submit \
--class logstat.App \
--master local[*] \
scala-1-assembly-1.0.jar \
--input /training/dataset/000000 \
--output /training/spark/2
```
4.2. Submitting to YARN:
```
spark-submit \
--class logstat.App \
--master yarn \
--executor-memory 512m \
--driver-memory 512m \
scala-1-assembly-1.0.jar \
--input /training/dataset/000000 \
--output /training/spark/1
```
**It is strongly recommended to define --executor-memory and --driver-memory flags when running on YARN. Otherwise your job may wait forever for resources allocation**

## Results

You can find screenshots of the application execution in /screenshots folder.