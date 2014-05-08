package org.apache.pig.backend.hadoop.executionengine.spark.converter;

import java.io.IOException;
import java.util.List;

import org.apache.pig.backend.hadoop.executionengine.physicalLayer.PhysicalOperator;
import org.apache.spark.rdd.RDD;

/**
 * Given an RDD and a PhysicalOperater, and implementation of this class can convert the RDD to
 * another RDD.
 *
 * @author billg
 */
public interface POConverter<IN, OUT, T extends PhysicalOperator> {
	
	/**
	 * 
	 * @param rdd 依赖的祖先RDDs(由前驱物理执行计划计算结果连成)
	 * @param physicalOperator 本次需要转化的物理操作
	 * @return 返回的是该物理操作对应的计算结果RDD
	 * @throws IOException
	 */
    RDD<OUT> convert(List<RDD<IN>> rdd, T physicalOperator) throws IOException;
    
}
