package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {

    public IndiaCensusCSV() {
    }

    @CsvBindByName(column = "State")
    public String state;

    @CsvBindByName(column = "Population")
    public int population;

    @CsvBindByName(column = "AreaInSqKm")
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm")
    public int densityPerSqKm;

    public IndiaCensusCSV(String state, int population, int areaInSqKm, int densityPerSqKm) {
        this.state = state;
        this.population = population;
        this.areaInSqKm = areaInSqKm;
        this.densityPerSqKm = densityPerSqKm;
    }

    @Override
    public String toString() {
        return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}
