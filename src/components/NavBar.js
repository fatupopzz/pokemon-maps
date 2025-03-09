import React, { useContext, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import { PokemonContext } from '../context/PokemonContext';

/**
 * Componente de barra de navegación
 */
const NavBar = () => {
  const location = useLocation();
  const { userCollection, mapType } = useContext(PokemonContext);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  
  // Enlaces de navegación
  const navLinks = [
    { path: '/', label: 'Inicio' },
    { path: '/collection', label: `Mi Colección (${userCollection.length})` }
  ];
  
  return (
    <nav className="bg-gradient-to-r from-blue-700 to-indigo-800 text-white shadow-md">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          {/* Logo y título */}
          <div className="flex items-center">
            <Link to="/" className="flex items-center">
              <span className="text-xl font-bold">Pokémon Explorer</span>
            </Link>
          </div>
          
          {/* Enlaces de navegación - versión escritorio */}
          <div className="hidden md:block">
            <div className="flex items-center space-x-4">
              {navLinks.map(link => (
                <Link
                  key={link.path}
                  to={link.path}
                  className={`px-3 py-2 rounded-md text-sm font-medium ${
                    location.pathname === link.path
                      ? 'bg-blue-800 text-white'
                      : 'text-blue-100 hover:bg-blue-600'
                  }`}
                >
                  {link.label}
                </Link>
              ))}
            </div>
          </div>
          
          {/* Etiqueta que muestra el tipo de Map actual */}
          <div className="hidden md:block">
            <span className="bg-blue-900 px-3 py-1 rounded-full text-xs">
              {mapType}
            </span>
          </div>
          
          {/* Botón de menú móvil */}
          <div className="md:hidden">
            <button
              className="text-gray-100 hover:text-white focus:outline-none"
              onClick={() => setIsMenuOpen(!isMenuOpen)}
            >
              <svg className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                {isMenuOpen ? (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                ) : (
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16M4 18h16" />
                )}
              </svg>
            </button>
          </div>
        </div>
      </div>
      
      {/* Menú móvil */}
      {isMenuOpen && (
        <div className="md:hidden bg-blue-800">
          <div className="px-2 pt-2 pb-3 space-y-1">
            {navLinks.map(link => (
              <Link
                key={link.path}
                to={link.path}
                className={`block px-3 py-2 rounded-md text-base font-medium ${
                  location.pathname === link.path
                    ? 'bg-blue-900 text-white'
                    : 'text-blue-100 hover:bg-blue-700'
                }`}
                onClick={() => setIsMenuOpen(false)}
              >
                {link.label}
              </Link>
            ))}
            <div className="px-3 py-2 text-sm text-blue-200">
              Usando: {mapType}
            </div>
          </div>
        </div>
      )}
    </nav>
  );
};

export default NavBar;