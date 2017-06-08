package com.jtarun;

import com.jtarun.config.SparkConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

	@Autowired
	private SparkConfig sparkConfig;

	public static void main(String[] args)   {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {

		if (args.length < 1) {
			throw new Exception("No file or directory path provided in argument");
		}

		SparkConf conf = new SparkConf();

		sparkConfig.getConf().forEach(conf::set);

		SparkSession spark = SparkSession
				.builder()
				.config(conf)
				.getOrCreate();

		Dataset<String> ds = spark.read().textFile(args);

		System.out.println("count: " + ds.count());
	}
}
