import { Todo } from "../classes/Todo";
export default function TodoComponent({
  id,
  user,
  description,
  isComplete,
  dueDate,
  priority,
}: Todo) {
  return (
    <div className='max-w-lg rounded overflow-hidden shadow-lg bg-white my-5'>
      <div className='px-6 py-4'>
        <div className='px-6 pt-4 pb-2'>
          <p>{description}</p>
          <div className='mr-2'>
            {isComplete ? (
              <svg
                className='h-6 w-6 text-green-500'
                fill='none'
                viewBox='0 0 24 24'
                stroke='currentColor'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  strokeWidth='2'
                  d='M5 13l4 4L19 7'
                />
              </svg>
            ) : (
              <svg
                xmlns='http://www.w3.org/2000/svg'
                fill='none'
                viewBox='0 0 24 24'
                strokeWidth={1.5}
                stroke='currentColor'
                className='w-6 h-6'
              >
                <path
                  strokeLinecap='round'
                  strokeLinejoin='round'
                  d='M9 12.75L11.25 15 15 9.75M21 12a9 9 0 11-18 0 9 9 0 0118 0z'
                />
              </svg>
            )}
          </div>
          <div
            className={
              isComplete ? 'line-through text-gray-600' : 'text-gray-600'
            }
          >
            {/* {isComplete ? 'Completed' : 'Not Completed'} */}
          </div>
          <div>
            <span className='text-gray-600 font-semibold'>Due Date: </span>
            <span>{dueDate}</span>
          </div>

          <div className='text-gray-600 font-semibold'>
            Priority: {priority}
          </div>
        </div>
      </div>
    </div>
  );
}
