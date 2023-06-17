import { useState } from 'react';
import { CircleProps } from '../interfaces/CircleProps';

const CircleIcon: React.FC<CircleProps> = () => {
  const [hovered, setHovered] = useState(false);

  const handleHover = () => {
    setHovered(true);
  };

  const handleMouseLeave = () => {
    setHovered(false);
  };
  const handleOnClick = () => {};

  return (
    <div
      className={`w-8 h-8 rounded-full flex items-center justify-center ${
        hovered ? 'bg-green-500' : 'bg-white'
      }`}
      onMouseEnter={handleHover}
      onMouseLeave={handleMouseLeave}
    >
      {hovered ? (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-6 w-6 text-white"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M5 13l4 4L19 7"
          />
        </svg>
      ) : (
        <svg
          xmlns="http://www.w3.org/2000/svg"
          className="h-8 w-8 text-gray-600"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <circle cx="12" cy="12" r="10" strokeWidth={1} />
        </svg>
      )}
    </div>
  );
};
export default CircleIcon;
