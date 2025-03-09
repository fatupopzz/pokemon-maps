import Papa from 'papaparse';

/**
 * Esta función carga los datos de Pokémon desde el CSV
 * @param {string} mapType - Tipo de mapa (HashMap, TreeMap, LinkedHashMap)
 * @returns {Promise<Object>} - Un objeto con los datos de Pokémon
 */
export const fetchAllPokemons = async (mapType) => {
  try {
    // Ruta para el CSV
    const csvUrl = `${process.env.PUBLIC_URL}/data/pokemon_data_pokeapi.csv`;
    
    // Cargar el archivo CSV
    const response = await fetch(csvUrl);
    if (!response.ok) {
      console.error(`Error al cargar el CSV: ${response.statusText}`);
      return {};
    }
    
    const csvText = await response.text();
    
    // Convertir el CSV a un objeto según el tipo de Map
    return parseCSVToMap(csvText, mapType);
  } catch (error) {
    console.error('Error en fetchAllPokemons:', error);
    return {};
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
          // Ver los nombres de las columnas para debug
          console.log("Columnas encontradas:", results.meta.fields);
          
          // Ver el primer Pokémon para entender la estructura
          if (results.data.length > 0) {
            console.log("Primer Pokémon:", results.data[0]);
          }
          
          // Transformar los datos según el tipo de Map
          const pokemonMap = createPokemonMap(results.data, mapType);
          resolve(pokemonMap);
        } catch (error) {
          console.error("Error procesando CSV:", error);
          reject(error);
        }
      },
      error: (error) => {
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
  
  // Comprobar si tenemos datos
  if (!pokemonArray || pokemonArray.length === 0) {
    console.error("No hay datos de Pokémon para procesar");
    return {};
  }
  
  // Comprobar los nombres de las propiedades del primer Pokémon
  const firstPokemon = pokemonArray[0];
  const propertyNames = Object.keys(firstPokemon);
  console.log("Nombres de propiedades:", propertyNames);
  
  switch(mapType) {
    case "HashMap":
      // Un objeto normal en JavaScript (no garantiza orden)
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        // Buscar el campo correcto para el nombre según los datos
        const nameField = getNameField(pokemon);
        if (nameField) {
          map[pokemon[nameField]] = transformPokemonData(pokemon);
        }
        return map;
      }, {});
      break;
      
    case "TreeMap":
      // Simulamos TreeMap ordenando las claves alfabéticamente
      const sorted = [...pokemonArray].sort((a, b) => {
        const nameFieldA = getNameField(a);
        const nameFieldB = getNameField(b);
        if (!nameFieldA || !nameFieldB) return 0;
        return a[nameFieldA].localeCompare(b[nameFieldB]);
      });
      
      pokemonMap = sorted.reduce((map, pokemon) => {
        const nameField = getNameField(pokemon);
        if (nameField) {
          map[pokemon[nameField]] = transformPokemonData(pokemon);
        }
        return map;
      }, {});
      break;
      
    case "LinkedHashMap":
      // Simulamos LinkedHashMap manteniendo el orden de inserción
      pokemonMap = pokemonArray.reduce((map, pokemon) => {
        const nameField = getNameField(pokemon);
        if (nameField) {
          map[pokemon[nameField]] = transformPokemonData(pokemon);
        }
        return map;
      }, {});
      break;
      
    default:
      throw new Error(`Tipo de mapa no válido: ${mapType}`);
  }
  
  return pokemonMap;
};

/**
 * Encuentra el campo correcto que contiene el nombre del Pokémon
 * @param {Object} pokemon - Datos del Pokémon
 * @returns {string|null} - Nombre del campo para el nombre del Pokémon
 */
const getNameField = (pokemon) => {
  // Campos posibles para el nombre, en orden de prioridad
  const possibleFields = ['name', 'Name', 'nombre', 'Nombre', 'pokemon', 'Pokemon'];
  
  for (const field of possibleFields) {
    if (pokemon[field]) {
      return field;
    }
  }
  
  // Si no encuentra ningún campo, usa la primera propiedad que no sea nula
  for (const key in pokemon) {
    if (pokemon[key] && typeof pokemon[key] === 'string') {
      console.log(`Usando '${key}' como campo nombre fallback`);
      return key;
    }
  }
  
  return null;
};

/**
 * Transforma los datos crudos del Pokémon al formato deseado
 * @param {Object} rawPokemon - Datos crudos del Pokémon desde el CSV
 * @returns {Object} - Datos del Pokémon transformados
 */
const transformPokemonData = (rawPokemon) => {
  // Mapeo flexible que busca en diferentes nombres de campo
  const getValue = (possibleKeys, defaultValue) => {
    for (const key of possibleKeys) {
      if (rawPokemon[key] !== undefined && rawPokemon[key] !== null) {
        return rawPokemon[key];
      }
    }
    return defaultValue;
  };
  
  // Detectar los campos para cada propiedad
  const nameField = getNameField(rawPokemon) || 'Name';
  
  return {
    name: getValue([nameField, 'Name', 'nombre', 'Nombre'], 'Unknown'),
    type1: getValue(['Type1', 'Type 1', 'Tipo1', 'Tipo 1', 'PrimaryType', 'Primary Type'], 'Normal'),
    type2: getValue(['Type2', 'Type 2', 'Tipo2', 'Tipo 2', 'SecondaryType', 'Secondary Type'], ''),
    total: getValue(['Total', 'total', 'Total Stats'], 0),
    hp: getValue(['HP', 'hp', 'Hit Points', 'Health'], 0),
    attack: getValue(['Attack', 'attack', 'Atk', 'atk', 'Ataque'], 0),
    defense: getValue(['Defense', 'defense', 'Def', 'def', 'Defensa'], 0),
    spAtk: getValue(['Sp. Atk', 'SpAtk', 'Sp.Atk', 'Special Attack', 'special_attack', 'sp_atk', 'Ataque Especial'], 0),
    spDef: getValue(['Sp. Def', 'SpDef', 'Sp.Def', 'Special Defense', 'special_defense', 'sp_def', 'Defensa Especial'], 0),
    speed: getValue(['Speed', 'speed', 'spd', 'Spd', 'Velocidad'], 0),
    generation: getValue(['Generation', 'generation', 'gen', 'Gen', 'Generación'], 1),
    legendary: getValue(
      ['Legendary', 'legendary', 'is_legendary', 'IsLegendary', 'Legendario'], 
      false
    ) === true || getValue(
      ['Legendary', 'legendary', 'is_legendary', 'IsLegendary', 'Legendario'], 
      ''
    ) === 'True',
    ability: getValue(['Ability', 'ability', 'Abilities', 'abilities', 'Habilidad', 'habilidad'], 'Unknown')
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
    pokemon.ability && pokemon.ability.toLowerCase().includes(ability.toLowerCase())
  );
};

/**
 * Ordena los Pokémon por tipo
 * @param {Array} pokemons - Array de Pokémon
 * @returns {Object} - Pokémon agrupados por tipo
 */
export const getPokemonsByType = (pokemons) => {
  return pokemons.reduce((groups, pokemon) => {
    const type = pokemon.type1 || "Unknown";
    if (!groups[type]) {
      groups[type] = [];
    }
    groups[type].push(pokemon);
    return groups;
  }, {});
};