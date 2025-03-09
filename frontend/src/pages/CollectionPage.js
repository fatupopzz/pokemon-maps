import React, { useContext } from 'react';
import { PokemonContext } from '../context/PokemonContext';
import UserCollection from '../components/UserCollection';

/**
 * Página que muestra la colección de Pokémon del usuario
 */
const CollectionPage = () => {
  const { userCollection, loading } = useContext(PokemonContext);
  
  return (
    <div>
      <h1 className="text-2xl font-bold text-blue-900 mb-6">
        Mi Colección de Pokémon {userCollection.length > 0 && `(${userCollection.length})`}
      </h1>
      
      {!loading && <UserCollection />}
      
      {loading && (
        <div className="flex items-center justify-center h-64">
          <div className="text-center">
            <div className="w-16 h-16 border-4 border-blue-500 border-t-transparent rounded-full animate-spin mx-auto mb-4"></div>
            <p className="text-lg text-gray-600">Cargando datos...</p>
          </div>
        </div>
      )}
    </div>
  );
};

export default CollectionPage;