package com.pokemon.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.pokemon.model.Pokemon;

/**
 * Gestiona la colección personal de Pokémon del usuario.
 * Autor: Fatima Navarro - 24044
 */
public class ColeccionUsuario {
    //--------
    // Utilizamos un HashSet para almacenar solo los nombres de los Pokémon
    // que el usuario tiene en su colección. El uso de Set garantiza
    // que no haya duplicados, y HashSet ofrece operaciones O(1)
    //--------
    private Set<String> pokemonesUsuario;
    private MapaPokemons mapaReferencia;
    
    /**
     * Constructor que recibe el mapa completo de Pokémon como referencia.
     */
    public ColeccionUsuario(MapaPokemons mapaReferencia) {
        //--------
        // El mapa de referencia contiene todos los Pokémon disponibles
        // La colección del usuario es un subconjunto de éstos
        //--------
        this.pokemonesUsuario = new HashSet<>();
        this.mapaReferencia = mapaReferencia;
    }
    
    /**
     * Agrega un Pokémon a la colección del usuario por su nombre.
     */
    public boolean agregarPokemon(String nombre) {
        //--------
        // Verificamos primero que el Pokémon exista en la base de datos
        // Luego comprobamos que no esté ya en la colección del usuario
        //--------
        if (!mapaReferencia.existePokemon(nombre)) {
            // No existe en los datos de referencia
            return false;
        }
        
        // Intentamos agregarlo (retorna false si ya existía)
        return pokemonesUsuario.add(nombre);
    }
    
    /**
     * Elimina un Pokémon de la colección del usuario por su nombre.
     */
    public boolean eliminarPokemon(String nombre) {
        //--------
        // Simple operación de eliminación del Set
        // Retorna true si existía y se eliminó, false si no estaba
        //--------
        return pokemonesUsuario.remove(nombre);
    }
    
    /**
     * Obtiene la lista de nombres de Pokémon en la colección.
     */
    public Set<String> obtenerNombresPokemon() {
        //--------
        // Devuelve una referencia directa al Set de nombres
        // El usuario no puede modificar la colección a través de este método
        // debido a la encapsulación de los métodos de modificación
        //--------
        return pokemonesUsuario;
    }
    
    /**
     * Obtiene la cantidad de Pokémon en la colección.
     */
    public int cantidadPokemon() {
        return pokemonesUsuario.size();
    }
    
    /**
     * Obtiene la lista de objetos Pokémon en la colección.
     */
    public List<Pokemon> obtenerPokemons() {
        //--------
        // Convertimos los nombres en objetos Pokémon completos
        // usando el mapa de referencia para obtener los datos
        //--------
        List<Pokemon> resultado = new ArrayList<>();
        for (String nombre : pokemonesUsuario) {
            Pokemon pokemon = mapaReferencia.obtenerPokemon(nombre);
            if (pokemon != null) {
                resultado.add(pokemon);
            }
        }
        return resultado;
    }
    
    /**
     * Obtiene los Pokémon ordenados por tipo primario.
     */
    public List<Pokemon> obtenerPokemonsPorTipo() {
        //--------
        // Usa Stream API para ordenar la colección por tipo
        // Primero por tipo principal y luego por nombre
        //--------
        return obtenerPokemons().stream()
            .sorted(Comparator.comparing(Pokemon::getType1)
                             .thenComparing(Pokemon::getName))
            .collect(Collectors.toList());
    }
    
    /**
     * Encuentra los Pokémon de un tipo específico en la colección.
     */
    public List<Pokemon> buscarPorTipo(String tipo) {
        //--------
        // Filtrado doble: busca tanto en tipo1 como en tipo2
        // Permite encontrar todos los Pokémon que tengan ese tipo
        // aunque no sea su tipo principal
        //--------
        return obtenerPokemons().stream()
            .filter(p -> p.getType1().equalsIgnoreCase(tipo) || 
                       (p.getType2() != null && p.getType2().equalsIgnoreCase(tipo)))
            .collect(Collectors.toList());
    }
    
    /**
     * Encuentra los Pokémon legendarios en la colección.
     */
    public List<Pokemon> obtenerLegendarios() {
        //--------
        // Filtra solo los Pokémon legendarios usando Stream API
        //--------
        return obtenerPokemons().stream()
            .filter(Pokemon::isLegendary)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de la colección.
     */
    public String obtenerEstadisticas() {
        //--------
        // Analiza la colección y retorna estadísticas interesantes
        // como distribución por tipos, generaciones, etc.
        //--------
        List<Pokemon> pokemons = obtenerPokemons();
        if (pokemons.isEmpty()) {
            return "No hay Pokémon en tu colección.";
        }
        
        // Contar Pokémon por generación
        int[] generaciones = new int[9]; // Generaciones 1-8
        for (Pokemon p : pokemons) {
            if (p.getGeneration() >= 1 && p.getGeneration() <= 8) {
                generaciones[p.getGeneration()]++;
            }
        }
        
        // Contar tipos principales
        StringBuilder stats = new StringBuilder();
        stats.append("=== Estadísticas de tu colección ===\n");
        stats.append("Total de Pokémon: ").append(pokemons.size()).append("\n");
        stats.append("Pokémon legendarios: ").append(
            pokemons.stream().filter(Pokemon::isLegendary).count()
        ).append("\n\n");
        
        stats.append("Distribución por generación:\n");
        for (int i = 1; i <= 8; i++) {
            if (generaciones[i] > 0) {
                stats.append("- Gen ").append(i).append(": ")
                     .append(generaciones[i]).append(" Pokémon\n");
            }
        }
        
        return stats.toString();
    }
}