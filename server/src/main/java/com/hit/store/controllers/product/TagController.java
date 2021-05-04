package com.hit.store.controllers.product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hit.store.models.product.Tag;
import com.hit.store.repositories.product.TagRepository;
import com.hit.store.utils.Validator;

@RestController
@RequestMapping("/tag")
public class TagController {

	@Autowired
	private TagRepository tagRepository;
	
	
	@GetMapping("/getAll")
	public List<Tag> getAll() {
		return tagRepository.findAll();
	}
	
	
	@GetMapping("/getById")
	public Tag getById(@RequestBody Tag tag) {
		final Long id = tag.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		final Optional<Tag> result = tagRepository.findById(id);
		if(!result.isPresent()) throw new IllegalArgumentException("Can't find tag with id: " + id);
		return result.get();
	}
	
	
	@PostMapping("/addOne")
	public Long addOneTag(@RequestBody Tag tag) {
		if(!Validator.validateTag(tag)) throw new IllegalArgumentException("Invalid tag");
		return tagRepository.save(tag).getId();
	}
	
	
	@PutMapping("/update")
	public Long update(@RequestBody Tag tag) {
		final Long id = tag.getId();
		if(id == null) throw new IllegalArgumentException("id can't be null");
		if(!Validator.validateTag(tag)) throw new IllegalArgumentException("Invalid tag");
		final Optional<Tag> result = tagRepository.findById(id);
		if(!result.isPresent()) throw new IllegalArgumentException("Can't find tag with id: " + id);
		return tagRepository.save(tag).getId();
	}
	
	
	@DeleteMapping("/delete")
	@SuppressWarnings("all")
	public HashMap delete(@RequestBody Tag tag) {
		final Long id = tag.getId();
		if(id == null) return null;
		final Optional<Tag> result = tagRepository.findById(id);
		if(!result.isPresent()) return null;
		tagRepository.deleteById(id);
		return new HashMap(){{put("deleted", 1);}};
	}
}
