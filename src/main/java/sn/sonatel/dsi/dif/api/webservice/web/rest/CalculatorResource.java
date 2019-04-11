package sn.sonatel.dsi.dif.api.webservice.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import sn.sonatel.dsi.dif.api.webservice.config.Operation;

@RestController
@RequestMapping("/api/calculator/v2")
@Api(value = "A calculator enable to computes operation (Add, Sub...) for a given two integers")
public class CalculatorResource {

    @Autowired
    RestTemplate restTemplate;

    private static final String URI = "http://localhost:8081/api/calculator/v1";

    @GetMapping("/add/{leftOperand}/{rightOperand}")
    @ApiOperation(value = "Calculate the sum between two integers", nickname = "add", notes = "This operation calculate the sum between two integers, both variables path (leftOperand and rightOperand) are mandatory and should be integers", response = ResponseEntity.class, tags={ "Calculator", })
    public ResponseEntity<Integer> add(@PathVariable("leftOperand") Integer leftOperand, @PathVariable("rightOperand") Integer rightOperand){
        Integer res = restTemplate.getForObject(URI+"/add/"+leftOperand+"/"+rightOperand, Integer.class);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/sub")
    @ApiOperation(value = "Calculate the diff between two integers", nickname = "sub", notes = "This operation calculate the diff between two integers, both variables path (leftOperand and rightOperand) are mandatory and should be integers", response = ResponseEntity.class, tags={ "Calculator", })
    public ResponseEntity<Integer> sub(@RequestBody Operation op){
        Integer res = restTemplate.postForObject(URI+"/sub", op, Integer.class);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/div/{leftOperand}/{rightOperand}")
    @ApiOperation(value = "Calculate the sum between two integers", nickname = "div", notes = "This operation calculate the div between two integers, both variables path (leftOperand and rightOperand) are mandatory and should be integers", response = ResponseEntity.class, tags={ "Calculator", })
    public ResponseEntity<Integer> div(@PathVariable("leftOperand") Integer leftOperand, @PathVariable("rightOperand") Integer rightOperand){
        Integer res = restTemplate.getForObject(URI+"/div/"+leftOperand+"/"+rightOperand, Integer.class);
        return ResponseEntity.ok(res);
    }
}


