package com.nickmeeker.server.bean;

import java.util.List;

public class FFNNPushRequest {
    private Integer baseModelId;
    private Double[][] outputWeights;
    private List<Double[][]> hiddenWeights;

    public FFNNPushRequest(Integer baseModelId, Double[][] outputWeights, List<Double[][]> hiddenWeights) {
        this.baseModelId = baseModelId;
        this.outputWeights = outputWeights;
        this.hiddenWeights = hiddenWeights;
    }

    public Integer getBaseModelId() {
        return baseModelId;
    }

    public void setBaseModelId(Integer baseModelId) {
        this.baseModelId = baseModelId;
    }

    public Double[][] getOutputWeights() {
        return outputWeights;
    }

    public void setOutputWeights(Double[][] outputWeights) {
        this.outputWeights = outputWeights;
    }

    public List<Double[][]> getHiddenWeights() {
        return hiddenWeights;
    }

    public void setHiddenWeights(List<Double[][]> hiddenWeights) {
        this.hiddenWeights = hiddenWeights;
    }
}
