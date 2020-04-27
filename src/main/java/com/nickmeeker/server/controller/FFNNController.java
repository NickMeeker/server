package com.nickmeeker.server.controller;

import com.nickmeeker.server.bean.FFNNPushRequest;
import com.nickmeeker.server.document.FFNN;
import com.nickmeeker.server.service.FFNNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ffnn")
public class FFNNController {

    @Autowired
    FFNNService ffnnService;

    public ResponseEntity<String> defaultSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        return new ResponseEntity<String>("{\"status\": \"SUCCESS\"}", headers, HttpStatus.OK);
    }

    @PostMapping("/push")
    @ResponseBody
    public ResponseEntity<String> push(@RequestBody FFNNPushRequest FFNNPushRequest) {
        Integer baseModelId = FFNNPushRequest.getBaseModelId();
        List<Double[][]> hiddenWeights = FFNNPushRequest.getHiddenWeights();
        Double[][] outputWeights = FFNNPushRequest.getOutputWeights();
        ffnnService.push(baseModelId, hiddenWeights, outputWeights);

        return defaultSuccess();
    }

    @GetMapping("/pull")
    @ResponseBody
    public ResponseEntity<?> pull(@RequestParam("modelId") int modelId) {
        FFNN ffnn = ffnnService.pull(modelId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        if(ffnn == null) {
            return new ResponseEntity<String>(
                    "{\n" +
                            "  \"status\": \"FAILURE\",\n" +
                            "  \"message\": \"It looks like you already have the most recent model\"\n" +
                            "}",
                    headers, HttpStatus.OK);
        }

        return new ResponseEntity<>(ffnn, headers, HttpStatus.OK);
    }

    @PostMapping("/aggregate")
    @ResponseBody
    public ResponseEntity<String> aggregate() {
        ffnnService.aggregate();

        return defaultSuccess();
    }
}
