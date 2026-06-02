package com.cstify.common.constant;

public final class SystemActor {
    private SystemActor() {}

    public static final long USER_SYSTEM = 0L;     // 시스템(일반)
    public static final long USER_BATCH  = -1L;    // 배치/스케줄러
}
