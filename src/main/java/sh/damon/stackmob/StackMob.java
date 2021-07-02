package sh.damon.stackmob;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import sh.damon.stackmob.entity.EntityManager;
import sh.damon.stackmob.entity.stackentity.StackSize;
import sh.damon.stackmob.util.Log;

public class StackMob implements ModInitializer {
	public static final String MOD_ID = "stackmob";
	public static final String MOD_NAME = "StackMob";

	public final EntityManager entityManager = new EntityManager();
	public static Log log = new Log(MOD_NAME);

	private static StackMob instance;

	@Override
	public void onInitialize() {
		StackMob.instance = this;



		StackMob.log.info( "Mod is ready.");
	}

	public static StackMob getInstance() {
		return instance;
	}
}
