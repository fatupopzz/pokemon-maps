import React, { useContext, useMemo, useState } from 'react';
import { PokemonContext } from '../context/PokemonContext';
import MapTypeSelector from '../components/MapTypeSelector';
import SearchBar from '../components/SearchBar';
import PokemonList from '../components/PokemonList';
import { searchPokemonsByName, searchPokemonsByAbility } from '../services/PokemonService';

/**
 * Página principal que muestra todos los Pokémon
 */
const HomePage = () => {
  const { allPokemons, loading } = useContext(PokemonContext);
  const [searchTerm, setSearchTerm] = useState('');
  const [abilityFilter, setAbilityFilter] = useState('');
  
  // Filtrar los Pokémon según los criterios de búsqueda
  const filteredPokemons = useMemo(() => {
    if (loading) return [];
    
    let result = Object.values(allPokemons);
    
    if (searchTerm) {
      result = searchPokemonsByName(allPokemons, searchTerm);
    }
    
    if (abilityFilter) {
      result = searchPokemonsByAbility(
        result.reduce((obj, pokemon) => {
          obj[pokemon.name] = pokemon;
          return obj;
        }, {}), 
        abilityFilter
      );
    }
    
    return result;
  }, [allPokemons, searchTerm, abilityFilter, loading]);
  
  return (
    <div>
      <h1 className="text-2xl font-bold text-blue-900 mb-6">
        Explorador de Pokémon
      </h1>
      
      <MapTypeSelector />
      
      {!loading && (
        <>
          <SearchBar 
            onSearch={setSearchTerm}
            onFilterByAbility={setAbilityFilter}
          />
          
          <PokemonList pokemons={filteredPokemons} />
        </>
      )}
      
      {loading && (
        <div className="flex items-center justify-center h-64">
          <div className="text-center">
            <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
            <p className="text-lg text-gray-600">Cargando Pokémon...</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default HomePage;