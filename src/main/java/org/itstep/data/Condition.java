package org.itstep.data;

public enum Condition {
    ACTIVE(1, "Active"),
    DONE(2, "Done");

    private final int num;
    private final String condition;

    Condition(int num, String condition) {
        this.num = num;
        this.condition = condition;
    }

    public int num() {
        return num;
    }

    public String condition() {
        return condition;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", num, condition);
    }
}
