package com.example.examplemod;

import com.example.examplemod.tileentity.TileEntityDigitalOutput;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DigitalOutputBlock extends Block {
	ParticleAPI api = new ParticleAPI();
	private boolean isOn;
	public static final PropertyInteger PIN = PropertyInteger.create("pin", 0, 7);
	
	protected DigitalOutputBlock(Material mat) {
		super(Material.redstoneLight);
		this.isOn = false;
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setDefaultState(this.blockState.getBaseState().withProperty(PIN, 0));
		this.setRegistryName("digitalOutputBlock");
	}
    
    public boolean isNormalCube() {
    	return true;
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);
    	System.out.println("Ok, cool");
    	worldIn.setBlockState(pos, state.cycleProperty(PIN), 3);
        return true;
    }
	
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock) {
		if (!world.isRemote) {
            if (world.isBlockPowered(pos)) {
            	System.out.println(state.getValue(PIN).toString());
            	api.setDigitalPin(state.getValue(PIN).toString(), "on");
            	this.setLightLevel(1.0F);
            	this.isOn = true;
            } else {
            	api.setDigitalPin(state.getValue(PIN).toString(), "off");
            	this.setLightLevel(0.0F);
            	this.isOn = false;
            }
        }
    }
	
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityDigitalOutput();
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
    	System.out.println(meta);
        return this.getDefaultState().withProperty(PIN, Integer.valueOf(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((Integer)state.getValue(PIN)).intValue();
        return i;
    }
    
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {PIN});
    }
}
