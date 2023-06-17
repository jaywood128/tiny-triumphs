import { Todo } from '../classes/Todo';
import { Dispatch, SetStateAction } from 'react';
import { FormEvent } from 'react';

export interface PopupModalProps {
  handleSubmit(event: React.FormEvent<HTMLFormElement>): void;
  todo: Todo | undefined;
  handleNameChange(
    e:
      | React.ChangeEvent<HTMLInputElement>
      | React.ChangeEvent<HTMLTextAreaElement>
  ): void;
  handleDescriptionChange(
    e:
      | React.ChangeEvent<HTMLInputElement>
      | React.ChangeEvent<HTMLTextAreaElement>
  ): void;
  handleDueDateChange(date: Date | null): void;
  handlePriorityChange(e: React.ChangeEvent<HTMLSelectElement>): void;
  handleHover(): void;
  hovered: boolean | undefined;
  handleMouseLeave(): void;
  // setHidePlusIcon(hide: boolean): void;
  onClick(event: FormEvent<HTMLFormElement>): void;
  handlePopupModalClick(): void;
}
