package com.workflow.engine;

import com.workflow.engine.model.FlowModel;
import com.workflow.engine.parser.FlowNodeParser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootApplication
@MapperScan("com.workflow.engine.mapper")
public class EngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineApplication.class, args);

	}

}
