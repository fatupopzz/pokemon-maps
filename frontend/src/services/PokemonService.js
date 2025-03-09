import Papa from 'papaparse';

/**
 * Esta función carga los datos de Pokémon desde el CSV
 * @param {string} mapType - Tipo de mapa (HashMap, TreeMap, LinkedHashMap)
 * @returns {Promise<Object>} - Un objeto con los datos de Pokémon
 */
export const fetchAllPokemons = async (mapType) => {
  try {
    // Usar la URL basada en PUBLIC_URL para permitir rutas relativas en GitHub Pages
    const csvUrl = `${process.env.PUBLIC_URL}/data/pokemon_data_pokeapi.csv`;
    
    console.log('Intentando cargar CSV desde:', csvUrl);
    
    const response = await fetch(csvUrl);
    console.log('Estado de la respuesta:', response.status, response.statusText);
    
    if (!response.ok) {
      throw new Error(`No se pudo cargar el archivo: ${response.status} ${response.statusText}`);
    }
    
    const csvText = await response.text();
    console.log('Primeros 100 caracteres del CSV:', csvText.substring(0, 100));
    
    // Convertir el CSV a un objeto según el tipo de Map
    return parseCSVToMap(csvText, mapType);
  } catch (error) {
    console.error('Error en fetchAllPokemons:', error);
    // Registrar más detalles del error para depuración
    if (error instanceof TypeError) {
      console.error('Error de red o CORS. Detalles:', error.message);
    }
    throw error;
  }
};

/**
 * Convierte los datos CSV a un objeto según el tipo de Map seleccionado
 * @param {string} csvText - Texto CSV
 * @param {string} mapType - Tipo de Map (HashMap, TreeMap, LinkedHashMap)
 * @returns {Object} - Map de Pokémon
 */
const parseCSVToMap = (csvText, mapType) => {
  return new Promise((resolve, reject) => {
    Papa.parse(csvText, {
      header: true,
      dynamicTyping: true,
      skipEmptyLines: true,
      complete: (results) => {
        try {
          console.log('Resultados de Papa Parse:', {
            rows: results.data.length,
            firstRow: results.data[0],
            fields: results.meta.fields
          });
          
          // Transformar los datos según el tipo de Map
          const pokemonMap = createPokemonMap(results.data, mapType);
          resolve(pokemonMap);
        } catch (error) {
          console.error('Error procesando los datos CSV:', error);
          reject(error);
        }
      },
      error: (error) => {
        console.error('Error en Papa Parse:', error);
        reject(error);
      }
    });
  });
};

/**
 * Crea un Map de Pokémon según el tipo especificado
 * @param {Array} pokemonArray - Array de datos de Pokémon
 * @param {string} mapType - Tipo de Map a crear
 * @returns {Object} - Map de Pokémon
 */
const createPokemonMap = (pokemonArray, mapType) => {
  let pokemonMap = {};
  
  switch(mapType) {
    case "HashMap":
      // Un objeto normal en JavaScript (no garantiza orden)
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    case "TreeMap":
      // Simulamos TreeMap ordenando las claves alfabéticamente
      const sorted = [...pokemonArray].sort((a, b) => 
        a.Name.localeCompare(b.Name)
      );
      
      pokemonMap = sorted.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    case "LinkedHashMap":
      // Simulamos LinkedHashMap manteniendo el orden de inserción
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        map[pokemon.Name] = transformPokemonData(pokemon);
        return map;
      }, {});
      break;
      
    default:
      throw new Error(`Tipo de mapa no válido: ${mapType}`);
  }
  
  console.log(`Creado ${mapType} con ${Object.keys(pokemonMap).length} Pokémon`);
  
  return pokemonMap;
};

/**
 * Transforma los datos crudos del Pokémon al formato deseado
 * @param {Object} rawPokemon - Datos crudos del Pokémon desde el CSV
 * @returns {Object} - Datos del Pokémon transformados
 */
const transformPokemonData = (rawPokemon) => {
  console.log('Transformando datos de Pokémon raw:', JSON.stringify(rawPokemon).substring(0, 100));
  return {
    name: rawPokemon.Name,
    pokedexNumber: rawPokemon["Pokedex Number"],
    type1: rawPokemon.Type1,
    type2: rawPokemon.Type2 || "",
    classification: rawPokemon.Classification,
    height: rawPokemon["Height (m)"],
    weight: rawPokemon["Weight (kg)"],
    abilities: rawPokemon.Abilities || "Unknown",
    generation: rawPokemon.Generation,
    legendary: rawPokemon["Legendary Status"] === "True" || rawPokemon["Legendary Status"] === true
  };
};

/**
 * Filtra los Pokémon por nombre
 * @param {Object} pokemons - Map de Pokémon
 * @param {string} searchTerm - Término de búsqueda
 * @returns {Array} - Array de Pokémon filtrados
 */
export const searchPokemonsByName = (pokemons, searchTerm) => {
  if (!searchTerm) return Object.values(pokemons);
  
  return Object.values(pokemons).filter(pokemon => 
    pokemon.name.toLowerCase().includes(searchTerm.toLowerCase())
  );
};

/**
 * Filtra los Pokémon por habilidad
 * @param {Object} pokemons - Map de Pokémon
 * @param {string} ability - Habilidad a buscar
 * @returns {Array} - Array de Pokémon filtrados
 */
export const searchPokemonsByAbility = (pokemons, ability) => {
  if (!ability) return Object.values(pokemons);
  
  return Object.values(pokemons).filter(pokemon => 
    pokemon.abilities.toLowerCase().includes(ability.toLowerCase())
  );
};

/**
 * Agrupa los Pokémon por tipo
 * @param {Array} pokemons - Array de Pokémon
 * @returns {Object} - Pokémon agrupados por tipo
 */
export const getPokemonsByType = (pokemons) => {
  return pokemons.reduce((groups, pokemon) => {
    const type = pokemon.type1;
    if (!groups[type]) {
      groups[type] = [];
    }
    groups[type].push(pokemon);
    return groups;
  }, {});
};