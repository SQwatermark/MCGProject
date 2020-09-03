/*
 * @file BlockTrapdoorSwitch.java
 * @author Stefan Wilhelm (wile)
 * @copyright (C) 2018 Stefan Wilhelm
 * @license MIT (see https://opensource.org/licenses/MIT)
 *
 * Basic class for blocks representing switches which swing
 * open on activation, allowing entities to fall throuh.
 * Different activation triggers possible: fall uppon, walk,
 * etc.
 */
package moe.gensoukyo.mcgproject.common.feature.rsgauges.blocks;

import moe.gensoukyo.mcgproject.common.feature.rsgauges.detail.ModResources;
import moe.gensoukyo.mcgproject.common.feature.rsgauges.items.ItemSwitchLinkPearl;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTrapdoorSwitch extends BlockContactSwitch
{

  public BlockTrapdoorSwitch(String registryName, AxisAlignedBB unrotatedBBUnpowered, AxisAlignedBB unrotatedBBPowered, long config, @Nullable ModResources.BlockSoundEvent powerOnSound, @Nullable ModResources.BlockSoundEvent powerOffSound, @Nullable Material material)
  { super(registryName, unrotatedBBUnpowered, unrotatedBBPowered, config, powerOnSound, powerOffSound, material); }

  public BlockTrapdoorSwitch(String registryName, AxisAlignedBB unrotatedBBUnpowered, AxisAlignedBB unrotatedBBPowered, long config, @Nullable ModResources.BlockSoundEvent powerOnSound, @Nullable ModResources.BlockSoundEvent powerOffSound)
  { super(registryName, unrotatedBBUnpowered, unrotatedBBPowered, config, powerOnSound, powerOffSound, null); }

  @Override
  public boolean onLinkRequest(final ItemSwitchLinkPearl.SwitchLink link, long req, final World world, final BlockPos pos, final @Nullable EntityPlayer player)
  {
    if((world.isRemote) || ((config & (SWITCH_CONFIG_LINK_TARGET_SUPPORT))==0)) return false;
    IBlockState state = world.getBlockState(pos);
    if((state == null) || (!(state.getBlock() instanceof BlockTrapdoorSwitch))) return false;
    if(state.getValue(POWERED)) return true; // already active
    TileEntityContactSwitch te = getTe(world, pos);
    if((te==null) || (!te.check_link_request(link))) return false;
    world.setBlockState(pos, state.withProperty(POWERED, true), 1|2);
    power_on_sound.play(world, pos);
    notifyNeighbours(world, pos, state, te, false);
    te.on_timer_reset( (te.configured_on_time()==0) ? (default_pulse_on_time) : ( (te.configured_on_time() < 2) ? 2 : te.configured_on_time()) );
    te.reschedule_block_tick();
    return true;
  }

  @Override
  public boolean isPassable(IBlockAccess world, BlockPos pos)
  { IBlockState state=world.getBlockState(pos); return ((state!=null) && (state.getBlock()==this) && state.getValue(POWERED)); }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
  { return ((face==EnumFacing.UP) && (state!=null) && (state.getBlock()==this) && (!state.getValue(POWERED))) ? (BlockFaceShape.SOLID) : super.getBlockFaceShape(world, state, pos, face) ; }

  @Override
  public void onFallenUpon(World world, BlockPos pos, Entity entity, float distance)
  {
    if(((config & SWITCH_CONFIG_SHOCK_SENSITIVE)!=0)) {
      if(world.isRemote) return;
      onEntityCollided(world, pos, world.getBlockState(pos), entity, new AxisAlignedBB(pos.add(-1.2,0,-1.2), pos.add(1.2,2,1.2)));
      final BlockPos[] neighbors = { pos.add(1,0,0), pos.add(-1,0,0), pos.add(0,0,1), pos.add(0,0,-1), pos.add(1,0,1), pos.add(-1,0,-1), pos.add(-1,0,1), pos.add(1,0,-1), };
      for(BlockPos p: neighbors) {
        final IBlockState st = world.getBlockState(p);
        if((st!=null) && (st.getBlock()==this)) onEntityCollided(world, p, st, entity, new AxisAlignedBB(p.add(-1.2,0,-1.2), p.add(1.2,2,1.2)));
      }
    }
    super.onFallenUpon(world, pos, entity, distance);
  }

  @Override
  public void onEntityWalk(World world, BlockPos pos, Entity entity)
  {
    if(((config & SWITCH_CONFIG_HIGH_SENSITIVE)==0) || (world.isRemote) || (entity.isSneaking())) return;
    onEntityCollided(world, pos, world.getBlockState(pos), entity, new AxisAlignedBB(pos.add(-1.2,0,-1.2), pos.add(1.2,2,1.2)));
    final BlockPos[] neighbors = { pos.add(1,0,0), pos.add(-1,0,0), pos.add(0,0,1), pos.add(0,0,-1), pos.add(1,0,1), pos.add(-1,0,-1), pos.add(-1,0,1), pos.add(1,0,-1), };
    for(BlockPos p: neighbors) {
      final IBlockState st = world.getBlockState(p);
      if((st!=null) && (st.getBlock()==this)) onEntityCollided(world, p, st, entity, new AxisAlignedBB(p.add(-1.2,0,-1.2), p.add(1.2,2,1.2)));
    }
  }

  @Override
  public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
  {
    if(((config & SWITCH_CONFIG_SHOCK_SENSITIVE)!=0) && (entity.height < 0.9)) return; // close on items
    onEntityCollided(world, pos, state, entity, new AxisAlignedBB(pos, pos.add(1,1,1)));
  }

}
