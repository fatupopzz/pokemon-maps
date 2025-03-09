import React, { useState } from 'react';

/**
 * Componente para buscar Pokémon por nombre o filtrar por habilidad
 */
const SearchBar = ({ onSearch, onFilterByAbility }) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [abilityFilter, setAbilityFilter] = useState('');
  
  // Manejar la búsqueda cuando cambia el término de búsqueda
  const handleSearchChange = (e) => {
    const value = e.target.value;
    setSearchTerm(value);
    onSearch(value);
  };
  
  // Manejar el filtro por habilidad
  const handleAbilityChange = (e) => {
    const value = e.target.value;
    setAbilityFilter(value);
    onFilterByAbility(value);
  };
  
  // Limpiar todos los filtros
  const clearFilters = () => {
    setSearchTerm('');
    setAbilityFilter('');
    onSearch('');
    onFilterByAbility('');
  };
  
  return (
    <div className="bg-white bg-opacity-80 rounded-xl p-4 shadow-md backdrop-blur-sm mb-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <label htmlFor="search" className="block text-sm font-medium text-gray-700 mb-1">
            Buscar por nombre:
          </label>
          <div className="relative">
            <input
              type="text"
              id="search"
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500"
              placeholder="Pikachu, Charizard, etc..."
              value={searchTerm}
              onChange={handleSearchChange}
            />
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <svg className="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
              </svg>
            </div>
          </div>
        </div>
        
        <div>
          <label htmlFor="ability" className="block text-sm font-medium text-gray-700 mb-1">
            Filtrar por habilidad:
          </label>
          <input
            type="text"
            id="ability"
            className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500"
            placeholder="Static, Blaze, etc..."
            value={abilityFilter}
            onChange={handleAbilityChange}
          />
        </div>
      </div>
      
      {(searchTerm || abilityFilter) && (
        <div className="flex justify-end mt-3">
          <button
            className="text-sm text-blue-600 hover:text-blue-800"
            onClick={clearFilters}
          >
            Limpiar filtros
          </button>
        </div>
      )}
    </div>
  );
};

export default SearchBar;