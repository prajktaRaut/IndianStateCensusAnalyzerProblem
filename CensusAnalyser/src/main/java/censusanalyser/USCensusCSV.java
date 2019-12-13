package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    public USCensusCSV() {
    }

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "Total Area")
    public double totalArea;

    @CsvBindByName(column = "Population Density")
    public double populationDensity;

    public USCensusCSV(String state, int population, double totalArea, double populationDensity) {

        this.state = state;
        this.population = population;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
    }
}
