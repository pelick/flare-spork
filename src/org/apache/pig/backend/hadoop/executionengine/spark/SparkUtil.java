package org.apache.pig.backend.hadoop.executionengine.spark;

import org.apache.hadoop.mapred.JobConf;
import org.apache.pig.backend.hadoop.datastorage.ConfigurationUtil;
import org.apache.pig.backend.hadoop.executionengine.physicalLayer.PhysicalOperator;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.PigContext;
import org.apache.pig.impl.util.ObjectSerializer;
import org.apache.pig.impl.util.UDFContext;
import org.apache.spark.rdd.RDD;

import scala.Tuple2;
import scala.collection.JavaConversions;
import scala.collection.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;

import java.io.IOException;
import java.util.List;

/**
 * 
 * @author zhangbaofeng
 *
 */
public class SparkUtil {

	public static void main(String[] args) {
		System.out.print(ClassTag$.MODULE$.apply(Tuple.class));
	}
	
	/**
	 * Scala 2.10 use ClassTag to replace ClassManifest
	 */
    public static <T> ClassTag<T> getClassTag(Class<T> clazz) {
    	return ClassTag$.MODULE$.apply(clazz);
    }
    
    @SuppressWarnings("unchecked")
	public static <K, V> ClassTag<Tuple2<K, V>> getTuple2ClassTag() {
    	return (ClassTag<Tuple2<K, V>>)(Object) getClassTag(Tuple2.class);
    }

    public static JobConf newJobConf(PigContext pigContext) throws IOException {
        JobConf jobConf = new JobConf(ConfigurationUtil.toConfiguration(pigContext.getProperties()));
        jobConf.set("pig.pigContext", ObjectSerializer.serialize(pigContext));
        UDFContext.getUDFContext().serialize(jobConf);
        jobConf.set("udf.import.list", ObjectSerializer.serialize(PigContext.getPackageImportList()));
        return jobConf;
    }

    public static <T> Seq<T> toScalaSeq(List<T> list) {
        return JavaConversions.asScalaBuffer(list);
    }

    public static void assertPredecessorSize(List<RDD<Tuple>> predecessors, PhysicalOperator physicalOperator, int size) {
        if (predecessors.size() != size) {
            throw new RuntimeException("Should have " + size + " predecessors for " +
                    physicalOperator.getClass() + ". Got : " + predecessors.size());
        }
    }

    public static void assertPredecessorSizeGreaterThan(List<RDD<Tuple>> predecessors, PhysicalOperator physicalOperator, int size) {
        if (predecessors.size() <= size) {
            throw new RuntimeException("Should have greater than" + size + " predecessors for " +
                    physicalOperator.getClass() + ". Got : " + predecessors.size());
        }
    }

    public static  int getParallelism(List<RDD<Tuple>> predecessors, PhysicalOperator physicalOperator) {
        int parallelism = physicalOperator.getRequestedParallelism();
        if (parallelism <= 0) {
            // Parallelism wasn't set in Pig, so set it to whatever Spark thinks is reasonable.
            parallelism = predecessors.get(0).context().defaultParallelism();
        }
        return parallelism;
    }

}
