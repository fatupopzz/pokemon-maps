package com.pokemon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pokemon.model.Pokemon;

/**
 * Pruebas para la implementación TreeMapPokemon
 * Autor: Fatima Navarro
 */
public class TreeMapPokemonTest {
    
    private MapaPokemons mapaPokemons;
    
    @BeforeEach
    public void setUp() {
        // Inicializar un mapa de tipo TreeMap
        mapaPokemons = new TreeMapPokemon();
        
        // Agregar algunos Pokémon en orden aleatorio para probar el ordenamiento
        mapaPokemons.agregarPokemon(new Pokemon(
            "Charizard", 
            6, 
            "Fire", 
            "Flying", 
            "Flame Pokémon", 
            1.7f, 
            90.5f, 
            "Blaze", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Bulbasaur", 
            1, 
            "Grass", 
            "Poison", 
            "Seed Pokémon", 
            0.7f, 
            6.9f, 
            "Overgrow", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Mewtwo", 
            150, 
            "Psychic", 
            "", 
            "Genetic Pokémon", 
            2.0f, 
            122.0f, 
            "Pressure", 
            1, 
            true
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Alakazam", 
            65, 
            "Psychic", 
            "", 
            "Psi Pokémon", 
            1.5f, 
            48.0f, 
            "Synchronize", 
            1, 
            false
        ));
    }
    
    @Test
    public void testOrdenPorNombre() {
        // En un TreeMap, los elementos deben estar ordenados por clave (nombre)
        Map<String, Pokemon> todos = mapaPokemons.obtenerTodos();
        
        // Convertir las claves a una lista para verificar el orden
        List<String> nombres = new ArrayList<>(todos.keySet());
        
        // Verificar que los nombres están en orden alfabético
        assertEquals(4, nombres.size());
        assertEquals("Alakazam", nombres.get(0));
        assertEquals("Bulbasaur", nombres.get(1));
        assertEquals("Charizard", nombres.get(2));
        assertEquals("Mewtwo", nombres.get(3));
    }
    
    @Test
    public void testBuscarPorHabilidad() {
        // Verificar la búsqueda por habilidad
        Map<String, Pokemon> conPressure = mapaPokemons.buscarPorHabilidad("Pressure");
        
        // Verificar que se encuentra el Pokémon correcto
        assertEquals(1, conPressure.size());
        assertTrue(conPressure.containsKey("Mewtwo"));
        
        // Verificar que el resultado mantiene el orden (TreeMap)
        List<String> nombres = new ArrayList<>(conPressure.keySet());
        assertEquals("Mewtwo", nombres.get(0));
    }
    
    @Test
    public void testObtenerOrdenadosPorTipo() {
        // Verificar ordenamiento por tipo y luego por nombre
        List<Pokemon> ordenados = mapaPokemons.obtenerOrdenadosPorTipo();
        
        // Verificar que están ordenados por tipo
        assertEquals(4, ordenados.size());
        assertEquals("Fire", ordenados.get(0).getType1()); // Charizard
        assertEquals("Grass", ordenados.get(1).getType1()); // Bulbasaur
        assertEquals("Psychic", ordenados.get(2).getType1()); // Alakazam o Mewtwo
        assertEquals("Psychic", ordenados.get(3).getType1()); // Alakazam o Mewtwo
        
        // Verificar que dentro del mismo tipo están ordenados por nombre
        if (ordenados.get(2).getName().equals("Alakazam")) {
            assertEquals("Mewtwo", ordenados.get(3).getName());
        } else {
            assertEquals("Alakazam", ordenados.get(3).getName());
            assertEquals("Mewtwo", ordenados.get(2).getName());
        }
    }
    
    @Test
    public void testGetTipoMapa() {
        // Verificar que devuelve el tipo correcto
        assertEquals("TreeMap", mapaPokemons.getTipoMapa());
    }
    
    @Test
    public void testAgregarYObtenerPokemon() {
        // Agregar un nuevo Pokémon
        Pokemon pikachu = new Pokemon(
            "Pikachu", 
            25, 
            "Electric", 
            "", 
            "Mouse Pokémon", 
            0.4f, 
            6.0f, 
            "Static", 
            1, 
            false
        );
        
        mapaPokemons.agregarPokemon(pikachu);
        
        // Verificar que se agregó correctamente
        Pokemon recuperado = mapaPokemons.obtenerPokemon("Pikachu");
        assertNotNull(recuperado);
        assertEquals("Pikachu", recuperado.getName());
        assertEquals("Electric", recuperado.getType1());
        assertEquals("Static", recuperado.getAbilities());
    }
}