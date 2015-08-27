//package tehnut.soulshards.client.render;
//
//import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
//import net.minecraft.block.Block;
//import net.minecraft.client.renderer.RenderBlocks;
//import net.minecraft.client.renderer.entity.RenderManager;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityList;
//import net.minecraft.item.ItemStack;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.IBlockAccess;
//import org.lwjgl.opengl.GL11;
//import tehnut.soulshards.SoulShardsReawakening;
//import tehnut.soulshards.soulshards;
//import tehnut.soulshards.tile.TileEntitySpawner;
//import tehnut.soulshards.util.helper.ShardHelper;
//
//public class RenderSpawner implements ISimpleBlockRenderingHandler {
//
//    @Override
//    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//        renderer.setOverrideBlockTexture(block.getIcon(0, 0));
//    }
//
//    @Override
//    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
////        renderer.renderAllFaces = true;
//        TileEntity spawner = world.getTileEntity(x, y, z);
//        ItemStack shardIn = ((TileEntitySpawner) spawner).getShardIn();
//        String entityName = ShardHelper.getShardEntity(shardIn);
//        Entity entity = EntityList.createEntityByName(entityName, SoulShardsReawakening.proxy.getClientWorld());
//
//        float f1 = 0.4375F;
//        GL11.glTranslatef(0.0F, 0.4F, 0.0F);
//        GL11.glRotatef(20F, 0F, 0F, 1F);
////        GL11.glRotatef(Minecraft.getMinecraft().getSystemTime() / -10, 0F, 1F, 0F);
//        GL11.glRotatef(-20F, 1F, 0F, 0F);
//        GL11.glTranslatef(0.0F, -0.4F, 0.0F);
//        GL11.glScalef(f1, f1, f1);
//        entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
//        RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0F, 1F);
//
//        return true;
//    }
//
//    @Override
//    public boolean shouldRender3DInInventory(int modelId) {
//        return true;
//    }
//
//    @Override
//    public int getRenderId() {
//        return soulshards.renderIDSpawner;
//    }
//}
