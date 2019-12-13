package censusanalyser;

import com.bridgelabzs.CSVBuilderException;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CSVBuilderException {
        Map<String,IndiaCensusDAO> censusDAOMap=super.loadCensusData(USCensusCSV.class,csvFilePath);
        return censusDAOMap;
    }
}
