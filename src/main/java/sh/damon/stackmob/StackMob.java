package sh.damon.stackmob;

import net.fabricmc.api.ModInitializer;
import sh.damon.stackmob.entity.EntityManager;
import sh.damon.stackmob.entity.traits.TraitManager;
import sh.damon.stackmob.util.Log;

import java.lang.reflect.InvocationTargetException;

public class StackMob implements ModInitializer {
	public static final String MOD_ID = "stackmob";
	public static final String MOD_NAME = "StackMob";

	public final EntityManager entityManager = new EntityManager();
	public final TraitManager traitManager = new TraitManager();
	public static Log log = new Log(MOD_NAME);

	private static StackMob instance;

	@Override
	public void onInitialize() {
		StackMob.instance = this;

		try {
			this.traitManager.registerAll();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		StackMob.log.info( "Mod is ready.");
	}

	public static StackMob getInstance() {
		return instance;
	}
}
