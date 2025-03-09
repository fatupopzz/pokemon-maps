package com.pokemon.util;

import com.pokemon.model.Pokemon;
import com.pokemon.service.MapaPokemons;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Clase para leer datos de Pokémon desde un archivo CSV.
 */
public class LectorCSV {
    
    /**
     * Lee el archivo CSV de Pokémon y carga los datos en el mapa proporcionado.
     */
    public static MapaPokemons leerArchivoPokemon(String rutaArchivo, MapaPokemons mapaPokemons) throws IOException {
        System.out.println("Leyendo archivo: " + rutaArchivo);
        
        try (BufferedReader br = Files.newBufferedReader(Paths.get(rutaArchivo), StandardCharsets.UTF_8)) {
            String linea;
            
            // Leer la primera línea (encabezados)
            String encabezado = br.readLine();
            if (encabezado == null) {
                throw new IOException("El archivo está vacío o no tiene encabezados");
            }
            
            // Determinar la estructura del archivo según los encabezados
            String[] columnas = encabezado.split(",");
            int contadorLineas = 0;
            
            // Procesar el resto de líneas
            while ((linea = br.readLine()) != null) {
                contadorLineas++;
                try {
                    String[] datos = linea.split(",");
                    
                    // Validar que tenga suficientes columnas
                    if (datos.length < 9) {
                        System.err.println("La línea " + contadorLineas + " no tiene suficientes columnas. Se omitirá.");
                        continue;
                    }
                    
                    // Extraer datos según la estructura del CSV pokemon_data_pokeapi.csv
                    String name = datos[0].trim();
                    int pokedexNumber = Integer.parseInt(datos[1].trim());
                    String type1 = datos[2].trim();
                    String type2 = datos[3].trim();
                    String classification = datos[4].trim();
                    float height = Float.parseFloat(datos[5].trim());
                    float weight = Float.parseFloat(datos[6].trim());
                    String abilities = datos[7].trim();
                    int generation = Integer.parseInt(datos[8].trim());
                    boolean legendary = datos.length > 9 && datos[9].trim().toLowerCase().contains("legendary");
                    
                    // Crear el objeto Pokémon y agregarlo al mapa
                    Pokemon pokemon = new Pokemon(
                        name, 
                        pokedexNumber,
                        type1, 
                        type2, 
                        classification,
                        height, 
                        weight, 
                        abilities,
                        generation, 
                        legendary
                    );
                    
                    mapaPokemons.agregarPokemon(pokemon);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Error al procesar la línea " + contadorLineas + ": " + e.getMessage());
                }
            }
            
            System.out.println("Se cargaron " + contadorLineas + " Pokémon desde el archivo.");
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            throw e;
        }
        
        return mapaPokemons;
    }
}