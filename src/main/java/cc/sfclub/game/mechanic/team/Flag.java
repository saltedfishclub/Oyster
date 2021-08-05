package cc.sfclub.game.mechanic.team;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.0.1")
public class Flag {
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 50;
    public static final int PRIORITY_LOW = 100;
    private String name;
    private int priority;
    private FlagType type;
}
