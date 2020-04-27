package com.nickmeeker.server.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class FFNN {
    @Id
    private Integer id;
    private Integer baseModelId;
    private Integer hiddenNodes;
    private List<Double[][]> hiddenWeights;
    private Double[][] outputWeights;
    private Boolean isAggregatedModel;


    public FFNN(Integer id, Integer baseModelId, Integer hiddenNodes, List<Double[][]> hiddenWeights, Double[][] outputWeights, Boolean isAggregatedModel) {
        this.id = id;
        this.baseModelId = baseModelId;
        this.hiddenNodes = hiddenNodes;
        this.hiddenWeights = hiddenWeights;
        this.outputWeights = outputWeights;
        this.isAggregatedModel = isAggregatedModel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBaseModelId() {
        return baseModelId;
    }

    public void setBaseModelId(Integer baseModelId) {
        this.baseModelId = baseModelId;
    }

    public Boolean getAggregatedModel() {
        return isAggregatedModel;
    }

    public Integer getHiddenNodes() {
        return hiddenNodes;
    }

    public void setHiddenNodes(Integer hiddenNodes) {
        this.hiddenNodes = hiddenNodes;
    }

    public List<Double[][]> getHiddenWeights() {
        return hiddenWeights;
    }

    public void setHiddenWeights(List<Double[][]> hiddenWeights) {
        this.hiddenWeights = hiddenWeights;
    }

    public Double[][] getOutputWeights() {
        return outputWeights;
    }

    public void setOutputWeights(Double[][] outputWeights) {
        this.outputWeights = outputWeights;
    }

    public Boolean isAggregatedModel() {
        return isAggregatedModel;
    }

    public void setAggregatedModel(Boolean aggregatedModel) {
        isAggregatedModel = aggregatedModel;
    }
}
