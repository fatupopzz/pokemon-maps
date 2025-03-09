import React from 'react';

/**
 * Componente para mostrar una barra de estadísticas
 * @param {Object} props - Propiedades del componente
 * @param {string} props.label - Etiqueta de la estadística
 * @param {number} props.value - Valor de la estadística
 * @param {number} props.maxValue - Valor máximo posible (para calcular el porcentaje)
 * @param {string} props.color - Clase CSS para el color de la barra
 */
const StatBar = ({ label, value, maxValue = 255, color = "bg-blue-500" }) => {
  // Calcular el porcentaje para el ancho de la barra
  const percentage = Math.min((value / maxValue) * 100, 100);
  
  return (
    <div className="mb-2">
      <div className="flex justify-between text-xs text-gray-600 mb-1">
        <span>{label}</span>
        <span>{value}</span>
      </div>
      <div className="h-2 w-full bg-gray-200 rounded-full overflow-hidden">
        <div 
          className={`h-2 ${color} rounded-full transition-all duration-500 ease-out`} 
          style={{ width: `${percentage}%` }} 
        />
      </div>
    </div>
  );
};

export default StatBar;