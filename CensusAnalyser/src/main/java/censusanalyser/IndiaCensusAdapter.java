package censusanalyser;

import com.bridgelabzs.CSVBuilderException;
import com.bridgelabzs.CSVBuilderFactory;
import com.bridgelabzs.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {

    @Override
    public Map<String, IndiaCensusDAO> loadCensusData(CensusAnalyser.Country country, String... csvFilePath) throws CSVBuilderException {

        Map<String,IndiaCensusDAO> censusStateMap=super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        if (csvFilePath.length>1)
        this.loadIndiaStateCode(censusStateMap,csvFilePath[1]);
        return censusStateMap;
    }

    public int loadIndiaStateCode(Map<String,IndiaCensusDAO> censusMap, String csvFilePath) throws CSVBuilderException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder icsvBuilder = CSVBuilderFactory.createOpenCSVBuilder();
            Iterator<IndiaStateCodeCSV> censusCSVIterator = icsvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable=()->censusCSVIterator;
            StreamSupport.stream(csvIterable.spliterator(),false)
                    .filter(csvState->censusStateMap.get(csvState.state)!=null)
                    .forEach(csvState->censusStateMap.get(csvState.state).stateCode=csvState.stateCode);
            return censusMap.size();

        } catch (IOException | RuntimeException e) {
            throw new CSVBuilderException(e.getMessage(), CSVBuilderException.ExceptionType.STATE_CODE_FILE_PROBLEM);
        }
    }

}
