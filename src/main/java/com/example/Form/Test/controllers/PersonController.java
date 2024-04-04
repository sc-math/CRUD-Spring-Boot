package com.example.Form.Test.controllers;

import com.example.Form.Test.dtos.PersonRecordDto;
import com.example.Form.Test.models.PersonModel;
import com.example.Form.Test.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @PostMapping("/persons/register")
    public ResponseEntity<PersonModel> savePerson(@RequestBody @Valid PersonRecordDto personRecordDto) {

        var personModel = new PersonModel();
        BeanUtils.copyProperties(personRecordDto, personModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(personRepository.save(personModel));
    }

    @GetMapping("/persons/get")
    public ResponseEntity<List<PersonModel>> getAllPersons(){

        List<PersonModel> personsList = personRepository.findAll();

        if(!personsList.isEmpty()){
            for(PersonModel person: personsList){
                UUID id = person.getIdPerson();
                person.add(linkTo(methodOn(PersonController.class).getOnePerson(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(personsList);
    }

    @GetMapping("/persons/get/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") UUID id){

        Optional<PersonModel> person0 = personRepository.findById(id);

        if(person0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        person0.get().add(linkTo(methodOn(PersonController.class).getAllPersons()).withRel("Persons List"));

        return ResponseEntity.status(HttpStatus.OK).body(person0.get());
    }

    @PutMapping("/persons/update/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") UUID id, @RequestBody @Valid PersonRecordDto personRecordDto){

        Optional<PersonModel> person0 = personRepository.findById(id);

        if(person0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found!");
        }

        var personModel = person0.get();
        BeanUtils.copyProperties(personRecordDto, personModel);

        return ResponseEntity.status(HttpStatus.OK).body(personRepository.save(personModel));
    }

    @DeleteMapping("/persons/delete/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") UUID id){

        Optional<PersonModel> person0 = personRepository.findById(id);

        if(person0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found!");
        }

        personRepository.delete(person0.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product deleted sucessfully!");
    }
}
