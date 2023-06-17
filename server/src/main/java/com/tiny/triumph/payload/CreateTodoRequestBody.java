package com.tiny.triumph.payload;

import com.tiny.triumph.enums.Priority;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CreateTodoRequestBody {

    @NotEmpty(message = "Name required")
    public String name;

    @NotEmpty(message = "Description required")
    public String description;
    @NotEmpty(message = "isComplete required")
    public boolean isComplete;

    public LocalDateTime dueDate;
    public Priority priority;

    public long unixTimestamp;

    public String getName() {
        return name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public long getUnixTimestamp() {
        return unixTimestamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnixTimestamp(long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setDueDate() {
        Instant instant = Instant.ofEpochMilli(this.getUnixTimestamp());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        this.dueDate = localDateTime;
    }
}
