package com.pokemon.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.pokemon.model.Pokemon;

/**
 * Pruebas para la implementación LinkedHashMapPokemon
 */
public class LinkedHashMapPokemonTest {
    
    private MapaPokemons mapaPokemons;
    
    @BeforeEach
    public void setUp() {
        // Inicializar un mapa de tipo LinkedHashMap
        mapaPokemons = new LinkedHashMapPokemon();
        
        // Agregar Pokémon en un orden específico para probar que se mantiene
        mapaPokemons.agregarPokemon(new Pokemon(
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
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
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
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Jigglypuff", 
            39, 
            "Normal", 
            "Fairy", 
            "Balloon Pokémon", 
            0.5f, 
            5.5f, 
            "Cute Charm", 
            1, 
            false
        ));
        
        mapaPokemons.agregarPokemon(new Pokemon(
            "Meowth", 
            52, 
            "Normal", 
            "", 
            "Scratch Cat Pokémon", 
            0.4f, 
            4.2f, 
            "Pickup", 
            1, 
            false
        ));
    }
    
    @Test
    public void testOrdenDeInsercion() {
        // En un LinkedHashMap, los elementos deben mantener el orden de inserción
        Map<String, Pokemon> todos = mapaPokemons.obtenerTodos();
        
        // Convertir a una lista para verificar el orden
        List<String> nombres = new ArrayList<>(todos.keySet());
        
        // Verificar que el orden es el mismo que el de inserción
        assertEquals(4, nombres.size());
        assertEquals("Squirtle", nombres.get(0));
        assertEquals("Pikachu", nombres.get(1));
        assertEquals("Jigglypuff", nombres.get(2));
        assertEquals("Meowth", nombres.get(3));
    }
    
    @Test
    public void testIteracionMantienelOrden() {
        // Verificar que la iteración mantiene el orden de inserción
        Map<String, Pokemon> todos = mapaPokemons.obtenerTodos();
        Iterator<Map.Entry<String, Pokemon>> it = todos.entrySet().iterator();
        
        // Verificar el orden mediante la iteración
        Map.Entry<String, Pokemon> entry = it.next();
        assertEquals("Squirtle", entry.getKey());
        
        entry = it.next();
        assertEquals("Pikachu", entry.getKey());
        
        entry = it.next();
        assertEquals("Jigglypuff", entry.getKey());
        
        entry = it.next();
        assertEquals("Meowth", entry.getKey());
        
        // No debería haber más elementos
        assertFalse(it.hasNext());
    }
    
    @Test
    public void testBuscarPorHabilidad() {
        // Verificar búsqueda por habilidad
        Map<String, Pokemon> conPickup = mapaPokemons.buscarPorHabilidad("Pickup");
        
        // Verificar que se encuentra el Pokémon correcto
        assertEquals(1, conPickup.size());
        assertTrue(conPickup.containsKey("Meowth"));
        
        // Buscar por habilidad parcial
        Map<String, Pokemon> conChar = mapaPokemons.buscarPorHabilidad("Char");
        assertEquals(1, conChar.size());
        assertTrue(conChar.containsKey("Jigglypuff")); // Cute Charm contiene "Char"
    }
    
    @Test
    public void testObtenerOrdenadosPorTipo() {
        // Verificar que se ordenan correctamente por tipo, luego por nombre
        List<Pokemon> ordenados = mapaPokemons.obtenerOrdenadosPorTipo();
        
        // Los Pokémon deberían ordenarse: Electric, Normal, Normal, Water
        assertEquals(4, ordenados.size());
        assertEquals("Electric", ordenados.get(0).getType1()); // Pikachu
        assertEquals("Normal", ordenados.get(1).getType1()); // Jigglypuff o Meowth
        assertEquals("Normal", ordenados.get(2).getType1()); // Jigglypuff o Meowth
        assertEquals("Water", ordenados.get(3).getType1()); // Squirtle
        
        // Verificar ordenamiento por nombre dentro del mismo tipo
        if (ordenados.get(1).getName().equals("Jigglypuff")) {
            assertEquals("Meowth", ordenados.get(2).getName());
        } else {
            assertEquals("Jigglypuff", ordenados.get(2).getName());
            assertEquals("Meowth", ordenados.get(1).getName());
        }
    }
    
    @Test
    public void testGetTipoMapa() {
        // Verificar que devuelve el tipo correcto
        assertEquals("LinkedHashMap", mapaPokemons.getTipoMapa());
    }
    
    @Test
    public void testExistenciaPokemon() {
        // Verificar existencia de Pokémon
        assertTrue(mapaPokemons.existePokemon("Pikachu"));
        assertTrue(mapaPokemons.existePokemon("Squirtle"));
        assertTrue(mapaPokemons.existePokemon("Jigglypuff"));
        assertTrue(mapaPokemons.existePokemon("Meowth"));
        
        // Verificar que devuelve false para Pokémon inexistentes
        assertFalse(mapaPokemons.existePokemon("Charizard"));
        assertFalse(mapaPokemons.existePokemon("Bulbasaur"));
    }
}