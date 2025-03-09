package com.pokemon.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para el patrón Factory
 */
public class PokemonMapFactoryTest {
    
    @Test
    public void testCrearMapaPorTipo() {
        // Probar creación de HashMapPokemon
        MapaPokemons hashMap = PokemonMapFactory.crearMapa(1);
        assertNotNull(hashMap);
        assertEquals("HashMap", hashMap.getTipoMapa());
        assertTrue(hashMap instanceof HashMapPokemon);
        
        // Probar creación de TreeMapPokemon
        MapaPokemons treeMap = PokemonMapFactory.crearMapa(2);
        assertNotNull(treeMap);
        assertEquals("TreeMap", treeMap.getTipoMapa());
        assertTrue(treeMap instanceof TreeMapPokemon);
        
        // Probar creación de LinkedHashMapPokemon
        MapaPokemons linkedHashMap = PokemonMapFactory.crearMapa(3);
        assertNotNull(linkedHashMap);
        assertEquals("LinkedHashMap", linkedHashMap.getTipoMapa());
        assertTrue(linkedHashMap instanceof LinkedHashMapPokemon);
    }
    
    @Test
    public void testCrearMapaPorNombre() {
        // Probar creación por nombre
        MapaPokemons hashMap = PokemonMapFactory.crearMapaPorNombre("HashMap");
        assertNotNull(hashMap);
        assertEquals("HashMap", hashMap.getTipoMapa());
        assertTrue(hashMap instanceof HashMapPokemon);
        
        // Mayúsculas/minúsculas no debería afectar
        MapaPokemons treeMap = PokemonMapFactory.crearMapaPorNombre("treemap");
        assertNotNull(treeMap);
        assertEquals("TreeMap", treeMap.getTipoMapa());
        assertTrue(treeMap instanceof TreeMapPokemon);
        
        MapaPokemons linkedHashMap = PokemonMapFactory.crearMapaPorNombre("LinkedHashMap");
        assertNotNull(linkedHashMap);
        assertEquals("LinkedHashMap", linkedHashMap.getTipoMapa());
        assertTrue(linkedHashMap instanceof LinkedHashMapPokemon);
    }
    
    @Test
    public void testTipoInvalido() {
        // Verificar que lanza excepción para un tipo numérico inválido
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            PokemonMapFactory.crearMapa(4);
        });
        
        assertTrue(exception.getMessage().contains("Tipo de mapa no válido"));
        
        // Verificar que lanza excepción para un nombre inválido
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            PokemonMapFactory.crearMapaPorNombre("InvalidMap");
        });
        
        assertTrue(exception2.getMessage().contains("Tipo de mapa no válido"));
    }
}