package day05

import day05.Line.Companion.toLine
import utils.component6
import utils.component7
import utils.component8
import utils.split

data class Almanac(
    private val seedToSoilMap: List<Line>,
    private val soilToFertilizerMap: List<Line>,
    private val fertilizerToWaterMap: List<Line>,
    private val waterToLightMap: List<Line>,
    private val lightToTemperatureMap: List<Line>,
    private val temperatureToHumidityMap: List<Line>,
    private val humidityToLocationMap: List<Line>,
) {
    fun getSoil(seed: Long) = seedToSoilMap.getOrDefault(seed)

    fun getFertilizer(seed: Long) = soilToFertilizerMap.getOrDefault(getSoil(seed))

    fun getWater(seed: Long) = fertilizerToWaterMap.getOrDefault(getFertilizer(seed))

    fun getLight(seed: Long) = waterToLightMap.getOrDefault(getWater(seed))

    fun getTemperature(seed: Long) = lightToTemperatureMap.getOrDefault(getLight(seed))

    fun getHumidity(seed: Long) = temperatureToHumidityMap.getOrDefault(getTemperature(seed))

    fun getLocation(seed: Long) = humidityToLocationMap.getOrDefault(getHumidity(seed))

    private fun List<Line>.getOrDefault(default: Long): Long {
        return this.firstNotNullOfOrNull { line -> line.getDestinationOrNull(default) } ?: default
    }

    companion object {
        fun List<String>.toAlmanac(): Almanac {
            val (_,
                seedToSoil,
                soilToFertilizer,
                fertilizerToWater,
                waterToLight,
                lightToTemperature,
                temperatureToHumidity,
                humidityToLocation
            ) = split { it.isBlank() }

            return Almanac(
                seedToSoilMap = seedToSoil.toMap(),
                soilToFertilizerMap = soilToFertilizer.toMap(),
                fertilizerToWaterMap = fertilizerToWater.toMap(),
                waterToLightMap = waterToLight.toMap(),
                lightToTemperatureMap = lightToTemperature.toMap(),
                temperatureToHumidityMap = temperatureToHumidity.toMap(),
                humidityToLocationMap = humidityToLocation.toMap(),
            )
        }

        private fun List<String>.toMap(): List<Line> {
            return subList(1, size).map { it.split(' ').map { element -> element.toLong() }.toLine() }
        }
    }
}
