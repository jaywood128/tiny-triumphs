package com.tiny.triumph.payload;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

public class CreateTodoRequestBody {

    @NotEmpty(message = "Description required")
    public String description;
    @NotEmpty(message = "isComplete required")
    public boolean isComplete;

    public LocalDateTime dueDate;

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

