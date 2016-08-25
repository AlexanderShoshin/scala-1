## Testing environment

Programm was tested on HDP 2.4 sandbox.

## How to deploy

1. Make scala-1-assembly-1.0.jar by running sbt command:
```
sbt clean assembly
```
2. Copy scala-1-assembly-1.0.jar to machine with Spark installed.
3. Download dataset(https://drive.google.com/file/d/0B4eU5TenoBPjZllmdTVfRS1xSE0/view) and put it to hdfs.
4. Run Spark job, defining claster manager, hdfs path to dataset file and hdfs path to output directory:
```
spark-submit --class BytesCount --master <claster_manager> <path_to_jar>/scala-1-assembly-1.0.jar <hdfs_path_to_dataset_file> <hdfs_path_to_output_directory>
```
4.1 Local mode task submitting may look like this:
```
spark-submit --class BytesCount --master local[*] scala-1-assembly-1.0.jar /training/dataset/000000 /training/spark/2
```
4.2 Submitting to YARN:
```
spark-submit --class BytesCount --master yarn --executor-memory 512m --driver-memory 512m scala-1-assembly-1.0.jar /training/dataset/000000 /training/spark/2
```
**It is recomended to define --executor-memory and --driver-memory flags when running on YARN. Otherwise your job may wate forever for resources allocation**