package com.example.esa_spring.api;

import com.example.esa_spring.dto.BonusDTO;
import com.example.esa_spring.entity.Bonus;
import com.example.esa_spring.service.BonusService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

import javax.xml.namespace.QName;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bonus")
public class BonusRESTController {

    private final BonusService bonusService;
    private final ModelMapper mapper;
    private final XmlMapper xmlMapper;

    @GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<BonusDTO> findById(@PathVariable("id") UUID id) {
        BonusDTO bonus = bonusService.findById(id);
        return bonus == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : ResponseEntity.ok(bonus);
    }

    @GetMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> findAll(@RequestHeader("Accept") String acceptHeader) throws JsonProcessingException {
        List<BonusDTO> bonuses = bonusService.findAll();
        if (acceptHeader.equals(MediaType.APPLICATION_XML_VALUE)) {
            try {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Source xsltSource = new StreamSource("src/main/resources/xslt/bonus.xslt");
                Transformer transformer = transformerFactory.newTransformer(xsltSource);
                Source xmlSource = new StreamSource(new ByteArrayInputStream(xmlMapper.writeValueAsBytes(bonuses)));
                StringWriter outWriter = new StringWriter();
                Result result = new StreamResult(outWriter);
                transformer.transform(xmlSource, result);
                return new ResponseEntity<>(outWriter.toString(), HttpStatus.OK);
            } catch (TransformerException | JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(bonuses);
    }

    @PostMapping(path = "/", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<BonusDTO> save(@RequestBody BonusDTO bonus) {
        bonusService.save(mapper.map(bonus, Bonus.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BonusDTO> delete(@PathVariable("id") UUID id) {
        bonusService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BonusDTO> update(@PathVariable("id") UUID id, @RequestBody BonusDTO bonus) {
        boolean new_data = bonusService.findById(id) == null;
        bonusService.save(mapper.map(bonus, Bonus.class));
        return new ResponseEntity<>(new_data ? HttpStatusCode.valueOf(201): HttpStatus.OK);
    }

}
