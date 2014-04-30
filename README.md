What is flare-spork ?
===========

flare-spork is based on Pig spork-branch, which is aimed to achieve <strong>Pig on Spark</strong>. flare-spork is compatible with Pig 0.12.0, Hadoop 2.2.0, Spark 2.9.1 and Scala 2.10.3. Actually, I merged the Spork project into Hortonwork HDP 2.0 Pig 0.12.0, and updated the Scala and Spark codes to new version, and will improve Pig on Spark next.


What Spork brings me ?
===========

Spork did some work long ago and no updates recently. <br/>
Spork created SparkLauncher under <strong>org.apache.pig.backend.hadoop.executionengine.spark</strong> to support spark backend when launching Pig like:

    pig -x spark

Also, it supported several Convertors to mapping Physical Ops to Spark RDD computations, which can be enlarged. <br/>
Besides, Spork modified build.xml and ivy things to include Spark and Scala dependencies. 


Why flare-spork ?
===========
Spork is old and has something wrong if you directly build and run with it. And it is highly exprimental. <br/>
As far as I know, Spork may be in progress in Twitter. So I am trying out Pig on Spark based on Spork.


How to Run ?
===========
You can build flare-spork (better like this to support Hadoop2.2.0)

    ant clean jar-withouthadoop -Dhadoopversion=23

Then, do some System settings and enable pig in spark mode 

    export SPARK_HOME=/xx/xx/xx
    export SPARK_JARS=/xx/xx/xx/spark-assembly_2.10-0.9.1-hadoop2.2.0.jar
    export PIG_JAR=$SPORK_HOME/build/pig-0.12.0-SNAPSHOT-withdependencies.jar
    
    pig -x spark

I have succeeded in merging Spark onto Pig 0.12.0 and run with it. <br/ >
Simple Ops like Load, Filter, Foreach, Distinct are OK now. Currently, Spark local mode (mesos maybe) is available.

    A = LOAD 'kv.txt' USING PigStorage() AS (name:chararray, money:float);
    B = foreach A generate name;
    dump B;
    C = DISTINCT B;
    dump C;

Progress ?
===========
1. Update Scala to 2.10.3
2. Update Spark to 0.9.1
3. Merging Spark with Pig 0.12.0, Hadoop 2.2.0

