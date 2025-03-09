import React, { useContext } from 'react';
import { PokemonContext } from '../context/PokemonContext';

/**
 * Componente para seleccionar el tipo de Map a utilizar
 * (HashMap, TreeMap, LinkedHashMap)
 */
const MapTypeSelector = () => {
  const { mapType, changeMapType, loading } = useContext(PokemonContext);
  
  // Información sobre cada tipo de Map para mostrar al usuario
  const mapTypes = [
    {
      id: 'HashMap',
      name: 'HashMap',
      description: 'Acceso rápido O(1), sin orden específico'
    },
    {
      id: 'TreeMap',
      name: 'TreeMap',
      description: 'Ordenado por nombre O(log n)'
    },
    {
      id: 'LinkedHashMap',
      name: 'LinkedHashMap',
      description: 'Mantiene orden de inserción O(1)'
    }
  ];
  
  return (
    <div className="bg-white bg-opacity-80 backdrop-blur-sm rounded-lg shadow-md p-4 mb-6">
      <h2 className="text-lg font-semibold text-blue-900 mb-3">Selecciona el tipo de Map</h2>
      
      <div className="flex flex-wrap gap-3">
        {mapTypes.map((type) => (
          <button
            key={type.id}
            className={`flex-1 px-4 py-3 rounded-lg transition-colors border ${
              mapType === type.id 
                ? 'bg-blue-600 text-white border-blue-700' 
                : 'bg-white hover:bg-blue-50 border-gray-200 text-gray-700'
            }`}
            onClick={() => changeMapType(type.id)}
            disabled={loading}
          >
            <div className="font-medium">{type.name}</div>
            <div className="text-xs mt-1 opacity-80">{type.description}</div>
          </button>
        ))}
      </div>
      
      {loading && (
        <div className="flex items-center justify-center mt-4 text-sm text-blue-700">
          <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-blue-700" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
          </svg>
          Cargando datos...
        </div>
      )}
    </div>
  );
};

export default MapTypeSelector;