package me.limeglass.skellett.elements.bossbars;

import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class CondBossBarPlayer extends Condition {

	static {
		Skript.registerCondition(CondBossBarPlayer.class, "%bossbars% (contains|has|have) player[s] %players%", "%bossbars% (doesn't|does not|do not|don't) (have|contain) player[s] %players%");
	}

	private Expression<BossBar> bossbars;
	private Expression<Player> players;

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
		bossbars = (Expression<BossBar>) exprs[0];
		players = (Expression<Player>) exprs[1];
		setNegated(matchedPattern == 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event event, boolean debug) {
		return "bossbar players " + players.toString(event, debug) + " for bossbars " + bossbars.toString(event, debug);
	}

	@Override
	public boolean check(Event event) {
		return bossbars.check(event, bossbar -> players.stream(event).allMatch(player -> bossbar.getPlayers().contains(player)), isNegated());
	}

}
