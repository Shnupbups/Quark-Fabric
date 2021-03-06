package vazkii.quark.oddities.module;

import java.util.List;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import vazkii.arl.util.RegistryHelper;
import vazkii.quark.base.Quark;
import vazkii.quark.base.module.LoadModule;
import vazkii.quark.base.module.Module;
import vazkii.quark.base.module.ModuleCategory;
import vazkii.quark.base.module.config.Config;
import vazkii.quark.oddities.block.MagnetBlock;
import vazkii.quark.oddities.block.MovingMagnetizedBlock;
import vazkii.quark.oddities.client.render.MagnetizedBlockTileEntityRenderer;
import vazkii.quark.oddities.tile.MagnetTileEntity;
import vazkii.quark.oddities.tile.MagnetizedBlockTileEntity;

@LoadModule(category = ModuleCategory.ODDITIES, requiredMod = Quark.ODDITIES_ID)
public class MagnetsModule extends Module {
	
    public static BlockEntityType<MagnetTileEntity> magnetType;
    public static BlockEntityType<MagnetizedBlockTileEntity> magnetizedBlockType;
    
    @Config(description = "Any items you place in this list will be derived so that any block made of it will become magnetizable") 
    public static List<String> magneticDerivationList = Lists.newArrayList("minecraft:iron_ingot");
    
    @Config public static List<String> magneticWhitelist = Lists.newArrayList("minecraft:chipped_anvil", "minecraft:damaged_anvil");
    @Config public static List<String> magneticBlacklist = Lists.newArrayList("minecraft:tripwire_hook");
	
	public static Block magnet;
	public static Block magnetized_block;

	@Override
	public void construct() {
		magnet = new MagnetBlock(this);
		magnetized_block = new MovingMagnetizedBlock(this);
		
		magnetType = BlockEntityType.Builder.create(MagnetTileEntity::new, magnet).build(null);
		RegistryHelper.register(magnetType, "magnet");

		magnetizedBlockType = BlockEntityType.Builder.create(MagnetizedBlockTileEntity::new, magnetized_block).build(null);
		RegistryHelper.register(magnetizedBlockType, "magnetized_block");
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void clientSetup() {
		ClientRegistry.bindTileEntityRenderer(magnetizedBlockType, MagnetizedBlockTileEntityRenderer::new);
	}
	
}
