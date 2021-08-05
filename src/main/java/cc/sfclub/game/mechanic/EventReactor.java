package cc.sfclub.game.mechanic;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.AvailableSince("0.0.1")
public interface EventReactor {
    void onEvent(GameEvent event);
}
