package com.example.esa_spring.api;

import com.example.esa_spring.dto.CharacterDTO;
import com.example.esa_spring.entity.Character;
import com.example.esa_spring.service.CharacterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
public class CharacterRESTController {

    private final CharacterService characterService;
    private final ModelMapper mapper;
    private final XmlMapper xmlMapper;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CharacterDTO> findById(@PathVariable("id") UUID id) {
        CharacterDTO character = characterService.findById(id);
        return character == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(character);
    }

    @GetMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> findAll(@RequestHeader("Accept") String acceptHeader) {
        List<CharacterDTO> characters = characterService.findAll();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xsltSource = new StreamSource("src/main/resources/xslt/character.xslt");
                Transformer transformer = transformerFactory.newTransformer(xsltSource);
                Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(characters)));
                StringWriter outWriter = new StringWriter();
                Result result = new StreamResult(outWriter);
                transformer.transform(xmlSource, result);
                return new ResponseEntity<>(outWriter.toString(), HttpStatus.OK);
            } catch (TransformerException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(characters);
    }

    @PostMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO character) {
        characterService.save(mapper.map(character, Character.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CharacterDTO> delete(@PathVariable("id") UUID id) {
        characterService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharacterDTO> update(@PathVariable("id") UUID id, @RequestBody CharacterDTO character) {
        boolean new_data = characterService.findById(id) == null;
        characterService.save(mapper.map(character, Character.class));
        return new ResponseEntity<>(new_data ? HttpStatusCode.valueOf(201): HttpStatus.OK);
    }

}
