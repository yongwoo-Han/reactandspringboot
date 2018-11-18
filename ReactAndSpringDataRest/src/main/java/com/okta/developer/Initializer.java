package com.okta.developer;

import java.sql.Date;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.okta.developer.model.Event;
import com.okta.developer.model.Group;
import com.okta.developer.model.GroupRepository;


/**
 * Spring boot 애플리케이션 구동 시점에 특정코드 실행하는 두가지
 * CommandLineRunner : 자바 문자열(String) 아규먼트 배열에 접근해야할 필요가 있을 때 사용
 * ApplicationRunner : CommandLineRunner와 마찬가지로 run 메소드를 실행시키며, String 문자열을 포함한 
 * 						ApplicationArguments 타입의 객체가 대신 인자값으로 넘어옴
 * @author han
 *
 */
@Component
public class Initializer implements CommandLineRunner {

	private GroupRepository repository;
	
	public Initializer(GroupRepository repository) {
		// TODO Auto-generated constructor stub
		this.repository = repository;
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Stream.of("Denver JUG", "Utah JUG", "Seattle JUG",
                "Richmond JUG").forEach(name->repository.save(new Group(name)));
		
		Group djug = repository.findByName("Denver JUG");
		// 빌더 패턴을 통한 Event 객체 생성
		Event event = Event.builder().title("Full Stack Reactive")
						.description("Reactive with Spring Boot + React")
						.date(Instant.parse("2018-12-12T18:00:00.000Z"))
						.build();
		djug.setEvents(Collections.singleton(event));
		repository.save(djug);
		
		repository.findAll().forEach(System.out::println);
	}

}
