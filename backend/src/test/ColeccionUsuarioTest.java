package com.pokemon.service;

import com.pokemon.model.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas para la colección del usuario
 */
public class ColeccionUsuarioTest {
    
    private MapaPokemons mapaPokemons;
    private ColeccionUsuario coleccionUsuario;
    
    @BeforeEach
    public void setUp() {
        // Crear un mapa con algunos Pokémon
        mapaPokemons = new HashMapPokemon();
        
        // Agregar varios Pokémon al mapa para las pruebas
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
            "Gyarados", 
            130, 
            "Water", 
            "Flying", 
            "Atrocious Pokémon", 
            6.5f, 
            235.0f, 
            "Intimidate", 
            1, 
            false
        ));
        
        // Crear la colección del usuario
        coleccionUsuario = new ColeccionUsuario(mapaPokemons);
    }
    
    @Test
    public void testAgregarPokemon() {
        // Verificar que inicialmente la colección está vacía
        assertEquals(0, coleccionUsuario.cantidadPokemon());
        
        // Agregar un Pokémon a la colección
        boolean resultado = coleccionUsuario.agregarPokemon("Pikachu");
        
        // Verificar que se agregó correctamente
        assertTrue(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Intentar agregar el mismo Pokémon de nuevo (no debería permitirlo)
        resultado = coleccionUsuario.agregarPokemon("Pikachu");
        assertFalse(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Intentar agregar un Pokémon que no existe
        resultado = coleccionUsuario.agregarPokemon("MewTwo");
        assertFalse(resultado);
        assertEquals(1, coleccionUsuario.cantidadPokemon());
        
        // Agregar más Pokémon válidos
        assertTrue(coleccionUsuario.agregarPokemon("Charizard"));
        assertTrue(coleccionUsuario.agregarPokemon("Bulbasaur"));
        assertEquals(3, coleccionUsuario.cantidadPokemon());
    }
    
    @Test
    public void testObtenerNombresPokemon() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Charizard");
        
        // Obtener los nombres
        Set<String> nombres = coleccionUsuario.obtenerNombresPokemon();
        
        // Verificar que contiene los nombres correctos
        assertEquals(2, nombres.size());
        assertTrue(nombres.contains("Pikachu"));
        assertTrue(nombres.contains("Charizard"));
        assertFalse(nombres.contains("Bulbasaur"));
    }
    
    @Test
    public void testObtenerPokemons() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Gyarados");
        
        // Obtener los objetos Pokémon
        List<Pokemon> pokemons = coleccionUsuario.obtenerPokemons();
        
        // Verificar que se obtienen los Pokémon correctos
        assertEquals(2, pokemons.size());
        
        // Verificar que se obtienen las instancias correctas
        boolean encontroPikachu = false;
        boolean encontroGyarados = false;
        
        for (Pokemon pokemon : pokemons) {
            if (pokemon.getName().equals("Pikachu")) {
                encontroPikachu = true;
                assertEquals("Electric", pokemon.getType1());
            } else if (pokemon.getName().equals("Gyarados")) {
                encontroGyarados = true;
                assertEquals("Water", pokemon.getType1());
                assertEquals("Flying", pokemon.getType2());
            }
        }
        
        assertTrue(encontroPikachu);
        assertTrue(encontroGyarados);
    }
    
    @Test
    public void testObtenerPokemonsPorTipo() {
        // Agregar Pokémon de diferentes tipos
        coleccionUsuario.agregarPokemon("Bulbasaur");  // Grass/Poison
        coleccionUsuario.agregarPokemon("Charizard");  // Fire/Flying
        coleccionUsuario.agregarPokemon("Gyarados");   // Water/Flying
        coleccionUsuario.agregarPokemon("Pikachu");    // Electric
        
        // Obtener ordenados por tipo
        List<Pokemon> ordenados = coleccionUsuario.obtenerPokemonsPorTipo();
        
        // Verificar que están ordenados correctamente por tipo primario
        assertEquals(4, ordenados.size());
        assertEquals("Electric", ordenados.get(0).getType1()); // Pikachu
        assertEquals("Fire", ordenados.get(1).getType1());     // Charizard
        assertEquals("Grass", ordenados.get(2).getType1());    // Bulbasaur
        assertEquals("Water", ordenados.get(3).getType1());    // Gyarados
    }
    
    @Test
    public void testToJson() {
        // Agregar algunos Pokémon a la colección
        coleccionUsuario.agregarPokemon("Pikachu");
        coleccionUsuario.agregarPokemon("Charizard");
        
        // Obtener el JSON
        String json = coleccionUsuario.toJson();
        
        // Verificar que el JSON contiene la información de los Pokémon
        assertTrue(json.contains("\"name\":\"Pikachu\""));
        assertTrue(json.contains("\"name\":\"Charizard\""));
        assertTrue(json.contains("\"type1\":\"Electric\""));
        assertTrue(json.contains("\"type1\":\"Fire\""));
        assertTrue(json.startsWith("["));
        assertTrue(json.endsWith("]"));
    }
}