package com.okta.developer.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.okta.developer.model.Group;
import com.okta.developer.model.GroupRepository;

@RestController
@RequestMapping("/api")
public class GroupController {

	private final Logger log = LoggerFactory.getLogger(GroupController.class);
	private GroupRepository groupRepository;
	
	public GroupController(GroupRepository groupRepository) {
		// TODO Auto-generated constructor stub
		this.groupRepository = groupRepository;
	}
	
	@GetMapping("/groups")
	public Collection<Group> groups() {
		return groupRepository.findAll();
	}
	
	/**
	 * Optional 객체에 감싸서 NPE(Null Point Exception) 예외 처리
	 * group의 결과값을 ResponseEntity 200일 때 body에 담아서 보내고
	 * 혹시나 값이 null 일경우 404 Not Found 상태값을 전송
	 * @param id
	 * @return
	 * @throws URISyntaxException
	 */
	@GetMapping("/group/{id}")
	public ResponseEntity<?> getGroup(@PathVariable Long id) throws URISyntaxException {
		Optional<Group> group = groupRepository.findById(id);
		return group.map(response->ResponseEntity.ok().body(response))
					.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/group")
	public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) throws URISyntaxException {
		log.info("Request to create group: {}", group);
		Group result = groupRepository.save(group);
		return ResponseEntity.created(new URI("/api/group/" + result.getId())).body(result);
	}
	
	@PutMapping("/group/{id}")
	public ResponseEntity<Group> updateGroup(@PathVariable Long id, @Valid @RequestBody Group group) {
		group.setId(id);
		log.info("Request to update group :{}", group);
		Group result = groupRepository.save(group);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("/group/{id}")
	public ResponseEntity<?> deleteGroup(@PathVariable Long id) {
		log.info("Request to delete group : {}", id);
		groupRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
