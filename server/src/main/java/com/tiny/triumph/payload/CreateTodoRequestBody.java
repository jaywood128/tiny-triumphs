package com.tiny.triumph.payload;

import com.tiny.triumph.enums.Priority;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class CreateTodoRequestBody {

    @NotEmpty(message = "Description required")
    public String description;
    @NotEmpty(message = "isComplete required")
    public boolean isComplete;

    public LocalDateTime dueDate;
    @NotEmpty(message = "priority is required")
    public Priority priority;

    public boolean isComplete() {
        return isComplete;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}

