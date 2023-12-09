package com.example.esa_spring.api;

import com.example.esa_spring.dto.WeaponDTO;
import com.example.esa_spring.entity.Weapon;
import com.example.esa_spring.service.WeaponService;
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
@RequestMapping("/api/weapon")
public class WeaponRESTController {

    private final WeaponService weaponService;
    private final ModelMapper mapper;
    private final XmlMapper xmlMapper;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<WeaponDTO> findById(@PathVariable("id") UUID id) {
        WeaponDTO weapon = weaponService.findById(id);
        return weapon == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(weapon);
    }

    @GetMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> findAll(@RequestHeader("Accept") String acceptHeader) {
        List<WeaponDTO> weapons = weaponService.findAll();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xsltSource = new StreamSource("src/main/resources/xslt/weapon.xslt");
                Transformer transformer = transformerFactory.newTransformer(xsltSource);
                Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(weapons)));
                StringWriter outWriter = new StringWriter();
                Result result = new StreamResult(outWriter);
                transformer.transform(xmlSource, result);
                return new ResponseEntity<>(outWriter.toString(), HttpStatus.OK);
            } catch (TransformerException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(weapons);
    }

    @PostMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<WeaponDTO> save(@RequestBody WeaponDTO weapon) {
        weaponService.save(mapper.map(weapon, Weapon.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<WeaponDTO> delete(@PathVariable("id") UUID id) {
        weaponService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeaponDTO> update(@PathVariable("id") UUID id, @RequestBody WeaponDTO weapon) {
        boolean new_data = weaponService.findById(id) == null;
        weaponService.save(mapper.map(weapon, Weapon.class));
        return new ResponseEntity<>(new_data ? HttpStatusCode.valueOf(201): HttpStatus.OK);
    }

}
