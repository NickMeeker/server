package com.nickmeeker.server.service;

import com.nickmeeker.server.document.FFNN;

import java.util.List;

public interface FFNNService {
    void push(Integer baseModelId, List<Double[][]> hiddenWeights, Double[][] outputWeights);
    FFNN pull(Integer id);
    void aggregate();
}
