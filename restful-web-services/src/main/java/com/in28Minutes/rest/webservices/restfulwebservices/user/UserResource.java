package com.in28Minutes.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

  @Autowired private UserDaoService userDaoService;

  @GetMapping("/users")
  public List<User> getAllUsers() {
    return userDaoService.findAll();
  }

  @GetMapping("/users/{id}")
  public EntityModel<User> getUser(@PathVariable int id) {
    User user = userDaoService.findOne(id);
    if(user == null) {
      throw new UserNotFoundException("User not found with id - " + id);
    }
    EntityModel<User> model = EntityModel.of(user);
    WebMvcLinkBuilder linkToUsers = linkTo(methodOn(this.getClass()).getAllUsers());
    model.add(linkToUsers.withRel("all-users"));
    return model;
  }

  @PostMapping("/users")
  public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
    User savedUser = userDaoService.saveUser(user);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedUser.getId())
            .toUri();

    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<Object> deleteUser(@PathVariable int id) {
    User user = userDaoService.findOne(id);
    if(user == null){
      throw new UserNotFoundException("User not found!");
    }
    return new ResponseEntity<>(userDaoService.deleteById(id), HttpStatus.OK);
  }
}
