import React, { useState } from 'react';
import { BsPlus } from 'react-icons/bs';
import { PopupModalProps } from '../interfaces/PopupModalProps';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const PopupModal: React.FC<PopupModalProps> = ({
  handleSubmit,
  todo,
  handleNameChange,
  handleDescriptionChange,
  handleDueDateChange,
  handlePriorityChange,
  handleHover,
  hovered,
  handleMouseLeave,
  handlePopupModalClick
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const [plusIconHovered, setPluIconHovered] = useState(false);

  const openModal = () => {
    handlePopupModalClick;
    setIsOpen(true);
  };

  const closeModal = () => {
    handlePopupModalClick;
    setIsOpen(false);
  };

  const handlePlusIconHover = () => {
    setPluIconHovered(true);
  };
  const handlePlusIconLeave = () => {
    setPluIconHovered(false);
  };

  return (
    <div
      className="flex items-center w-3/12 mr-20 h-auto bg-white font-normal"
      onMouseEnter={handleHover}
      onMouseLeave={handleMouseLeave}
    >
      {/* Overlay */}
      {isOpen && <div className="fixed inset-0 bg-black opacity-50"></div>}

      {/* Modal */}
      {isOpen && (
        <div className="fixed w-1/2 h-6/10 top-1/2 left-1/4 z-50 overflow-hidden bg-white bg-opacity-90 p-4 rounded-2xl shadow-lg border border-white border-opacity-20">
          <div>
            <form onSubmit={handleSubmit}>
              <div className="">
                <input
                  type="text"
                  id="name"
                  name="name"
                  className=""
                  placeholder="Task name"
                  required
                  value={todo?.name || ''}
                  onChange={handleNameChange}
                />
                <textarea
                  id="taskDescription"
                  name="description"
                  className=""
                  onChange={handleDescriptionChange}
                  placeholder="Task description"
                ></textarea>
                <label htmlFor="dueDate">Due date:</label>
                <DatePicker
                  id="dueDate"
                  selected={todo?.dueDate}
                  onChange={(date: Date) => handleDueDateChange(date)}
                />
                <label htmlFor="priorities">Priority:</label>
                <select
                  id="priorities"
                  name="priorities"
                  onChange={handlePriorityChange}
                >
                  <option value="high">High</option>
                  <option value="medium">Medium</option>
                  <option value="low">Low</option>
                </select>
                <button type="submit">Create todo</button>
              </div>
            </form>
          </div>
          ;
          <button
            className="bg-gray-200 text-gray-800 hover:bg-gray-300 px-4 py-2 rounded-lg flex items-center"
            onClick={closeModal}
          >
            X
          </button>
        </div>
      )}

      {/* Button to open the modal */}
      {isOpen && (
        <button className="px-2 py-1 text-sm font-medium ml-8">
          <div className="flex justify-center items-center space-x-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-12 h-12 text-blue-500"
              viewBox="0 0 24 24"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              fill="none"
              stroke="currentColor"
            >
              <circle cx="12" cy="12" r="10" className="text-blue-200" />
              <line x1="12" y1="8" x2="12" y2="16" />
              <line x1="8" y1="12" x2="16" y2="12" />
            </svg>
          </div>
        </button>
      )}
      {hovered ? (
        <button
          className="px-2 py-1 text-sm font-medium ml-8"
          onClick={openModal}
          onMouseEnter={handleHover}
          onMouseLeave={handleMouseLeave}
        >
          <div className="flex justify-center items-center space-x-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-12 h-12 text-blue-500"
              viewBox="0 0 24 24"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
              fill="none"
              style={{ fill: '#2F2FA2' }}
              stroke="currentColor"
            >
              <circle cx="12" cy="12" r="10" className="text-blue-200" />
              <line x1="12" y1="8" x2="12" y2="16" />
              <line x1="8" y1="12" x2="16" y2="12" />
            </svg>
          </div>
        </button>
      ) : (
        <button
          className="px-2 py-1 text-sm font-medium ml-8"
          onClick={openModal}
          onMouseEnter={handleHover}
          onMouseLeave={handleMouseLeave}
        >
          <div className="flex justify-center items-center space-x-1">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="40"
              height="40"
              fill="black"
              className="bi bi-plus"
              viewBox="0 0 16 16"
              style={{ fill: '#2F2FA2' }}
            >
              <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z" />
            </svg>
          </div>
        </button>
      )}
    </div>
  );
};

export default PopupModal;
