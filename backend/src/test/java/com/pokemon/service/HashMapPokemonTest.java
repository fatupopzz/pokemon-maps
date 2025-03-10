package com.pokemon.service;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pokemon.model.Pokemon;

/**
 * Pruebas para la implementación HashMapPokemon
 * Autor: Fatima Navarro
 */
public class HashMapPokemonTest {
    
    private MapaPokemons mapaPokemons;
    private Pokemon bulbasaur;
    private Pokemon charmander;
    private Pokemon squirtle;
    
    @BeforeEach
    public void setUp() {
        // Inicializar un mapa de tipo HashMap
        mapaPokemons = new HashMapPokemon();
        
        // Crear algunos Pokémon para las pruebas
        bulbasaur = new Pokemon(
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
        );
        
        charmander = new Pokemon(
            "Charmander", 
            4, 
            "Fire", 
            "", 
            "Lizard Pokémon", 
            0.6f, 
            8.5f, 
            "Blaze", 
            1, 
            false
        );
        
        squirtle = new Pokemon(
            "Squirtle", 
            7, 
            "Water", 
            "", 
            "Tiny Turtle Pokémon", 
            0.5f, 
            9.0f, 
            "Torrent", 
            1, 
            false
        );
        
        // Agregar los Pokémon al mapa
        mapaPokemons.agregarPokemon(bulbasaur);
        mapaPokemons.agregarPokemon(charmander);
        mapaPokemons.agregarPokemon(squirtle);
    }
    
    @Test
    public void testAgregarPokemon() {
        // Verificar que los Pokémon se agregaron correctamente
        assertEquals(3, mapaPokemons.obtenerTodos().size());
        
        // Verificar que se pueden recuperar por nombre
        assertNotNull(mapaPokemons.obtenerPokemon("Bulbasaur"));
        assertNotNull(mapaPokemons.obtenerPokemon("Charmander"));
        assertNotNull(mapaPokemons.obtenerPokemon("Squirtle"));
    }
    
    @Test
    public void testExistePokemon() {
        // Verificar que existePokemon funciona correctamente
        assertTrue(mapaPokemons.existePokemon("Bulbasaur"));
        assertTrue(mapaPokemons.existePokemon("Charmander"));
        assertTrue(mapaPokemons.existePokemon("Squirtle"));
        assertFalse(mapaPokemons.existePokemon("Pikachu"));
    }
    
    @Test
    public void testObtenerPokemon() {
        // Verificar que se obtiene el Pokémon correcto
        Pokemon pokemon = mapaPokemons.obtenerPokemon("Bulbasaur");
        assertEquals("Bulbasaur", pokemon.getName());
        assertEquals("Grass", pokemon.getType1());
        assertEquals("Poison", pokemon.getType2());
        
        // Verificar que devuelve null para un Pokémon inexistente
        assertNull(mapaPokemons.obtenerPokemon("Pikachu"));
    }
    
    @Test
    public void testBuscarPorHabilidad() {
        // Verificar que se encuentran los Pokémon por habilidad
        Map<String, Pokemon> conOvergrow = mapaPokemons.buscarPorHabilidad("Overgrow");
        assertEquals(1, conOvergrow.size());
        assertTrue(conOvergrow.containsKey("Bulbasaur"));
        
        Map<String, Pokemon> conBlaze = mapaPokemons.buscarPorHabilidad("Blaze");
        assertEquals(1, conBlaze.size());
        assertTrue(conBlaze.containsKey("Charmander"));
        
        // Verificar que devuelve un mapa vacío para una habilidad inexistente
        Map<String, Pokemon> sinHabilidad = mapaPokemons.buscarPorHabilidad("Invisible");
        assertEquals(0, sinHabilidad.size());
    }
    

    @Test
    public void testObtenerOrdenadosPorTipo() {
        // Agregar un Pokémon adicional del mismo tipo que uno existente
        Pokemon venusaur = new Pokemon(
            "Venusaur", 
            3, 
            "Grass", 
            "Poison", 
            "Seed Pokémon", 
            2.0f, 
            100.0f, 
            "Overgrow", 
            1, 
            false
        );
        mapaPokemons.agregarPokemon(venusaur);
        
        // Obtener ordenados por tipo
        List<Pokemon> ordenados = mapaPokemons.obtenerOrdenadosPorTipo();
        
        // Verificar que están ordenados por tipo
        assertEquals(4, ordenados.size());
        assertEquals("Fire", ordenados.get(0).getType1()); // Charmander
        assertEquals("Grass", ordenados.get(1).getType1()); // Bulbasaur o Venusaur
        assertEquals("Grass", ordenados.get(2).getType1()); // Bulbasaur o Venusaur
        assertEquals("Water", ordenados.get(3).getType1()); // Squirtle
        
        // Verificar que los del mismo tipo están ordenados por nombre
        if (ordenados.get(1).getName().equals("Bulbasaur")) {
            assertEquals("Venusaur", ordenados.get(2).getName());
        } else {
            assertEquals("Bulbasaur", ordenados.get(2).getName());
            assertEquals("Venusaur", ordenados.get(1).getName());
        }
    }
    
    @Test
    public void testGetTipoMapa() {
        // Verificar que devuelve el tipo correcto
        assertEquals("HashMap", mapaPokemons.getTipoMapa());
    }
}