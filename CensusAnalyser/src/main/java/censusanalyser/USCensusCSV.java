package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State_Id",required = true)
    public int stateId;

    @CsvBindByName(column = "State",required = true)
    public String State;

    @CsvBindByName(column = "Population",required = true)
    public int population;

    @CsvBindByName(column = "TotalArea",required = true)
    public int totalArea;

    @CsvBindByName(column = "PopulationDensity",required = true)
    public int populationDensity;

}
