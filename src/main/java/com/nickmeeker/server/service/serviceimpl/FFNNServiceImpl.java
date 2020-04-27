package com.nickmeeker.server.service.serviceimpl;

import com.nickmeeker.server.config.MLConfig;
import com.nickmeeker.server.document.FFNN;
import com.nickmeeker.server.document.Property;
import com.nickmeeker.server.repository.FFNNRepository;
import com.nickmeeker.server.repository.PropertiesRepository;
import com.nickmeeker.server.service.FFNNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FFNNServiceImpl implements FFNNService {
    @Autowired
    MLConfig mlConfig;
    @Autowired
    private FFNNRepository modelRepository;
    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    NextSequenceService nextSequenceService;

    @Override
    public FFNN pull(Integer id) {
        Integer latestModelId = Integer.valueOf(Integer.parseInt(propertiesRepository.findById("latestModel").get().getValue()));
        if(id.intValue() != latestModelId.intValue()) {
            return modelRepository.findById(latestModelId).get();
        }

        return null;
    }

    @Override
    public void push(Integer baseModelId, List<Double[][]> hiddenWeights, Double[][] outputWeights) {
        modelRepository.save(new FFNN(Integer.valueOf(nextSequenceService.getNextSequence("modelsequence")),
        baseModelId,
        Integer.parseInt(mlConfig.getMLConfigValue("hidden.layers")),
        hiddenWeights,
        outputWeights,
        false));
    }

    @Override
    public void aggregate() {
        List<FFNN> ffnns = new ArrayList<>();
        for(FFNN ffnn : modelRepository.findAll()) {
            if(!ffnn.isAggregatedModel()) {
                ffnns.add(ffnn);
                modelRepository.delete(ffnn);
            }
        }

        // we will use the number of data points for calculating the average of the weights
        int dataPoints = ffnns.size();
        System.out.println(dataPoints);
        if(dataPoints == 0)
            return;

        List<Double[][]> averageHiddenWeights = new ArrayList<Double[][]>();
        Double[][] averageOutputWeights = new Double[ffnns.get(0).getOutputWeights().length][ffnns.get(0).getOutputWeights()[0].length];

        for(int i = 0; i < ffnns.get(0).getHiddenWeights().size(); i++) {
            averageHiddenWeights.add(new Double[ffnns.get(0).getHiddenWeights().get(i).length][ffnns.get(0).getHiddenWeights().get(i)[0].length]);
            for(Double[] arr : averageHiddenWeights.get(i))
                Arrays.fill(arr, 0.0);
        }

        for(Double[] arr : averageOutputWeights)
            Arrays.fill(arr, 0.0);

        for(int i = 0; i < averageHiddenWeights.size(); i++) {
            for(int j = 0; j < averageHiddenWeights.get(i).length; j++) {
                for(int k = 0; k < averageHiddenWeights.get(i)[0].length; k++) {
                    for(int l = 0; l < ffnns.size(); l++) {
                        averageHiddenWeights.get(i)[j][k] += ffnns.get(l).getHiddenWeights().get(i)[j][k];
                    }
                    averageHiddenWeights.get(i)[j][k] /= dataPoints;
                }
            }
        }

        for(int i = 0; i < averageOutputWeights.length; i++) {
            for(int j = 0; j < averageOutputWeights[0].length; j++) {
                for(FFNN ffnn : ffnns) {
                    averageOutputWeights[i][j] += ffnn.getOutputWeights()[i][j];
                }
                averageOutputWeights[i][j] /= (double) dataPoints;
                //System.out.print(averageOutputWeights[i][j] + " ");
            }
            //System.out.println();
        }

        Integer newModelId = nextSequenceService.getNextSequence("modelsequence");

        FFNN aggregatedFFNN = new FFNN(
                newModelId,
                newModelId,
                Integer.parseInt(mlConfig.getMLConfigValue("hidden.layers")),
                averageHiddenWeights,
                averageOutputWeights,
                true);

        propertiesRepository.deleteById("latestModel");
        propertiesRepository.save(new Property("latestModel", Integer.toString(aggregatedFFNN.getId())));

        modelRepository.save(aggregatedFFNN);
    }
}
