package cn.bobdeng.smartlock.lockuser;

class TimeRange {
    Long start;
    Long end;

    public TimeRange(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    public TimeRange() {

    }

    public boolean notExpire() {
        if (start == null || end == null) {
            return true;
        }
        return LockUserRepositories.currentTime() >= start && LockUserRepositories.currentTime() <= end;
    }

    public boolean overflow(TimeRange timeRange) {
        if (isEmpty()) {
            return false;
        }
        if (timeRange.isEmpty()) {
            return true;
        }
        return this.start <= start || this.end >= end;
    }

    private boolean isEmpty() {
        return start == null || end == null;
    }

    public long beforeStart(int offset) {
        return start-offset;
    }

    public long afterEnd(int offset) {
        return end+offset;
    }
}
