package com.example.esa_spring.api;

import com.example.esa_spring.dto.ArtefactDTO;
import com.example.esa_spring.entity.Artefact;
import com.example.esa_spring.service.ArtefactService;
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
@RequestMapping("/api/artefact")
public class ArtefactRESTController {

    private final ArtefactService artefactService;
    private final ModelMapper mapper;
    private final XmlMapper xmlMapper;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ArtefactDTO> findById(@PathVariable("id") UUID id) {
        ArtefactDTO artefact = artefactService.findById(id);
        return artefact == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(artefact);
    }

    @GetMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> findAll(@RequestHeader("Accept") String acceptHeader) {
        List<ArtefactDTO> artefacts = artefactService.findAll();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xsltSource = new StreamSource("src/main/resources/xslt/artefact.xslt");
                Transformer transformer = transformerFactory.newTransformer(xsltSource);
                Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(artefacts)));
                StringWriter outWriter = new StringWriter();
                Result result = new StreamResult(outWriter);
                transformer.transform(xmlSource, result);
                return new ResponseEntity<>(outWriter.toString(), HttpStatus.OK);
            } catch (TransformerException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(artefacts);
    }

    @PostMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ArtefactDTO> save(@RequestBody ArtefactDTO artefact) {
        artefactService.save(mapper.map(artefact, Artefact.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArtefactDTO> delete(@PathVariable("id") UUID id) {
        artefactService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtefactDTO> update(@PathVariable("id") UUID id, @RequestBody ArtefactDTO artefact) {
        boolean new_data = artefactService.findById(id) == null;
        artefactService.save(mapper.map(artefact, Artefact.class));
        return new ResponseEntity<>(new_data ? HttpStatusCode.valueOf(201): HttpStatus.OK);
    }
}
