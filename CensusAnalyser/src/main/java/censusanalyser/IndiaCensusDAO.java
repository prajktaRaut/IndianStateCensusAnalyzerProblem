package censusanalyser;

public class IndiaCensusDAO {


    public int population;
    public String state;
    public String stateCode;
    public int areaInSqKm;
    public int densityPerSqKm;
    public double populationDensity;
    public double totalArea;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {

        state = indiaCensusCSV.state;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;

    }

    public IndiaCensusDAO(USCensusCSV censusCSV)
    {
        state=censusCSV.state;
        totalArea=censusCSV.totalArea;
        populationDensity=censusCSV.populationDensity;
        population=censusCSV.population;


    }

    public Object getCensusDTO(CensusAnalyser.Country country)
    {
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensusCSV(state,population,populationDensity,totalArea);
        return new IndiaCensusCSV(state,population,areaInSqKm,densityPerSqKm);
    }

}
