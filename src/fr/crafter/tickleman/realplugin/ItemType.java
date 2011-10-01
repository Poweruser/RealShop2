package fr.crafter.tickleman.realplugin;

import net.minecraft.server.Block;
import net.minecraft.server.Item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

//#################################################################################### RealItemType
public class ItemType
{

	/**
	 * Minecraft type identifier of item
	 */
	private int typeId;

	/**
	 * Variant code of item, for items than can have variants
	 * Equals ItemStack.getDurability() for items that can be damaged
	 * Is null for non-applicable items
	 */
	private short variant;

	//------------------------------------------------------------------------------------- ItemStack
	public ItemType(ItemStack itemStack)
	{
		this(itemStack.getTypeId(), itemStack.getDurability());
	}

	//-------------------------------------------------------------------------------------- ItemType
	public ItemType(net.minecraft.server.ItemStack itemStack)
	{
		this(itemStack.id, (short)itemStack.damage);
	}

	//------------------------------------------------------------------------------------------ Item
	public ItemType(net.minecraft.server.Item item)
	{
		this(item.id, (short)0);
	}

	//------------------------------------------------------------------------------------------ Item
	public ItemType(net.minecraft.server.Item item, short variant)
	{
		this(item.id, variant);
	}

	//-------------------------------------------------------------------------------------- ItemType
	public ItemType(Material material)
	{
		this(material.getId(), (short)0);
	}

	//-------------------------------------------------------------------------------------- ItemType
	public ItemType(Material material, short variant)
	{
		this(material.getId(), variant);
	}

	//-------------------------------------------------------------------------------------- ItemType
	public ItemType(int typeId)
	{
		this(typeId, (short)0);
	}

	//-------------------------------------------------------------------------------------- ItemType
	public ItemType(int typeId, short variant)
	{
		setTypeIdVariant(typeId, variant);
	}

	//--------------------------------------------------------------------------------------- getName
	public String getName()
	{
		String name;
		if (typeId < 256) {
			name = Block.byId[typeId].l();
		} else {
			name = Item.byId[typeId].b();
		}
		name = name.substring(name.indexOf(".") + 1);
		System.out.println("ItemType.getName() : name for " + typeId + " = " + name);
		return name;
	}

	//------------------------------------------------------------------------------------- getTypeId
	public int getTypeId()
	{
		return typeId;
	}

	//------------------------------------------------------------------------------------ getVariant
	public short getVariant()
	{
		return variant;
	}

	//--------------------------------------------------------------------------------- parseItemType
	public static ItemType parseItemType(String typeIdVariant)
	{
		if (typeIdVariant.contains(":")) {
			String[] split = typeIdVariant.split(":");
			return new ItemType(Integer.parseInt(split[0]), Short.parseShort(split[1]));
		} else {
			return new ItemType(Integer.parseInt(typeIdVariant));
		}
	}

	//------------------------------------------------------------------------------------- setTypeId
	public void setTypeId(int typeId)
	{
		setTypeIdVariant(typeId, variant);
	}

	//------------------------------------------------------------------------------------ isSameItem
	public boolean isSameItem(ItemType itemType)
	{
		return (itemType.getTypeId() == this.getTypeId())
			&& (itemType.getVariant() == this.getVariant());
	}

	//------------------------------------------------------------------------------ setTypeIdVariant
	public void setTypeIdVariant(int typeId, short variant)
	{
		this.typeId = typeId;
		setVariant(variant);
	}

	//------------------------------------------------------------------------------------ setVariant
	public void setVariant(short variant)
	{
		if (typeIdHasVariant(typeId)) {
			this.variant = variant;
		} else {
			this.variant = 0;
		}
	}

	//------------------------------------------------------------------------------- typeIdHasDamage
	public static Boolean typeIdHasDamage(int typeId)
	{
		return !typeIdHasVariant(typeId);
	}

	//------------------------------------------------------------------------------ typeIdHasVariant
	public static Boolean typeIdHasVariant(int typeId)
	{
		return
			// those codes have variant : durability is an item variant instead of damage
			(typeId == Material.WOOD.getId())
			|| (typeId == Material.LEAVES.getId())
			|| (typeId == Material.MONSTER_EGGS.getId())
			|| (typeId == Material.WOOL.getId())
			|| (typeId == Material.DOUBLE_STEP.getId())
			|| (typeId == Material.STEP.getId())
			|| (typeId == Material.COAL.getId())
			|| (typeId == Item.INK_SACK.id)
		;
	}

	//------------------------------------------------------------------------------- typeIdMaxDamage
	public static short typeIdMaxDamage(int typeId)
	{
		if (typeIdHasVariant(typeId)) {
			return 0;
		} else {
			switch (typeId) {
				case 270: return 60;
				case 273: return 132;
				// TODO : each item that can be damaged may have it's one maxDamage value. Get them !
				default:  return 100;
			}
		}
	}

	//-------------------------------------------------------------------------------------- toString
	@Override
	public String toString()
	{
		return getTypeId() + ((getVariant() > 0) ? ":" + getVariant() : "");
	}

}
