package cc.sfclub.game.mechanic.team;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.ApiStatus;

/**
 * It is recommended that you register FlagType as a listener, which helps you implements the functionality of it.
 */
@ApiStatus.AvailableSince("0.0.1")
public abstract class FlagType implements Listener {
    public abstract String getName();
}
