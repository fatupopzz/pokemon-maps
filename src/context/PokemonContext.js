import { createContext } from 'react';

// Contexto para compartir el estado de los Pokémon en toda la aplicación
export const PokemonContext = createContext({
  // Tipo de mapa seleccionado (HashMap, TreeMap, LinkedHashMap)
  mapType: 'HashMap',
  // Función para cambiar el tipo de mapa
  changeMapType: () => {},
  // Todos los Pokémon cargados del CSV
  allPokemons: {},
  // Colección de Pokémon del usuario
  userCollection: [],
  // Función para agregar un Pokémon a la colección
  addToCollection: () => false,
  // Estado de carga
  loading: true,
  // Mensajes de error
  error: null,
  // Función para establecer el error
  setError: () => {}
});